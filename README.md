# Encurtador de URL

Este projeto é um simples encurtador de URL criado como um teste técnico para a empresa TDS.

## Descrição

A aplicação recebe uma URL longa e gera um alias único e mais curto. Quando um usuário acessa o alias curto, ele é redirecionado para a URL longa original.

## Tecnologias Utilizadas

*   **Java 17**
*   **Spring Boot 3.5.3**
*   **Spring Web**
*   **Spring Data JPA**
*   **Maven**
*   **PostgreSQL**
*   **Lombok**

## Como Executar

1.  **Pré-requisitos:**
    *   Java 17 ou superior
    *   Maven
    *   Uma instância do PostgreSQL em execução

2.  **Configuração:**
    *   No diretório `src/main/resources`, você encontrará um arquivo chamado `application.example.yml`.
    *   Crie uma cópia deste arquivo e renomeie para `application.yml`.
    *   Abra o novo arquivo `application.yml` e atualize as propriedades `spring.datasource` (url, username, password) para corresponder à sua configuração do PostgreSQL.

3.  **Execução:**
    *   Clone o repositório.
    *   Execute a aplicação usando o seguinte comando Maven na raiz do projeto:
        ```bash
        ./mvnw spring-boot:run
        ```

## Modelo de Branch

A branch `main` é usada para a configuração inicial deste projeto. Todo o desenvolvimento contínuo é feito na branch `developer`.

## Endpoints da API

(Os detalhes dos endpoints da API serão adicionados aqui.)