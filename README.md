# 🌾 Projeto Sprig - Desenvolvimento Back-end

## 📖 Descrição Geral
Este repositório representa o back-end do sistema Sprig, desenvolvido com **Spring Boot + Java 17 + MySQL**.

O Sprig é uma plataforma completa criada para aprimorar o processo de controle de estoque, logística e distribuição de sementes, oferecendo uma API REST robusta, segura e escalável, destinada a gestores, técnicos e agricultores do programa.

## 🎯 O que foi desenvolvido no Back-end

### ✔️ Arquitetura Completa
- **API REST** com endpoints organizados por módulos
- **Autenticação JWT** com Spring Security
- **Validação de dados** com Bean Validation
- **Documentação automática** com Swagger/OpenAPI
- **Tratamento de exceções** global
- **Logs estruturados** para monitoramento

### ✔️ Módulos Implementados
- **Autenticação** - Login e controle de acesso
- **Usuários** - CRUD para Gestores, Técnicos e Agricultores
- **Estoque** - Controle de armazéns e lotes
- **Logística** - Gestão de rotas e entregas
- **Rastreabilidade** - Acompanhamento de entregas
- **Relatórios** - Geração de PDFs e métricas
- **Dashboard** - Métricas em tempo real

### ✔️ Funcionalidades Principais
- **Sistema de permissões** baseado em roles (GESTOR, TECNICO, AGRICULTOR)
- **Validação de CPF/CNPJ** com Hibernate Validator
- **Criptografia de senhas** com BCrypt
- **Otimização de rotas** com Google Maps API
- **Geração de códigos de rastreio** únicos
- **Alertas de estoque** (mínimo/máximo)
- **Relatórios em PDF** com iText/OpenPDF

## 🧱 Tecnologias Utilizadas

### Backend
- **Spring Boot 3.3.4**
- **Java 17**
- **Spring Security** + JWT
- **Spring Data JPA** + Hibernate
- **MySQL Connector**
- **SpringDoc OpenAPI** (Swagger)
- **Lombok**
- **Validation API**
- **JJWT** para tokens JWT
- **OpenPDF** para geração de relatórios

### Banco de Dados
- **MySQL 8.0+**
- **JPA/Hibernate** com herança JOINED
- **Índices otimizados** para consultas
- **Scripts de população** automática

## 🗂️ Estrutura do Projeto

```
sprig/
├── src/main/java/br/edu/pe/senac/pi_tads049/sprig/
│   ├── controllers/          # Controladores REST
│   ├── entidades/           # Entidades JPA
│   ├── repositorios/        # Repositórios Spring Data
│   ├── service/             # Lógica de negócio
│   ├── dto/                 # Data Transfer Objects
│   ├── security/            # Configurações de segurança
│   ├── config/              # Configurações gerais
│   └── SprigApplication.java
├── src/main/resources/
│   ├── application.properties
│   └── data.sql            # Dados iniciais
└── pom.xml
```

## 🚀 Como Rodar o Projeto

### 1. Pré-requisitos
- Java 17 ou superior
- MySQL 8.0+
- Maven 3.6+

### 2. Configurar Banco de Dados
```sql
CREATE DATABASE sprig;
CREATE USER 'sprig_user'@'localhost' IDENTIFIED BY 'sprig_password';
GRANT ALL PRIVILEGES ON sprig.* TO 'sprig_user'@'localhost';
```

### 3. Configurar Variáveis de Ambiente
Edite `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sprig
spring.datasource.username=user
spring.datasource.password=password
```

### 4. Executar o Projeto
```bash
# Instalar dependências
./mvnw clean install

# Executar aplicação
./mvnw spring-boot:run

# Ou executar diretamente
java -jar target/sprig-0.0.1-SNAPSHOT.jar
```

### 5. Acessar a Documentação
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Base**: http://localhost:8080/api

## 🔐 Autenticação e Segurança

### Roles do Sistema
- **GESTOR**: Acesso total ao sistema
- **TECNICO**: Gestão de logística e estoque
- **AGRICULTOR**: Apenas suas entregas e rastreamento

### Endpoints Principais
- `POST /auth/login` - Autenticação
- `POST /usuarios/cadastro` - Cadastro de usuários
- `GET /api/dashboard*` - Métricas do dashboard
- `GET /rastreabilidade/*` - Rastreamento de entregas

## 📊 Funcionalidades por Módulo

### Dashboard
- Métricas de entregas (concluídas, em rota, pendentes)
- Volume total entregue
- Tempo médio de entrega
- Custos e distâncias
- Alertas de estoque

### Estoque & Lotes
- Controle de armazéns e capacidade
- Gestão de lotes por espécie
- Validade e QR codes
- Alertas de vencimento
- Controle de quantidades

### Logística
- Criação e otimização de rotas
- Alocação de motoristas e veículos
- Cálculo de custos e distâncias
- Acompanhamento em tempo real

### Rastreabilidade
- Códigos únicos de rastreio
- Histórico de status
- Localização em tempo real
- Notificações de status

## 🔧 Configurações Avançadas

### Google Maps API (Opcional)
Para otimização de rotas, configure no `application.properties`:
```properties
google.maps.api.key=SUA_CHAVE_API
```

### JWT Configuration
```properties
jwt.secret=SUA_CHAVE_SECRETA_AQUI
jwt.expiration=86400000
```

## 📈 Próximos Passos

- [ ] Implementar notificações por email
- [ ] Adicionar cache com Redis
- [ ] Implementar filas com RabbitMQ
- [ ] Adicionar métricas com Micrometer
- [ ] Implementar testes unitários e de integração
- [ ] Configurar Docker e Docker Compose


## 👥 Equipe

Desenvolvido como projeto integrador.

Abigail Maria Nazário
Kallyne Melo
Sofia Leitão
Tamirys Maria

---

**Sprig** - Transformando a logística de sementes com tecnologia! 🌱
