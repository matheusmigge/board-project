## Projeto para o Bootcamp Dio Decola Tech 2025 - Board Project - Estilo Trello
Projeto de um board de gerenciamento de tarefas, inspirado no Trello, desenvolvido como parte do portfólio. A aplicação permite organizar tarefas em colunas dentro de quadros, acompanhar o histórico de movimentação dos cards e gerenciar bloqueios de tarefas.

## Principais Tecnologias

- **Java 17**: Utilizado para garantir compatibilidade e desempenho otimizado.
- **Spring Boot 3**: Framework para desenvolvimento rápido e eficiente de aplicações Java.
- **Spring Data JPA**: Facilita a interação com bancos de dados SQL, abstraindo a camada de persistência.
- **H2 Database**: Banco de dados em memória para testes e desenvolvimento rápido.
- **Lombok**: Reduz a verbosidade do código ao gerar automaticamente getters, setters e construtores.

## Estrutura do Projeto
A aplicação segue uma estrutura baseada em entidades e relacionamentos, permitindo organizar tarefas dentro de um Board, que contém colunas e cards. Cada card pode ser movido entre colunas e possui um histórico de movimentações.

## Modelos Principais
- Board: Representa um quadro de tarefas.
- BoardColumn: Representa colunas dentro de um board (Ex: "A Fazer", "Em Andamento", "Concluído").
- Card: Representa as tarefas dentro das colunas, podendo ser bloqueadas e movidas.
- CardHistory: Registra o histórico de movimentação dos cards entre colunas.

## Instalação e Execução

1. Clone o repositório:
   ```bash
    git clone https://github.com/matheusmigge/board-project
   ```
2. Compile e execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

## Contribuição

Contribuições são bem-vindas! Para sugerir melhorias, abra uma issue ou envie um pull request.

---

## Diagrama de classes:

```mermaid
classDiagram
    class Board {
        +Long id
        +String nome
        +List~BoardColumn~ columns
    }

    class BoardColumn {
        +Long id
        +String nome
        +int ordem
        +String tipo
        +Board board
        +List~Card~ cards
    }

    class Card {
        +Long id
        +String titulo
        +String descricao
        +LocalDateTime dataCriacao
        +boolean bloqueado
        +String motivoBloqueio
        +BoardColumn column
        +List~CardHistory~ history
        +setBlocked(boolean, String)
        +boolean isBlocked()
    }

    class CardHistory {
        +Long id
        +LocalDateTime dataEntrada
        +LocalDateTime dataSaida
        +Card card
        +BoardColumn column
    }

    Board "1" --> "many" BoardColumn : contém
    BoardColumn "1" --> "many" Card : contém
    Card "1" --> "many" CardHistory : possui histórico

    ```
