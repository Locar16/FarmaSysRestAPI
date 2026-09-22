# FarmaSys REST API

Projeto acadêmico da disciplina **POOW2** (Programação Orientada a Objetos para Web 2 — UFSM/CSI, prof. Alencar Machado).

API REST pura (sem front-end) para gestão de uma farmácia, com três CRUDs independentes:
**Remédios**, **Clientes** e **Fornecedores**. Documentação interativa via Swagger (Springdoc OpenAPI).

## Pré-requisitos

- **Java 17**
- **Maven** (ou use o wrapper `./mvnw` incluído no projeto)
- **PostgreSQL** rodando, com um banco de dados chamado `farmasys` já criado

## Como rodar

1. Crie o banco no PostgreSQL:
   ```sql
   CREATE DATABASE farmasys;
   ```
2. Ajuste usuário/senha em `src/main/resources/application.properties` se necessário
   (o padrão é `postgres` / `postgres`).
3. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```
   ou pelo IntelliJ IDEA, rodando a classe `FarmaSysApplication`.

As migrations do Flyway (`V1`, `V2`, `V3`) criam as tabelas automaticamente no primeiro start.

## URLs importantes

- **Swagger UI:** http://localhost:8080/farmasys/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/farmasys/api-docs

> O context-path da aplicação é `/farmasys`.

## Endpoints principais

### Remédios (`/remedio`)
| Verbo  | Rota                    | Descrição                       |
|--------|-------------------------|---------------------------------|
| GET    | `/remedio/listar`       | Lista todos os remédios         |
| GET    | `/remedio/{id}`         | Busca remédio por ID            |
| POST   | `/remedio`              | Cria um novo remédio            |
| PUT    | `/remedio`              | Atualiza um remédio             |
| DELETE | `/remedio/{id}`         | Exclui remédio por ID           |
| GET    | `/remedio/uuid/{uuid}`  | Busca remédio por UUID          |
| PUT    | `/remedio/uuid`         | Atualiza remédio por UUID       |
| DELETE | `/remedio/uuid/{uuid}`  | Exclui remédio por UUID         |

### Clientes (`/cliente`)
| Verbo  | Rota                    | Descrição                       |
|--------|-------------------------|---------------------------------|
| GET    | `/cliente/listar`       | Lista todos os clientes         |
| GET    | `/cliente/{id}`         | Busca cliente por ID            |
| POST   | `/cliente`              | Cria um novo cliente            |
| PUT    | `/cliente`              | Atualiza um cliente             |
| DELETE | `/cliente/{id}`         | Exclui cliente por ID           |
| GET    | `/cliente/uuid/{uuid}`  | Busca cliente por UUID          |
| PUT    | `/cliente/uuid`         | Atualiza cliente por UUID       |
| DELETE | `/cliente/uuid/{uuid}`  | Exclui cliente por UUID         |

### Fornecedores (`/fornecedor`)
| Verbo  | Rota                       | Descrição                    |
|--------|----------------------------|------------------------------|
| GET    | `/fornecedor/listar`       | Lista todos os fornecedores  |
| GET    | `/fornecedor/{id}`         | Busca fornecedor por ID      |
| POST   | `/fornecedor`              | Cria um novo fornecedor      |
| PUT    | `/fornecedor`              | Atualiza um fornecedor       |
| DELETE | `/fornecedor/{id}`         | Exclui fornecedor por ID     |
| GET    | `/fornecedor/uuid/{uuid}`  | Busca fornecedor por UUID    |
| PUT    | `/fornecedor/uuid`         | Atualiza fornecedor por UUID |
| DELETE | `/fornecedor/uuid/{uuid}`  | Exclui fornecedor por UUID   |

## Stack

- Spring Boot (Web MVC, Data JPA)
- Flyway (migrations)
- PostgreSQL
- Lombok
- Springdoc OpenAPI (Swagger UI)
