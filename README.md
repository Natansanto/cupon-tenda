Microsserviço

## Desenvolvimento local

- Swagger: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console (JDBC `jdbc:h2:mem:cupondb`, usuário `tenda`, senha vazia)

## Docker

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
