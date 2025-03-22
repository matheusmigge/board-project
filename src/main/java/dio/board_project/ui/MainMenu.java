package dio.board_project.ui;

import dio.board_project.model.Board;
import dio.board_project.model.BoardColumn;
import dio.board_project.repository.BoardRepository;
import dio.board_project.repository.BoardColumnRepository;
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
    private BoardRepository boardRepository;

    @Autowired
    private BoardColumnRepository columnRepository;

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
        Board board = new Board();
        System.out.println("Informe o nome do seu board:");
        board.setNome(scanner.next());

        System.out.println("Seu board terá colunas além das 3 padrões? Se sim, informe quantas, senão digite '0'");
        int additionalColumns = scanner.nextInt();

        List<BoardColumn> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do board:");
        BoardColumn initialColumn = createColumn(scanner.next(), "Inicial", 0, board);
        columns.add(initialColumn);

        for (int i = 0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da coluna intermediária:");
            BoardColumn pendingColumn = createColumn(scanner.next(), "Pendente", i + 1, board);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna final:");
        BoardColumn finalColumn = createColumn(scanner.next(), "Final", additionalColumns + 1, board);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento:");
        BoardColumn cancelColumn = createColumn(scanner.next(), "Cancelado", additionalColumns + 2, board);
        columns.add(cancelColumn);

        boardRepository.save(board);
        columnRepository.saveAll(columns);

        System.out.println("Board criado com sucesso!");
    }

    private void selectBoard() {
        System.out.println("Informe o ID do board que deseja selecionar:");
        long id = scanner.nextLong();
        Optional<Board> boardOpt = boardRepository.findById(id);

        boardOpt.ifPresentOrElse(
            board -> System.out.println("Board selecionado: " + board.getNome()),
            () -> System.out.println("Não foi encontrado um board com ID " + id)
        );
    }

    private void deleteBoard() {
        System.out.println("Informe o ID do board que será excluído:");
        long id = scanner.nextLong();
        if (boardRepository.existsById(id)) {
            boardRepository.deleteById(id);
            System.out.println("O board " + id + " foi excluído.");
        } else {
            System.out.println("Não foi encontrado um board com ID " + id);
        }
    }

    private BoardColumn createColumn(String name, String type, int order, Board board) {
        BoardColumn column = new BoardColumn();
        column.setNome(name);
        column.setTipo(type);
        column.setOrdem(order);
        column.setBoard(board);
        return column;
    }
}
