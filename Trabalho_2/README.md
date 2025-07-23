# Sistema Alexandria - Arquitetura Microkernel

Sistema de gerenciamento de biblioteca implementado usando arquitetura de microkernel, desenvolvido como trabalho acadêmico para a disciplina INF008 - Engenharia de Software.

## 📖 Visão Geral

O Sistema Alexandria é uma aplicação JavaFX para gerenciamento de bibliotecas que utiliza uma arquitetura de microkernel para fornecer flexibilidade e extensibilidade através de plugins. O sistema permite o gerenciamento completo de usuários, livros, empréstimos e relatórios através de uma interface gráfica moderna e intuitiva.

### Características Principais

- **Arquitetura Microkernel**: Core mínimo com funcionalidades extensíveis via plugins
- **Interface Gráfica JavaFX**: Interface moderna e responsiva
- **Banco de Dados MariaDB**: Persistência robusta e confiável
- **Sistema de Plugins**: Módulos independentes e intercambiáveis
- **Validação de Dados**: Validação completa em todas as operações
- **Mensagens de Feedback**: Sistema consistente de notificações ao usuário

## 🏗️ Arquitetura

### Estrutura do Projeto

```
Trabalho_2/
├── microkernel/                    # Projeto principal
│   ├── app/                        # Aplicação core (microkernel)
│   ├── interfaces/                 # Interfaces compartilhadas
│   ├── plugins/                    # Plugins do sistema
│   │   ├── bookplugin/            # Plugin de gerenciamento de livros
│   │   ├── userplugin/            # Plugin de gerenciamento de usuários
│   │   ├── loanplugin/            # Plugin de gerenciamento de empréstimos
│   │   └── reportplugin/          # Plugin de relatórios
│   ├── docker-compose.yml         # Configuração do banco de dados
│   └── pom.xml                    # Configuração Maven principal
└── README.md                      # Este arquivo
```

### Princípios Arquiteturais

1. **Separação de Responsabilidades**: Cada plugin é responsável por um domínio específico
2. **Baixo Acoplamento**: Plugins se comunicam apenas através de interfaces bem definidas
3. **Alta Coesão**: Funcionalidades relacionadas agrupadas em plugins específicos
4. **Extensibilidade**: Novos plugins podem ser adicionados sem modificar o core
5. **Intercambiabilidade**: Plugins podem ser substituídos ou removidos independentemente

## 🚀 Como Compilar e Executar

### Pré-requisitos

- **Java 24** ou superior
- **Maven 3.6+**
- **Docker** e **Docker Compose** (para o banco de dados)
- **Git** (para clonar o repositório)

### 1. Configuração do Banco de Dados

```bash
# Navegar para o diretório do projeto
cd microkernel

# Iniciar o banco de dados MariaDB
docker-compose up -d

# Verificar se o container está rodando
docker ps
```

O banco de dados será iniciado na porta `3307` com as seguintes credenciais:
- **Host**: localhost
- **Porta**: 3307
- **Usuário**: root
- **Senha**: root
- **Banco**: bookstore

### 2. Compilação do Projeto

```bash
# Compilar todos os módulos (no diretório microkernel/)
mvn clean install

# Ou compilar individualmente:
# Interfaces
cd interfaces && mvn clean install && cd ..

# Plugins
cd plugins/userplugin && mvn clean package && cd ../..
cd plugins/bookplugin && mvn clean package && cd ../..
cd plugins/loanplugin && mvn clean package && cd ../..
cd plugins/reportplugin && mvn clean package && cd ../..

# Aplicação principal
cd app && mvn clean package && cd ..
```

### 3. Execução da Aplicação

```bash
# Navegar para o diretório da aplicação
cd app

# Executar a aplicação
java -jar target/executable-1.0-SNAPSHOT.jar

# Ou usar Maven
mvn exec:java
```

### 4. Usando VS Code Task

Se estiver usando VS Code, há uma task configurada para compilação:

```bash
# Ctrl+Shift+P > Tasks: Run Task > "Compilar e empacotar o projeto"
```

## 🔌 Plugins Disponíveis

### [📚 Book Plugin](microkernel/plugins/bookplugin/README.md)
Gerenciamento completo de livros:
- Cadastro, edição e exclusão de livros
- Busca por título, autor, ISBN ou ano
- Controle de cópias disponíveis
- Validação de dados e ISBN

### [👥 User Plugin](microkernel/plugins/userplugin/README.md)
Gerenciamento de usuários do sistema:
- Cadastro, edição e exclusão de usuários
- Busca por nome ou email
- Validação de email
- Confirmação de exclusão

### [📋 Loan Plugin](microkernel/plugins/loanplugin/README.md)
Controle de empréstimos:
- Registro de novos empréstimos
- Controle de devoluções
- Edição de empréstimos
- Validação de disponibilidade

### [📊 Report Plugin](microkernel/plugins/reportplugin/README.md)
Relatórios e estatísticas:
- Ranking de usuários mais ativos
- Livros mais emprestados
- Empréstimos ativos
- Exportação para CSV

## 🎨 Interface do Usuário

### Características da Interface

- **Design Responsivo**: Interface adaptável a diferentes tamanhos de tela
- **Tema Alexandria**: Estilização customizada com cores e fontes consistentes
- **Feedback Visual**: Mensagens de sucesso, erro e confirmação padronizadas
- **Navegação por Abas**: Cada plugin possui sua própria aba
- **Tabelas Interativas**: Seleção e edição direta nas tabelas

### Sistema de Mensagens

Todos os plugins seguem o mesmo padrão de mensagens:

- 🟢 **Sucesso**: Mensagens verdes para operações bem-sucedidas
- 🔴 **Erro**: Mensagens vermelhas para erros e validações
- 🟡 **Aviso**: Mensagens amarelas para confirmações e alertas
- ℹ️ **Informação**: Mensagens azuis para informações gerais

## 🗄️ Banco de Dados

### Modelo de Dados

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

### Acesso ao Banco

```bash
# Via cliente MariaDB do host
mariadb -h 127.0.0.1 -P 3307 -u root -proot --skip-ssl bookstore

# Via container Docker
docker exec -it bookstore-db mariadb -u root -proot bookstore
```

## 📝 Desenvolvimento

### Adicionando Novos Plugins

1. **Criar estrutura do plugin**:
```bash
mkdir plugins/myplugin
cd plugins/myplugin
# Copiar estrutura de um plugin existente
```

2. **Implementar interfaces obrigatórias**:
```java
public class MyPlugin implements ILibraryPlugin, IPluginUI {
    // Implementar métodos obrigatórios
}
```

3. **Adicionar ao pom.xml principal**:
```xml
<modules>
    <!-- outros módulos -->
    <module>plugins/myplugin</module>
</modules>
```

4. **Compilar e testar**:
```bash
mvn clean install
```

### Estrutura de um Plugin

```
myplugin/
├── pom.xml
├── README.md
└── src/
    ├── main/java/br/edu/ifba/inf008/plugins/
    │   ├── MyPlugin.java              # Classe principal
    │   ├── controller/
    │   │   └── MyController.java      # Controlador
    │   ├── service/
    │   │   └── MyService.java         # Serviços
    │   └── ui/
    │       └── components/
    │           └── MyMessageUtils.java # Utilitários UI
    ├── resources/
    │   ├── fxml/
    │   │   └── MyView.fxml            # Interface FXML
    │   └── styles/
    │       └── my-theme.css           # Estilos CSS
    └── test/java/                     # Testes
```
---

