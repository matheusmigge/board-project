package dio.board_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dio.board_project.model.CardHistory;

@Repository
public interface CardHistoryRepository extends JpaRepository<CardHistory, Long> { 

}

