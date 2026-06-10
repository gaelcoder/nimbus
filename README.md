# Nimbus: Microservices Banking System with Spring Boot, AWS and Automation

Nimbus é um sistema bancário baseado em microsserviços desenvolvido para demonstrar arquitetura moderna de backend utilizando Java, Spring Boot, Docker, PostgreSQL, AWS e automações Python.

O projeto foi concluído com foco em boas práticas de desenvolvimento, comunicação assíncrona entre serviços, segurança com JWT e integração com serviços cloud da AWS.

Todo o ambiente pode ser executado localmente utilizando Docker Compose, permitindo um ambiente consistente e de fácil reprodução.

---

# ✨ Features

## 🔐 Auth Service
- Registro de usuários
- Login com autenticação JWT
- Geração e validação de tokens
- Spring Security

## 🏦 Account Service
- Criação de contas bancárias
- Consulta de contas por ID
- Consulta de contas por usuário
- Controle de status da conta

## 💸 Transaction Service
- Criação de transferências
- Histórico de transações
- Consulta por ID
- Controle de status da transação
- Publicação de eventos via SQS

## 🔔 Notification Service
- Consumo de eventos via SQS
- Registro de notificações
- Histórico de notificações
- Persistência de auditoria em DynamoDB

---

# 📦 Arquitetura

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

# 🔄 Fluxo de Eventos

```text
Transaction Service
    ↓ (SQS)
Notification Service
    ↓
DynamoDB (audit)
    ↓
S3 (reports)
    ↓
Python Automation
    ↓
AWS Lambda (report generator)
```

---

# 🚀 Tecnologias Utilizadas

## Backend
- Java 21
- Spring Boot 3.3
- Spring Data JPA
- Spring Security
- JWT Authentication

## Banco de Dados
- PostgreSQL 16
- DynamoDB (audit logs)

## Mensageria
- AWS SQS
- AWS SNS

## Storage
- AWS S3

## Serverless
- AWS Lambda

## Automação
- Python (pandas, boto3)

## Infraestrutura
- Docker
- Docker Compose

## Testes
- JUnit 5
- Mockito

---

# 🐳 Execução Local

```bash
docker compose up -d
```

Depois execute cada serviço:

```bash
mvn spring-boot:run
```

---

# 📖 Swagger

- Auth: http://localhost:8080/swagger-ui/index.html  
- Account: http://localhost:8081/swagger-ui/index.html  
- Transaction: http://localhost:8082/swagger-ui/index.html  
- Notification: http://localhost:8083/swagger-ui/index.html  

---

# 🧪 Testes

```bash
mvn clean test
```

---

# 🏁 Status do Projeto

✔ Microsserviços implementados  
✔ Comunicação assíncrona via SQS  
✔ Auditoria com DynamoDB  
✔ Storage com S3  
✔ Automação com Python  
✔ Processamento com AWS Lambda  
✔ Docker Compose  
✔ Segurança com JWT  

---

# 👨‍💻 Autor

Gabriel Azevedo  
GitHub: https://github.com/gaelcoder  
LinkedIn: https://www.linkedin.com/in/gabrielsaz/
