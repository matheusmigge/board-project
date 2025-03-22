package dio.board_project.service;

import dio.board_project.model.Board;
import dio.board_project.model.BoardColumn;
import dio.board_project.repository.BoardRepository;
import dio.board_project.repository.BoardColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardColumnRepository columnRepository;

    public Board createBoard(String nome, int additionalColumns, String initialColumnName, String finalColumnName, String cancelColumnName, List<String> intermediateColumnNames) {
        Board board = new Board();
        board.setNome(nome);

        List<BoardColumn> columns = new ArrayList<>();
        columns.add(createColumn(initialColumnName, "Inicial", 0, board));

        for (int i = 0; i < additionalColumns; i++) {
            columns.add(createColumn(intermediateColumnNames.get(i), "Pendente", i + 1, board));
        }

        columns.add(createColumn(finalColumnName, "Final", additionalColumns + 1, board));
        columns.add(createColumn(cancelColumnName, "Cancelado", additionalColumns + 2, board));

        boardRepository.save(board);
        columnRepository.saveAll(columns);

        return board;
    }

    public Optional<Board> getBoardById(long id) {
        return boardRepository.findById(id);
    }

    public Optional<Board> getBoardDetails(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public boolean deleteBoard(long id) {
        if (boardRepository.existsById(id)) {
            boardRepository.deleteById(id);
            return true;
        }
        return false;
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
