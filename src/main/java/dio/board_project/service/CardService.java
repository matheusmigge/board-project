package dio.board_project.service;

import dio.board_project.model.BoardColumn;
import dio.board_project.model.Card;
import dio.board_project.repository.BoardColumnRepository;
import dio.board_project.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BoardColumnRepository columnRepository;

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public Optional<Card> getCardById(Long cardId) {
        return cardRepository.findById(cardId);
    }

    public void moveToNextColumn(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("O card de ID " + cardId + " não foi encontrado"));

        if (card.isBlocked()) {
            throw new IllegalStateException("O card " + cardId + " está bloqueado. É necessário desbloqueá-lo para mover.");
        }

        BoardColumn currentColumn = card.getColumn();
        List<BoardColumn> columns = columnRepository.findByBoardId(currentColumn.getBoard().getId());

        if (currentColumn.getTipo().equals("Final")) {
            throw new IllegalStateException("O card já foi finalizado.");
        }

        BoardColumn nextColumn = columns.stream()
                .filter(c -> c.getOrdem() == currentColumn.getOrdem() + 1)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Não há próxima coluna disponível para mover o card"));

        card.setColumn(nextColumn);
        cardRepository.save(card);
    }

    public void cancelCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("O card de ID " + cardId + " não foi encontrado"));

        if (card.isBlocked()) {
            throw new IllegalStateException("O card " + cardId + " está bloqueado. É necessário desbloqueá-lo para cancelar.");
        }

        BoardColumn currentColumn = card.getColumn();
        List<BoardColumn> columns = columnRepository.findByBoardId(currentColumn.getBoard().getId());

        BoardColumn cancelColumn = columns.stream()
                .filter(c -> c.getTipo().equals("Cancelado"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Não foi encontrada uma coluna de cancelamento"));

        card.setColumn(cancelColumn);
        cardRepository.save(card);
    }

    public void blockCard(Long cardId, String motivo) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("O card de ID " + cardId + " não foi encontrado"));

        if (card.isBlocked()) {
            throw new IllegalStateException("O card " + cardId + " já está bloqueado.");
        }

        BoardColumn currentColumn = card.getColumn();

        if (currentColumn.getTipo().equals("Final") || currentColumn.getTipo().equals("Cancelado")) {
            throw new IllegalStateException("O card está em uma coluna do tipo " + currentColumn.getTipo() + " e não pode ser bloqueado.");
        }

        card.setBlocked(true, motivo);
        cardRepository.save(card);
    }

    public void unblockCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("O card de ID " + cardId + " não foi encontrado"));

        if (!card.isBlocked()) {
            throw new IllegalStateException("O card " + cardId + " não está bloqueado.");
        }

        card.setBlocked(false, null);
        cardRepository.save(card);
    }
}
