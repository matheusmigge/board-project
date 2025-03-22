package dio.board_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dio.board_project.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {    
}

