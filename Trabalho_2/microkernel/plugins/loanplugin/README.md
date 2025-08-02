# Plugin de Gerenciamento de Empréstimos

## 📋 Visão Geral

O `LoanPlugin` é um plugin do sistema Alexandria que fornece funcionalidades completas para gerenciamento de empréstimos de livros. Controla o ciclo completo desde o registro até a devolução, com regras de negócio robustas.

Este plugin fornece uma interface gráfica completa para:

- ✅ Registrar novos empréstimos
- 🔍 Buscar empréstimos existentes
- 👁️ Visualizar detalhes dos empréstimos
- ✏️ Editar empréstimos ativos
- 📅 Registrar devoluções
- 🗑️ Excluir empréstimos finalizados (com confirmação)
- ⚠️ Controlar regras de negócio (disponibilidade, datas, etc.)
- 🔁 Atualização automática de informações da tabela, usuáirios disponíveis e livros disponíveis

## 🏗️ Estrutura do Plugin

```
loanplugin/
├── pom.xml
├── README.md
└── src/main/
    ├── java/br/edu/ifba/inf008/plugins/
    │   ├── LoanPlugin.java                    # Classe principal do plugin
    │   ├── controller/
    │   │   └── LoanController.java            # Controlador MVC
    │   ├── persistence/
    │   │   ├── LoanBookDAO.java               # DAO para acesso aos livros
    │   │   ├── LoanDAO.java                   # Data Access Object principal
    │   │   ├── LoanUserDAO.java               # DAO para acesso aos usuários
    │   │   └── interfaces/                    # Interfaces de persistência
    │   ├── service/
    │   │   ├── LoanService.java               # Serviços de negócio
    │   │   ├── LoanUserService.java           # Serviços de negócio integrado a usuários
    │   │   └── LoanBookService.java           # Serviços de negócio integrado a livros
    │   ├── ui/                                # Componentes de interface
    │   └── util/                              # Utilitários auxiliares
    └── resources/
        ├── fxml/
        │   └── LoanView.fxml                  # Layout da interface
        └── styles/
            └── loan-theme.css                 # Estilos específicos
```

## ⚙️ Funcionalidades

### 📝 Registro de Empréstimos
- **Campos**: Usuário, Livro, Data de empréstimo, filtros avançados para seleção de usuário e livro
- **Validações**: Disponibilidade do livro, usuário válido, data não futura
- **Exemplo**:
  ```
  Usuário: "João Silva"
  Livro: "Clean Code"
  Data: 15/01/2024
  ```

### 🔍 Sistema de Busca e Filtros
- **Tipos**: Por usuário, livro, data de empréstimo, data de devolução
- **Características**: Busca parcial (com exceção da busca por data), case-insensitive
- **Exemplo**: Buscar por livro "Clean" retorna empréstimos de livros "Clean Code", "Clean Architecture"

### ✏️ Edição de Empréstimos
- Registro de data de devolução
- Atualização de dados do empréstimo
- Controle automático de estoque
- Validações de datas

### 📅 Controle de Devoluções
- Registro simples de devolução
- Atualização automática do estoque
- Status visual (ativo/devolvido)

### 🗑️ Exclusão de Empréstimos
- Apenas empréstimos devolvidos podem ser excluídos
- Confirmação obrigatória

## 🖥️ Fluxo de Uso da Interface

1. **Acessar Plugin**
   - Menu "Gerenciamento" → "Gerenciar Empréstimos"
   - Interface carrega com lista de empréstimos

2. **Registrar Novo Empréstimo**
   - Usar filtros avançados se necessário
   - Selecionar usuário no ComboBox
   - Selecionar livro disponível no ComboBox
   - Definir data de empréstimo (padrão: hoje)
   - Clicar "Cadastrar"

3. **Buscar Empréstimos**
   - Selecionar tipo de busca
   - Digite critério de busca
   - Clicar "Buscar"
   - Usar "Limpar" para resetar filtros

4. **Registrar Devolução**
   - Selecionar empréstimo na tabela
   - Clicar "Editar"
   - Realizar edição
   - Clicar "Atualizar"

5. **Visualizar Status**
   - Tabela mostra status visual (ativo/devolvido)
   - Colunas informam datas e detalhes
   - Filtros permitem focar em empréstimos específicos

## 📚 Links Relacionados

- [📖 README Principal](../../../README.md)
- [🏗️ README do Microkernel](../../README.md)
- [📚 Plugin de Livros](../bookplugin/README.md)
- [👥 Plugin de Usuários](../userplugin/README.md)
- [📊 Plugin de Relatórios](../reportplugin/README.md)

---

**Desenvolvido por:** Marcus Vinicius Silva da Fonseca  
**Disciplina:** INF008 - POO  
**Instituição:** IFBA