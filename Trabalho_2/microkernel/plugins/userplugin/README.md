# Plugin de Gerenciamento de Usuários

## 👥 Visão Geral

O `UserPlugin` é um plugin do sistema Alexandria que fornece funcionalidades completas para gerenciamento de usuários da biblioteca. Implementa operações CRUD com validação de email e interface intuitiva.

Este plugin fornece uma interface gráfica completa para:

- ✅ Cadastrar novos usuários
- 🔍 Buscar usuários existentes (por nome ou email)
- 👁️ Visualizar detalhes de usuários
- ✏️ Editar informações de usuários
- 🗑️ Excluir usuários (com confirmação obrigatória)
- 📧 Validar endereços de email
- 🔁 Atualização automática de informações da tabela

## 🏗️ Estrutura do Plugin

```
userplugin/
├── pom.xml
├── README.md
└── src/main/
    ├── java/br/edu/ifba/inf008/plugins/
    │   ├── UserPlugin.java                    # Classe principal do plugin
    │   ├── controller/
    │   │   └── UserController.java            # Controlador MVC
    │   ├── persistence/
    │   │   ├── UserBookDAO.java               # DAO para relação User-Book
    │   │   ├── UserDAO.java                   # Data Access Object principal
    │   │   ├── UserLoanDAO.java               # DAO para relação User-Loan
    │   │   └── interfaces/                    # Interfaces de persistência
    │   ├── service/
    │   │   ├── UserService.java               # Serviços de negócio
    │   │   └── UserValidationService.java     # Serviços de validação
    │   └── ui/                                # Componentes de interface
    └── resources/
        ├── fxml/
        │   └── UserView.fxml                  # Layout da interface
        └── styles/
            └── user-theme.css                 # Estilos específicos
```

## ⚙️ Funcionalidades

### 📝 Cadastro de Usuários
- **Campos**: Nome, Email
- **Validações**: Nome deve ser preenchido, email único e formato válido
- **Exemplo**:
  ```
  Nome: "João Silva"
  Email: "joao.silva@email.com"
  ```

### 🔍 Sistema de Busca
- **Tipos**: Por nome ou email
- **Características**: Busca parcial, case-insensitive
- **Exemplo**: Buscar por nome "Silva" retorna "João Silva", "Maria Silva"

### ✏️ Edição de Usuários
- Seleção por clique na tabela
- Preenchimento automático do formulário
- Validação de unicidade do email
- Modo de edição com feedback visual

### 🗑️ Exclusão de Usuários
- Confirmação obrigatória via pop-up
- Verificação de empréstimos ativos
- Aviso sobre finalização automática de empréstimos

## 🖥️ Fluxo de Uso da Interface

1. **Acessar Plugin**
   - Menu "Gerenciamento" → "Gerenciar Usuários"
   - Interface carrega com lista de usuários

2. **Cadastrar Novo Usuário**
   - Preencher nome e email no formulário
   - Clicar "Cadastrar"

3. **Buscar Usuários**
   - Selecionar critério de busca (Nome/Email)
   - Digite termo no campo de busca
   - Clicar "Pesquisar"
   - Usar "Limpar" para mostrar todos

4. **Editar Usuário**
   - Selecionar usuário na tabela
   - Clicar "Editar"
   - Modificar dados necessários
   - Clicar "Atualizar" ou "Cancelar"

5. **Excluir Usuário**
   - Selecionar usuário na tabela
   - Clicar "Excluir"
   - Confirmar exclusão no diálogo
   - Observar aviso sobre empréstimos ativos

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