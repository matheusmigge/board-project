package dio.board_project.ui;

import dio.board_project.model.Board;
import dio.board_project.model.Card;
import dio.board_project.model.BoardColumn;
import dio.board_project.service.BoardColumnService;
import dio.board_project.service.BoardService;
import dio.board_project.service.CardService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class BoardMenu {

    private final Scanner scanner = new Scanner(System.in); 
    private final CardService cardService;
    private final BoardService boardService;
    private final BoardColumnService boardColumnService;

    private Board board;

    public BoardMenu(CardService cardService, BoardService boardService, BoardColumnService boardColumnService) {
        this.cardService = cardService;
        this.boardService = boardService;
        this.boardColumnService = boardColumnService;
    }

    public void execute(Board board) {
        this.board = board; 
        System.out.printf("Bem-vindo ao board %s! Selecione a operação desejada:\n", board.getNome());
        
        while (true) {
            System.out.println("1 - Criar um card");
            System.out.println("2 - Mover um card");
            System.out.println("3 - Bloquear um card");
            System.out.println("4 - Desbloquear um card");
            System.out.println("5 - Cancelar um card");
            System.out.println("6 - Ver board");
            System.out.println("7 - Ver coluna com cards");
            System.out.println("8 - Ver card");
            System.out.println("9 - Voltar para o menu principal");
            System.out.println("10 - Sair");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Escolha um número do menu.");
                continue;
            }

            switch (option) {
                case 1 -> createCard();
                case 2 -> moveCardToNextColumn();
                case 3 -> blockCard();
                case 4 -> unblockCard();
                case 5 -> cancelCard();
                case 6 -> showBoard();
                case 7 -> showColumn();
                case 8 -> showCard();
                case 9 -> {
                    System.out.println("Voltando ao menu principal...");
                    return;
                }
                case 10 -> {
                    System.out.println("Encerrando aplicação...");
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida. Escolha uma opção do menu.");
            }
        }
    }

    private void createCard() {
        Card card = new Card();
        System.out.println("Informe o título do card:");
        card.setTitulo(scanner.nextLine());
        
        System.out.println("Informe a descrição do card:");
        card.setDescricao(scanner.nextLine());
        
        Optional<BoardColumn> initialColumn = Optional.ofNullable(boardColumnService.getInitialColumn(board.getId()));
        if (initialColumn.isPresent()) {
            card.setColumn(initialColumn.get());
            cardService.createCard(card);
            System.out.println("Card criado com sucesso!");
        } else {
            System.out.println("Erro: Nenhuma coluna inicial encontrada para o board.");
        }
    }

    private void moveCardToNextColumn() {
        System.out.println("Informe o ID do card que deseja mover:");
        try {
            Long cardId = Long.parseLong(scanner.nextLine());
            cardService.moveToNextColumn(cardId);
            System.out.println("Card movido com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void blockCard() {
        System.out.println("Informe o ID do card a ser bloqueado:");
        try {
            Long cardId = Long.parseLong(scanner.nextLine());
            System.out.println("Informe o motivo do bloqueio:");
            String motivo = scanner.nextLine();
            cardService.blockCard(cardId, motivo);
            System.out.println("Card bloqueado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void unblockCard() {
        System.out.println("Informe o ID do card a ser desbloqueado:");
        try {
            Long cardId = Long.parseLong(scanner.nextLine());
            cardService.unblockCard(cardId);
            System.out.println("Card desbloqueado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void cancelCard() {
        System.out.println("Informe o ID do card que deseja cancelar:");
        try {
            Long cardId = Long.parseLong(scanner.nextLine());
            cardService.cancelCard(cardId);
            System.out.println("Card cancelado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showBoard() {
        System.out.println("Detalhes do Board:");
        boardService.getBoardDetails(board.getId()).ifPresentOrElse(
                b -> {
                    System.out.printf("Board [%d] - %s\n", b.getId(), b.getNome());
                    List<?> columns = boardColumnService.getBoardColumns(b.getId());
                    columns.forEach(System.out::println);
                },
                () -> System.out.println("Board não encontrado!")
        );
    }

    private void showColumn() {
        System.out.println("Informe o ID da coluna que deseja visualizar:");
        try {
            Long columnId = Long.parseLong(scanner.nextLine());
            boardColumnService.getColumnById(columnId).ifPresentOrElse(
                    column -> {
                        System.out.printf("Coluna: %s\n", column.getNome());
                        column.getCards().forEach(card -> System.out.printf("Card %d - %s\n", card.getId(), card.getTitulo()));
                    },
                    () -> System.out.println("Coluna não encontrada!")
            );
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private void showCard() {
        System.out.println("Informe o ID do card que deseja visualizar:");
        try {
            Long cardId = Long.parseLong(scanner.nextLine());
            cardService.getCardById(cardId).ifPresentOrElse(
                    card -> {
                        System.out.printf("Card %d - %s\n", card.getId(), card.getTitulo());
                        System.out.printf("Descrição: %s\n", card.getDescricao());
                        System.out.println(card.isBloqueado() ? "Está bloqueado." : "Não está bloqueado.");
                    },
                    () -> System.out.println("Card não encontrado!")
            );
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
}
