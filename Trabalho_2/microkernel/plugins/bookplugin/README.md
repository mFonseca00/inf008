# Plugin de Gerenciamento de Livros

## 📖 Visão Geral

O `BookPlugin` é um plugin do sistema Alexandria que fornece funcionalidades completas para gerenciamento de livros em bibliotecas. Implementa operações CRUD (Create, Read, Update, Delete) com interface gráfica JavaFX e validações robustas.

Este plugin fornece uma interface gráfica completa para:

- ✅ Cadastrar novos livros
- 🔍 Buscar livros existentes (por título, autor, ISBN ou ano de publicação)
- 👁️ Visualizar detalhes dos livros
- ✏️ Editar informações dos livros
- 🗑️ Excluir livros (com confirmação)
- 📊 Controlar cópias disponíveis
- 🔁 Atualização automática de informações da tabela

## 🏗️ Estrutura do Plugin

```
bookplugin/
├── pom.xml
├── README.md
└── src/main/
    ├── java/br/edu/ifba/inf008/plugins/
    │   ├── BookPlugin.java                    # Classe principal do plugin
    │   ├── controller/
    │   │   └── BookController.java            # Controlador MVC
    │   ├── persistence/
    │   │   ├── BookDAO.java                   # Data Access Object
    │   │   └── interfaces/                    # Interfaces de persistência
    │   ├── service/
    │   │   ├── BookService.java               # Serviços de negócio
    │   │   └── BookValidationService.java     # Serviços de validação
    │   └── ui/                                # Componentes de interface
    └── resources/
        ├── fxml/
        │   └── BookView.fxml                  # Layout da interface
        └── styles/
            └── book-theme.css                 # Estilos específicos
```

## ⚙️ Funcionalidades

### 📝 Cadastro de Livros
- **Campos**: Título, Autor, ISBN, Ano de Publicação, Cópias Disponíveis
- **Validações**: Campos obrigatórios, formato e unicidade do ISBN, ano válido
- **Exemplo**: 
  ```
  Título: "Clean Code"
  Autor: "Robert Martin"
  ISBN: "978-0132350884"
  Ano: 2008
  Cópias: 5
  ```

### 🔍 Sistema de Busca
- **Tipos**: Por título, autor, ISBN ou ano de publicação
- **Características**: Busca parcial, case-insensitive
- **Exemplo**: Buscar por título "Clean" retorna "Clean Code", "Clean Architecture"

### ✏️ Edição de Livros
- Seleção por clique na tabela
- Preenchimento automático do formulário
- Validação de unicidade do ISBN
- Feedback visual durante edição

### 🗑️ Exclusão de Livros
- Confirmação obrigatória via pop-up
- Verificação de empréstimos ativos
- Mensagens informativas de sucesso/erro

## 🖥️ Fluxo de Uso da Interface

1. **Acessar Plugin**
   - Menu "Gerenciamento" → "Gerenciar Livros"
   - Interface carrega com tabela de livros existentes

2. **Cadastrar Novo Livro**
   - Preencher formulário no painel superior
   - Clicar "Cadastrar"
   - Verificar mensagem de sucesso/erro

3. **Buscar Livros**
   - Selecionar tipo de busca no ComboBox
   - Digite termo no campo de busca
   - Clicar "Buscar"
   - Usar "Limpar" para resetar filtros

4. **Editar Livro**
   - Selecionar livro na tabela
   - Clicar "Editar"
   - Modificar campos necessários
   - Clicar "Atualizar" ou "Cancelar"

5. **Excluir Livro**
   - Selecionar livro na tabela
   - Clicar "Excluir"
   - Confirmar na caixa de diálogo

## 📚 Links Relacionados

- [📖 README Principal](../../../README.md)
- [🏗️ README do Microkernel](../../README.md)
- [👥 Plugin de Usuários](../userplugin/README.md)
- [📋 Plugin de Empréstimos](../loanplugin/README.md)
- [📊 Plugin de Relatórios](../reportplugin/README.md)

---

**Desenvolvido por:** Marcus Vinicius Silva da Fonseca  
**Disciplina:** INF008 - POO  
**Instituição:** IFBA