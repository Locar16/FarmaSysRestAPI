# FarmaSys REST API

Projeto acadêmico da disciplina **POOW2** (Programação Orientada a Objetos para Web 2 — UFSM/CSI, prof. Alencar Machado).

API REST pura (sem front-end) para gestão de uma farmácia, com quatro recursos principais —
**Remédios**, **Clientes**, **Fornecedores** e **Vendas** — relacionados entre si: cada remédio
pode ter um fornecedor, cada venda pode ter um cliente e é composta por itens de venda, que
referenciam os remédios vendidos. Documentação interativa via Swagger (Springdoc OpenAPI).

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

As migrations do Flyway (`V1` a `V6`) criam as tabelas e os relacionamentos automaticamente no primeiro start.

## Modelo de dados

5 tabelas: `remedios`, `clientes`, `fornecedores`, `vendas` e `itens_venda`.

- `fornecedores` 1 ── N `remedios` (cada remédio pode ter um fornecedor, opcional)
- `clientes` 1 ── N `vendas` (cada venda pode ter um cliente, opcional)
- `vendas` 1 ── N `itens_venda` (uma venda tem vários itens, gerenciados via cascade)
- `remedios` 1 ── N `itens_venda` (cada item referencia o remédio vendido)

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

### Vendas (`/venda`)
| Verbo  | Rota                    | Descrição                                          |
|--------|-------------------------|-----------------------------------------------------|
| GET    | `/venda/listar`         | Lista todas as vendas (com cliente e itens)          |
| GET    | `/venda/{id}`           | Busca venda por ID                                   |
| POST   | `/venda`                | Registra uma nova venda (baixa o estoque)            |
| PUT    | `/venda`                | Atualiza cliente/forma de pagamento de uma venda     |
| DELETE | `/venda/{id}`           | Exclui venda por ID (devolve o estoque)              |
| GET    | `/venda/uuid/{uuid}`    | Busca venda por UUID                                 |
| PUT    | `/venda/uuid`           | Atualiza venda por UUID                              |
| DELETE | `/venda/uuid/{uuid}`    | Exclui venda por UUID (devolve o estoque)             |

## Exemplos de corpo JSON

```json
// POST /remedio  (fornecedor opcional)
{
  "nome": "Dipirona 500mg",
  "principioAtivo": "Dipirona sódica",
  "preco": 12.90,
  "quantidadeEstoque": 150,
  "necessitaReceita": false,
  "fornecedor": { "id": 1 }
}
```

```json
// POST /venda  (cliente opcional; total, data e preço unitário são calculados)
{
  "formaPagamento": "PIX",
  "cliente": { "id": 1 },
  "itens": [
    { "remedio": { "id": 1 }, "quantidade": 2 }
  ]
}
```

## Stack

- Spring Boot (Web MVC, Data JPA)
- Flyway (migrations)
- PostgreSQL
- Lombok
- Springdoc OpenAPI (Swagger UI)

## Regras de negócio das vendas

- Ao registrar uma venda, o estoque de cada remédio é verificado e baixado automaticamente.
- Ao excluir uma venda, o estoque dos remédios vendidos é devolvido.
- Um remédio que já foi vendido (possui item de venda associado) não pode ser excluído.
