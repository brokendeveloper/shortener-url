# Shortener URL

Um encurtador de URLs moderno, robusto e documentado, com suporte a múltiplas versões de API, métricas de acesso e pronto para rodar tanto localmente quanto em ambiente Docker. Feito para o teste técnico da vaga de backend JR da empresa TDS Company.

---

## Sumário

- [Funcionalidades](#funcionalidades)
- [Endpoints](#endpoints)
- [Como rodar localmente](#como-rodar-localmente)
- [Como rodar com Docker](#como-rodar-com-docker)
- [Testes](#testes)
- [Documentação Swagger](#documentação-swagger)
- [Configurações](#configurações)
- [Contato](#contato)

---

## Funcionalidades

- Encurtamento de URLs (v1 e v2)
- Redirecionamento para a URL original
- Métricas de acesso (total e média por dia)
- API documentada com Swagger
- Pronto para rodar em ambiente local ou Docker

---

## Endpoints

### v1

- **Encurtar URL**
    - `POST /api/v1/urls/shorten`
    - Body: `{ "originalUrl": "https://www.youtube.com/" }`
    - Resposta: `{ "shortenedUrl": "http://localhost:8080/abc123" }`

### v2

- **Encurtar URL**
    - `POST /api/v2/urls/shorten`
    - Body: `{ "originalUrl": "https://www.youtube.com/" }`
    - Resposta: `{ "shortenedUrl": "http://localhost:8080/XYZ9876543" }`

### Redirecionamento

- `GET /{shortCode}`
    - Exemplo: `GET /abc123`
    - Redireciona (HTTP 302) para a URL original

### Métricas de acesso

- `GET /api/urls/{shortCode}/access`
    - Exemplo: `GET /api/urls/abc123/access`
    - Resposta:
      ```json
      {
        "totalAccesses": 1,
        "avgPerDay": 1.0
      }
      ```

---

## Como rodar localmente

1. **Pré-requisitos**
    - Java 17+
    - Maven
    - PostgreSQL rodando localmente (ajuste as configs em `application.yml` se necessário)

2. **Clone o projeto**
   ```bash
   git clone <url-do-repo>
   cd shortener-url

3. **Configure o banco de dados**

   Certifique-se de que o Postgres está rodando na porta e com as credenciais do `application.yml`.

4. **Build e execute**
   ```bash
   ./mvnw clean package
   java -jar target/*.jar
   ```

5. **Acesse**
   - **API**: `http://localhost:8080`
   - **Swagger**: `http://localhost:8080/swagger-ui/index.html`

---

## Como rodar com Docker

1. **Pré-requisitos**
    - Docker e Docker Compose instalados

2. **Build do projeto**
   ```bash
   ./mvnw clean package -DskipTests
   ```

3. **Suba os containers**
   ```bash
   docker-compose up --build
   ```

4. **Acesse**
   - **API**: `http://localhost:8080`
   - **Swagger**: `http://localhost:8080/swagger-ui/index.html`

---

## Testes

Para rodar os testes unitários e de integração:

```bash
./mvnw test
```

---

## Exemplos de uso (cURL)

### Encurtar URL (v1)
```bash
curl -X POST http://localhost:8080/api/v1/urls/shorten \
-H "Content-Type: application/json" \
-d '{"originalUrl": "https://www.youtube.com/"}'
```

### Encurtar URL (v2)
```bash
curl -X POST http://localhost:8080/api/v2/urls/shorten \
-H "Content-Type: application/json" \
-d '{"originalUrl": "https://www.youtube.com/"}'
```

### Redirecionar
```bash
curl -v http://localhost:8080/abc123
```

### Consultar métricas
```bash
curl http://localhost:8080/api/urls/abc123/access
```

---

## Documentação Swagger

Acesse a documentação interativa em:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Configurações

- **Ambiente local**: Editar `src/main/resources/application.yml`
- **Ambiente Docker**: Editar `src/main/resources/application-docker.yml` (O profile `docker` é ativado automaticamente pelo Compose)

---

## Contato

Dúvidas, sugestões ou bugs?
Abra uma issue ou entre em contato pelo [e-mail](mailto:contatoluccasf9@gmail.com).

