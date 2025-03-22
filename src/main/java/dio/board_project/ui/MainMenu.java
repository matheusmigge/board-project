package dio.board_project.ui;

import dio.board_project.model.Board;
import dio.board_project.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class MainMenu {

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardMenu boardMenu; // Injetar o BoardMenu

    public void execute() {
        System.out.println("Bem-vindo ao gerenciador de boards, escolha a opção desejada");
        while (true) {
            System.out.println("1 - Criar um novo board");
            System.out.println("2 - Selecionar um board existente");
            System.out.println("3 - Excluir um board");
            System.out.println("4 - Sair");
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu");
            }
        }
    }

    private void createBoard() {
        System.out.println("Informe o nome do seu board:");
        String boardName = scanner.next();

        System.out.println("Seu board terá colunas além das 3 padrões? Se sim, informe quantas, senão digite '0'");
        int additionalColumns = scanner.nextInt();

        System.out.println("Informe o nome da coluna inicial do board:");
        String initialColumn = scanner.next();

        List<String> intermediateColumns = new ArrayList<>();
        for (int i = 0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da coluna intermediária:");
            intermediateColumns.add(scanner.next());
        }

        System.out.println("Informe o nome da coluna final:");
        String finalColumn = scanner.next();

        System.out.println("Informe o nome da coluna de cancelamento:");
        String cancelColumn = scanner.next();

        boardService.createBoard(boardName, additionalColumns, initialColumn, finalColumn, cancelColumn, intermediateColumns);
        System.out.println("Board criado com sucesso!");
    }

    private void selectBoard() {
        System.out.println("Informe o ID do board que deseja selecionar:");
        long id = scanner.nextLong();
        Optional<Board> boardOpt = boardService.getBoardById(id);

        boardOpt.ifPresentOrElse(
            board -> {
                System.out.println("Board selecionado: " + board.getNome());
                boardMenu.execute(board); // Executar BoardMenu com o board selecionado
            },
            () -> System.out.println("Não foi encontrado um board com ID " + id)
        );
    }

    private void deleteBoard() {
        System.out.println("Informe o ID do board que será excluído:");
        long id = scanner.nextLong();
        if (boardService.deleteBoard(id)) {
            System.out.println("O board " + id + " foi excluído.");
        } else {
            System.out.println("Não foi encontrado um board com ID " + id);
        }
    }
}
