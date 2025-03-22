package dio.board_project.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private boolean bloqueado;
    
    private String motivoBloqueio; // Novo campo para armazenar o motivo

    @ManyToOne
    @JoinColumn(name = "column_id", nullable = false)
    private BoardColumn column;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardHistory> history = new ArrayList<>();

    public void setBlocked(boolean blocked, String motivo) {
        this.bloqueado = blocked;
        this.motivoBloqueio = blocked ? motivo : null;
    }

    public boolean isBlocked() {
        return this.bloqueado;
    }
}
