# Sistema Alexandria - Arquitetura Microkernel

Sistema de gerenciamento de biblioteca implementado usando arquitetura de microkernel com JavaFX e MariaDB.

## 🚀 Execução do Projeto

### Pré-requisitos
- **Java 24+**
- **Maven 3.6+**
- **Docker** e **Docker Compose**

### Comandos de Execução

```bash
# 1. Navegar para o diretório do projeto
cd microkernel

# 2. Iniciar o banco de dados
docker-compose up -d

# 3. Compilar o projeto
mvn clean install

# 4. Executar a aplicação
mvn -pl app exec:java
```

**Configurações do Banco:**
- Host: localhost:3307
- Usuário: root / Senha: root
- Database: bookstore

## 📖 Visão Geral

O Sistema Alexandria é uma aplicação JavaFX para gerenciamento de bibliotecas que utiliza uma arquitetura de microkernel para fornecer flexibilidade e extensibilidade através de plugins. O sistema permite o gerenciamento completo de usuários, livros, empréstimos e relatórios através de uma interface gráfica moderna e intuitiva.

### Vídeo demonstrativo: https://youtu.be/-rZAwp0MTIo

### Características Principais
- **Arquitetura Microkernel**: Core mínimo com funcionalidades extensíveis
- **Interface JavaFX**: Interface moderna e responsiva
- **Banco MariaDB**: Persistência robusta com Hibernate
- **Sistema de Plugins**: Módulos independentes e intercambiáveis
- **Validação Completa**: Validação de dados em todas as operações

## 🏗️ Estrutura do Projeto

```
microkernel/
├── app/                    # Core da aplicação (microkernel)
├── interfaces/             # Interfaces compartilhadas
├── models/                 # Modelos de dados JPA
├── plugins/               # Plugins do sistema
│   ├── bookplugin/        # Gerenciamento de livros
│   ├── userplugin/        # Gerenciamento de usuários
│   ├── loanplugin/        # Controle de empréstimos
│   └── reportplugin/      # Relatórios e estatísticas
├── docker-compose.yml     # Configuração do banco
└── pom.xml               # Configuração Maven parent
```

## 🔌 Plugins Disponíveis

| Plugin           | Funcionalidades                                    |
|------------------|----------------------------------------------------|
| **BookPlugin**   | CRUD de livros, busca avançada, controle de cópias |
| **UserPlugin**   | CRUD de usuários                                   |
| **LoanPlugin**   | Empréstimos, devoluções, histórico de empréstimo   |
| **ReportPlugin** | Rankings, relatórios, exportação CSV               |

## 🗄️ Modelo de Dados

```sql
-- Usuários
CREATE TABLE User (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Livros
CREATE TABLE Book (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    publication_year INT,
    copies_available INT DEFAULT 1
);

-- Empréstimos
CREATE TABLE Loan (
    loan_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    loan_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (book_id) REFERENCES Book(book_id)
);
```

## 🎨 Interface do Usuário

- **Design Responsivo**: Tema Alexandria customizado
- **Navegação por Abas**: Cada plugin em sua própria aba
- **Sistema de Mensagens**: Feedback visual padronizado
- **Tabelas Interativas**: Seleção e operações CRUD
- **Formulários Validados**: Validação em tempo real

## 🔧 Comandos Úteis

```bash
# Acesso direto ao banco
docker exec -it bookstore-db mariadb -u root -proot bookstore

# Logs do banco
docker logs bookstore-db

# Compilar plugin específico
cd plugins/bookplugin && mvn clean package

# Parar o banco
docker-compose down
```

## 📚 Links Relacionados

- [📖 README Principal](../README.md)
- [📚 Plugin de Livros](plugins/bookplugin/README.md)
- [👥 Plugin de Usuários](plugins/userplugin/README.md)
- [📋 Plugin de Empréstimos](plugins/loanplugin/README.md)
- [📊 Plugin de Relatórios](plugins/reportplugin/README.md)
---

**Desenvolvido por:** Marcus Vinicius Silva da Fonseca  
**Disciplina:** INF008 - POO  
**Instituição:** IFBA
