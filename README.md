## Diagrama de classes:

```mermaid
classDiagram
    class Board {
        +Long id
        +String nome
    }

    class Column {
        +Long id
        +String nome
        +int ordem
        +String tipo
        +Long board_id
    }

    class Card {
        +Long id
        +String titulo
        +String descricao
        +Date dataCriacao
        +boolean bloqueado
        +String motivoBloqueio
        +Long coluna_id
    }

    class CardHistory {
        +Long id
        +Long card_id
        +Long coluna_id
        +Date dataEntrada
        +Date dataSaida
    }

    Board "1" --> "many" Column : contém
    Column "1" --> "many" Card : contém
    Card "1" --> "many" CardHistory : possui histórico
    ```
