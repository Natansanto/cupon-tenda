## Considerações - projeto cupon-tenda

* Foi utilizado o banco de dados H2 (em memória no ambiente local e em arquivo no Docker)
* Foi utilizado Java 17
* Foi utilizado Spring Boot 4.0.6
* Foi utilizado JUnit 5 (via spring-boot-starter-test)
* Foi utilizado Lombok
* Foi utilizado MapStruct para mapeamento entre API, domínio e persistência
* Foi utilizado Swagger / OpenAPI (SpringDoc) — documentação em http://localhost:8080/swagger-ui.html
* Foi utilizado Spring Data JPA e Hibernate para persistência
* Foi utilizado Flyway para versionamento do schema (V1, V2)
* Foi utilizado Bean Validation (jakarta.validation) nas requisições da API
* Foi utilizada arquitetura em camadas: domínio, application (use cases/services), infrastructure (API REST, JPA, adapters)
* Endpoints implementados:
*   POST /cupom — criar cupom (status 201)
*   DELETE /cupom/{id} — exclusão lógica (soft delete, status 204)
* Regras de negócio no domínio (Cupom): código sanitizado (6 caracteres), desconto mínimo, data de expiração futura, resgate e exclusão lógica
* Foram feitos testes unitários (services, mappers, repositório) e teste de integração para o fluxo de deleção
* Foi configurado JaCoCo para relatório de cobertura (mvn verify → target/site/jacoco/index.html)
* Foi configurado Docker e Docker Compose na pasta devops/ (container: cupon-tenda-app, porta 8080)

<img width="1526" height="820" alt="image" src="https://github.com/user-attachments/assets/531addcf-1397-471a-82a8-5588000dde08" />
<img width="1428" height="128" alt="image" src="https://github.com/user-attachments/assets/e1a94541-14e4-434b-8795-02d30a0d61ca" />


# Testes 

<img width="1161" height="581" alt="image" src="https://github.com/user-attachments/assets/d7c58aa8-dfc1-489d-abb8-5d963f1bf10b" />
<img width="1316" height="317" alt="image" src="https://github.com/user-attachments/assets/a52c261e-a527-4054-8144-e8b41a80caf9" />



# Desenvolvimento local

- Swagger: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console (JDBC `jdbc:h2:mem:cupondb`, usuário `tenda`, senha vazia)

# Docker

Arquivos em [`devops/`](devops/): `Dockerfile`, `docker-compose.yml`.

Pré-requisitos: [Docker](https://docs.docker.com/get-docker/) e [Docker Compose](https://docs.docker.com/compose/install/).

Sobe apenas a aplicação, com **H2 em arquivo** (dados persistem no volume `h2_data`):

```bash
docker compose -f devops/docker-compose.yml up --build
```

Ou, a partir da pasta `devops`:

```bash
cd devops
docker compose up --build
```

- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console (JDBC `jdbc:h2:file:/data/cupondb`, usuário `tenda`, senha vazia)

### Comandos úteis

```bash
docker compose -f devops/docker-compose.yml up -d --build
docker compose -f devops/docker-compose.yml down
docker compose -f devops/docker-compose.yml down -v   
```
