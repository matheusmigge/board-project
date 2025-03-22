package dio.board_project;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import dio.board_project.model.Board;
import dio.board_project.model.Card;
import dio.board_project.model.BoardColumn;
import dio.board_project.repository.BoardRepository;
import dio.board_project.repository.CardRepository;
import dio.board_project.repository.BoardColumnRepository;

@Component
public class TestDataLoader implements CommandLineRunner {
    
    @Autowired
    private BoardRepository boardRepository;
    
    @Autowired
    private BoardColumnRepository columnRepository;
    
    @Autowired
    private CardRepository cardRepository;
    
    @Override
    public void run(String... args) {
        // Criando um board
        Board board = new Board();
        board.setNome("Meu Primeiro Board");
        board = boardRepository.save(board);

        // Criando colunas
        BoardColumn todo = new BoardColumn(null, "A Fazer", 1, "Inicial", board, null);
        BoardColumn doing = new BoardColumn(null, "Em Progresso", 2, "Pendente", board, null);
        BoardColumn done = new BoardColumn(null, "Concluído", 3, "Final", board, null);

        columnRepository.saveAll(List.of(todo, doing, done));

        // Criando um card
        Card card = new Card(null, "Implementar Backend", "Criar serviços no Spring Boot", LocalDateTime.now(), false, null, todo, null);
        cardRepository.save(card);

        System.out.println("Dados de teste inseridos com sucesso!");
    }
}

