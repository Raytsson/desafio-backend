# PicPay Simplificado - Desafio Backend

API RESTful para simulação de transferências financeiras entre usuários e lojistas.

---

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 3.5**
- **Spring Data JPA + Hibernate**
- **Flyway** — migrations versionadas
- **MySQL / MariaDB**
- **Lombok**
- **JUnit 5 + Mockito**

---

## 🏗️ Arquitetura

O projeto foi desenvolvido seguindo a **Arquitetura Hexagonal (Ports & Adapters)**, com o objetivo de manter o domínio isolado de frameworks e dependências externas.

```
src/
├── adapters/           # Controllers REST, DTOs, Exception Handlers
├── application/        # Casos de uso e interfaces de serviços externos
├── domain/             # Entidades, regras de negócio e interfaces de repositórios
└── infrastructure/     # Implementações JPA, mappers e clientes HTTP externos
```

### Por que Arquitetura Hexagonal?

- O domínio não depende de frameworks (sem `@Entity`, sem Spring no domínio)
- Fácil troca de banco de dados sem tocar nas regras de negócio
- Testabilidade — casos de uso testados com mocks sem subir o Spring
- Separação clara de responsabilidades

---

## ⚙️ Como rodar

### Pré-requisitos

- Java 21
- MySQL ou MariaDB rodando localmente
- Maven

### Configuração

1. Crie o banco de dados:
```sql
CREATE DATABASE picpay;
```

2. Configure as variáveis de ambiente:
```
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
```

3. Clone o repositório e rode:
```bash
./mvnw spring-boot:run
```

O Flyway criará as tabelas automaticamente na primeira execução.

---

## 📌 Endpoint

### POST /transfers

Realiza uma transferência entre dois usuários.

**Request:**
```json
{
  "value": 100.0,
  "payer": "uuid-do-pagador",
  "payee": "uuid-do-recebedor"
}
```

**Response:**
```json
{
  "id": "uuid-da-transferencia",
  "value": 100.0,
  "status": "COMPLETED",
  "transferTime": "2026-05-04T10:23:59"
}
```

**Status de erro:**
| Status | Descrição |
|--------|-----------|
| 403 | Lojista tentando realizar transferência |
| 404 | Usuário não encontrado |
| 422 | Saldo insuficiente |
| 500 | Erro interno / autorizador negou |

---

## 🔄 Fluxo de Transferência

1. Busca pagador e recebedor pelo ID
2. Valida se o pagador é do tipo `COMMON` (lojistas não transferem)
3. Persiste a transferência com status `PENDING`
4. Consulta serviço autorizador externo
5. Se negado → status `FAILED` + exceção
6. Se aprovado → debita do pagador, credita no recebedor
7. Atualiza status para `COMPLETED`
8. Envia notificação (falha na notificação não reverte a transferência)

---

## 🧪 Testes

```bash
./mvnw test
```

Cobertura de testes unitários no `TransferUseCase`:
- ✅ Transferência bem sucedida
- ✅ Lojista tentando transferir
- ✅ Saldo insuficiente
- ✅ Autorizador negando transferência

---

## 💡 Decisões Técnicas

**UUID como identificador:** Optei por UUID em vez de Long para os IDs por questões de segurança — IDs sequenciais são facilmente enumeráveis.

**Wallet como entidade separada:** O saldo foi extraído para uma entidade `Wallet` separada, tornando o domínio mais coeso e facilitando futuras evoluções (ex: múltiplas carteiras por usuário).

**Notificação assíncrona:** A falha no serviço de notificação não reverte a transferência — o dinheiro já foi movimentado com sucesso. O erro é logado mas não propaga.

**`@Transactional` no caso de uso:** Garante que todas as operações de banco ocorram em uma única transação, revertendo automaticamente em caso de falha.

---