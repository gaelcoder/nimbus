# Nimbus Bank: Microservices Banking System with Spring Boot, AWS and Automation

Nimbus é um sistema bancário baseado em microsserviços desenvolvido para demonstrar arquitetura moderna de backend utilizando Java, Spring Boot, Docker, PostgreSQL, AWS e automações Python.

O projeto foi criado com foco em boas práticas de desenvolvimento, comunicação assíncrona entre serviços, segurança com JWT e integração com serviços cloud da AWS.

Todo o ambiente pode ser executado localmente utilizando Docker Compose, permitindo um ambiente consistente e de fácil reprodução.

---

# ✨ Features

### 🔐 Auth Service

- Registro de usuários
- Login com autenticação JWT
- Geração e validação de tokens
- Spring Security

### 🏦 Account Service

- Criação de contas bancárias
- Consulta de contas por ID
- Consulta de contas por usuário
- Controle de status da conta

### 💸 Transaction Service

- Criação de transferências
- Histórico de transações
- Consulta por ID
- Controle de status da transação

### 🔔 Notification Service

- Registro de notificações
- Consulta de notificações
- Histórico de notificações

### 📖 Documentação

- Swagger/OpenAPI
- Endpoints documentados

### 🧪 Qualidade

- Testes unitários com JUnit e Mockito
- Arquitetura em camadas
- DTO Pattern
- Exception Handling

### 🐳 Infraestrutura

- PostgreSQL
- Docker
- Docker Compose

---

# 🚀 Tecnologias Utilizadas

### Backend

- Java 21
- Spring Boot 3.3
- Spring Data JPA
- Spring Security
- JWT Authentication

### Banco de Dados

- PostgreSQL 16

### Documentação

- SpringDoc OpenAPI (Swagger)

### Testes

- JUnit 5
- Mockito

### Ambiente

- Docker
- Docker Compose

### Utilitários

- Lombok
- Maven

### Próximas Integrações

- AWS SQS
- AWS SNS
- AWS DynamoDB
- AWS S3
- AWS Lambda
- Python Automation

---

# 🏛️ Arquitetura

```text
Nimbus

├── auth-service
├── account-service
├── transaction-service
├── notification-service

├── shared
├── infrastructure
└── python-automation
```

---

# 🔄 Fluxo Atual

```text
Client
   |
   v
Auth Service
   |
   v
Account Service
   |
   v
Transaction Service
   |
   v
Notification Service
```

---

# 📌 Endpoints da API

## 🔐 Auth Service

Base URL:

```text
/api/v1/auth
```

| Verbo HTTP | Endpoint | Descrição |
|------------|----------|------------|
| POST | `/register` | Registro de usuário |
| POST | `/login` | Login e geração de JWT |

---

## 🏦 Account Service

Base URL:

```text
/api/v1/accounts
```

| Verbo HTTP | Endpoint | Descrição |
|------------|----------|------------|
| POST | `/` | Criação de conta |
| GET | `/{id}` | Busca conta por ID |
| GET | `/user/{userId}` | Busca contas de um usuário |

---

## 💸 Transaction Service

Base URL:

```text
/api/v1/transactions
```

| Verbo HTTP | Endpoint | Descrição |
|------------|----------|------------|
| POST | `/` | Criar transação |
| GET | `/{id}` | Buscar transação por ID |
| GET | `/history` | Buscar histórico de transações |

Parâmetros:

```text
sourceAccountId
destinationAccountId
```

---

## 🔔 Notification Service

Base URL:

```text
/api/v1/notifications
```

| Verbo HTTP | Endpoint | Descrição |
|------------|----------|------------|
| POST | `/` | Criar notificação |
| GET | `/{id}` | Buscar notificação por ID |
| GET | `/user/{userId}` | Buscar notificações do usuário |

---

# ⚙️ Como Executar o Projeto

## Método 1: Docker (Recomendado)

Esta abordagem executa toda a infraestrutura do banco utilizando containers Docker.

### Pré-requisitos

- Docker Desktop
- Docker Compose
- Java 21

---

### 1. Clone o repositório

```bash
git clone https://github.com/gaelcoder/Nimbus.git
```

---

### 2. Entre na pasta

```bash
cd Nimbus
```

---

### 3. Suba os containers

```bash
docker compose up -d
```

Verificar containers:

```bash
docker ps
```

---

### 4. Execute os microsserviços

Auth Service:

```bash
cd auth-service
mvn spring-boot:run
```

Account Service:

```bash
cd account-service
mvn spring-boot:run
```

Transaction Service:

```bash
cd transaction-service
mvn spring-boot:run
```

Notification Service:

```bash
cd notification-service
mvn spring-boot:run
```

---

# 🐘 PostgreSQL

Banco configurado via Docker.

Credenciais padrão:

```yaml
database: nimbus

username: postgres

password: postgres

port: 5432
```

---

# 📖 Swagger

Após iniciar os serviços:

### Auth Service

```text
http://localhost:8080/swagger-ui/index.html
```

### Account Service

```text
http://localhost:8081/swagger-ui/index.html
```

### Transaction Service

```text
http://localhost:8082/swagger-ui/index.html
```

### Notification Service

```text
http://localhost:8083/swagger-ui/index.html
```

---

# 🧪 Como Executar os Testes

Executar todos os testes:

```bash
mvn test
```

Ou:

```bash
mvn clean test
```

---

# 📂 Estrutura do Projeto

```text
Nimbus

├── auth-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── security
│   ├── config
│   ├── exception
│   └── dto
│
├── account-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── exception
│   ├── entity
│   └── dto
│
├── transaction-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── exception
│   ├── entity
│   └── dto
│
├── notification-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── exception
│   ├── entity
│   └── dto
│
├── shared
├── infrastructure
├── python-automation
└── docker-compose.yml
```

---

# 🛣️ Roadmap

## Concluído

- [x] Auth Service
- [x] JWT Authentication
- [x] Spring Security
- [x] Account Service
- [x] Transaction Service
- [x] Notification Service
- [x] PostgreSQL
- [x] Docker
- [x] Docker Compose
- [x] Swagger/OpenAPI
- [x] Testes Unitários

## Próximas Etapas

- [ ] AWS SQS
- [ ] AWS SNS
- [ ] DynamoDB Audit Logs
- [ ] S3 Report Storage
- [ ] AWS Lambda
- [ ] Python Automation
- [ ] Deploy AWS

---

# 👨‍💻 Autor

Projeto desenvolvido por **Gabriel Azevedo**.

Desenvolvido para estudo e demonstração de conhecimentos em:

- Backend Java
- Spring Boot
- Microsserviços
- AWS
- Docker
- Automação Python

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/gabrielsaz/)

[![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/gaelcoder)
