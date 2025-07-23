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
microkernel/
├── app/                    # Aplicação principal (microkernel core)
├── interfaces/             # Interfaces compartilhadas entre plugins
├── plugins/               # Plugins do sistema
│   ├── bookplugin/        # → [Documentação](microkernel/plugins/bookplugin/README.md)
│   ├── userplugin/        # → [Documentação](microkernel/plugins/userplugin/README.md)  
│   ├── loanplugin/        # → [Documentação](microkernel/plugins/loanplugin/README.md)
│   └── reportplugin/      # → [Documentação](microkernel/plugins/reportplugin/README.md)
├── docker-compose.yml     # Configuração do banco MariaDB
├── init.sql              # Script de inicialização do banco
└── pom.xml               # Configuração Maven parent
```

### Princípios Arquiteturais

1. **Core Mínimo**: A aplicação principal contém apenas funcionalidades essenciais
2. **Plugins Independentes**: Cada plugin é um módulo Maven independente com baixo acoplamento
3. **Interfaces Bem Definidas**: Contratos padronizados para comunicação entre componentes
4. **Extensibilidade**: Novos plugins podem ser adicionados sem modificar o core

## 🚀 Como Compilar e Executar

### Pré-requisitos

- **Java 24+**
- **Maven 3.6+** 
- **Docker** e **Docker Compose**

### 1. Configuração do Banco de Dados

```bash
# Navegar para o diretório do projeto
cd microkernel

# Iniciar o banco de dados MariaDB
docker-compose up -d
```

**Configurações do Banco:**
- Host: localhost:3307
- Usuário: root / Senha: root
- Database: bookstore

### 2. Compilação e Execução

```bash
# Compilar todos os módulos
mvn clean install

# Executar a aplicação
mvn -pl app exec:java

```

## 🔌 Plugins Disponíveis

| Plugin | Descrição | Funcionalidades |
|--------|-----------|-----------------|
| [📚 BookPlugin](microkernel/plugins/bookplugin/README.md) | Gerenciamento de livros | CRUD, busca com filtros, controle de cópias, validação ISBN |
| [👥 UserPlugin](microkernel/plugins/userplugin/README.md) | Gerenciamento de usuários | CRUD, busca com filtros, validação email, confirmação de exclusão |
| [📋 LoanPlugin](microkernel/plugins/loanplugin/README.md) | Controle de empréstimos | Empréstimos, devoluções, edição, validações |
| [📊 ReportPlugin](microkernel/plugins/reportplugin/README.md) | Relatórios e estatísticas | Rankings, relatórios, exportação CSV |

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

### Características

- **Design Responsivo**: Interface adaptável com tema Alexandria customizado
- **Navegação por Abas**: Cada plugin possui sua própria aba
- **Sistema de Mensagens Padronizado**:
  - 🟢 **Sucesso**: Operações bem-sucedidas
  - 🔴 **Erro**: Erros e validações
  - 🟡 **Aviso**: Confirmações e alertas
  - ℹ️ **Informação**: Mensagens informativas

### Funcionalidades Comuns

- Tabelas interativas com seleção
- Formulários com validação em tempo real
- Busca avançada com múltiplos critérios
- Confirmação para operações críticas
- Feedback visual imediato

## 📝 Desenvolvimento de Plugins

### Estrutura Padrão

```
newplugin/
├── pom.xml                    # Configuração Maven
├── README.md                  # Documentação do plugin
└── src/main/java/br/edu/ifba/inf008/plugins/
    ├── NewPlugin.java         # Classe principal (ILibraryPlugin, IPluginUI)
    ├── controller/            # Controladores MVC
    ├── service/              # Lógica de negócio
    └── ui/components/        # Utilitários de interface
```

### Interfaces Obrigatórias

```java
public class NewPlugin implements ILibraryPlugin, IPluginUI {
    @Override
    public String getPluginName() { return "New Plugin"; }
    
    @Override
    public String getTabTitle() { return "Nova Aba"; }
    
    @Override 
    public Node createTabContent() { /* carregar FXML */ }
}
```

## 🔧 Comandos Úteis

```bash
# Acesso ao banco via host
mariadb -h 127.0.0.1 -P 3307 -u root -proot bookstore

# Acesso ao banco via container
docker exec -it bookstore-db mariadb -u root -proot bookstore

# Logs do banco
docker logs bookstore-db

# Compilação específica de um plugin
cd plugins/pluginname && mvn clean package

# Execução com logs detalhados
java -Djava.util.logging.level=ALL -jar app/target/executable-1.0-SNAPSHOT.jar
```
---

## 📚 Links Relacionados

- [📖 README Principal](../../../README.md)
- [🏗️ README do Microkernel](../../README.md)
- [📚 Plugin de Livros](../bookplugin/README.md)
- [📋 Plugin de Empréstimos](../loanplugin/README.md)
- [📊 Plugin de Relatórios](../reportplugin/README.md)

---

**Desenvolvido por:** Marcus Vinicius Silva da Fonseca
**Disciplina:** INF008 - POO
**Instituição:** IFBA