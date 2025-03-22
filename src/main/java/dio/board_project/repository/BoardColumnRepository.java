package dio.board_project.repository;

import dio.board_project.model.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {

    List<BoardColumn> findByBoardId(Long boardId);

    Optional<BoardColumn> findFirstByBoardIdOrderByOrdemAsc(Long boardId);
}
