package dio.board_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dio.board_project.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> { 
    
}

