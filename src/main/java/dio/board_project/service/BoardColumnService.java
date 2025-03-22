package dio.board_project.service;

import dio.board_project.model.BoardColumn;
import dio.board_project.repository.BoardColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardColumnService {

    private final BoardColumnRepository boardColumnRepository;

    public List<BoardColumn> getBoardColumns(Long boardId) {
        return boardColumnRepository.findByBoardId(boardId);
    }

    public Optional<BoardColumn> getColumnById(Long columnId) {
        return boardColumnRepository.findById(columnId);
    }

    public BoardColumn getInitialColumn(Long boardId) {
        return boardColumnRepository.findFirstByBoardIdOrderByOrdemAsc(boardId)
                .orElseThrow(() -> new RuntimeException("Coluna inicial não encontrada!"));
    }
}
