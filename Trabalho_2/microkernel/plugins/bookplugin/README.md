# Plugin de Gerenciamento de Livros - README

Este documento descreve o plugin de gerenciamento de livros (`BookPlugin`) que faz parte do sistema baseado em microkernel para bibliotecas.

## 📖 Visão Geral

O `BookPlugin` é um componente plugável que implementa funcionalidades de gestão de livros para o sistema Alexandria, seguindo uma arquitetura de microkernel. Este plugin fornece uma interface gráfica completa para:

- ✅ Cadastrar novos livros
- 🔍 Buscar livros existentes (por título, autor, ISBN ou ano de publicação)
- 👁️ Visualizar detalhes dos livros
- ✏️ Editar informações dos livros
- 🗑️ Excluir livros (com confirmação)
- 📊 Controlar cópias disponíveis

## 🏗️ Estrutura do Plugin

```
bookplugin/
├── pom.xml                           # Configuração Maven
├── README.md                         # Esta documentação
└── src/main/
    ├── java/br/edu/ifba/inf008/plugins/
    │   ├── BookPlugin.java           # Classe principal do plugin
    │   ├── controller/
    │   │   └── BookController.java   # Controlador MVC
    │   ├── service/
    │   │   ├── BookService.java      # Serviço de acesso a dados
    │   │   └── BookValidationService.java # Validação de dados
    │   └── ui/
    │       ├── BookUIUtils.java      # Utilitários de UI
    │       └── components/
    │           ├── BookTableFactory.java    # Fábrica para tabela de livros
    │           └── BookMessageUtils.java    # Utilitários de mensagens
    └── resources/
        ├── fxml/
        │   └── BookView.fxml         # Interface FXML
        └── styles/
            └── book-theme.css        # Estilos específicos do plugin
```

## ⚙️ Funcionalidades Detalhadas

### 📝 Cadastro de Livros

**Campos disponíveis:**
- **Título**: Nome do livro (obrigatório)
- **Autor**: Nome do autor (obrigatório) 
- **ISBN**: Código ISBN único (opcional, validado quando preenchido)
- **Ano de Publicação**: Ano entre 1500 e ano atual
- **Cópias Disponíveis**: Quantidade de exemplares

**Validações implementadas:**
- Campos obrigatórios não podem estar vazios
- ISBN deve ter formato válido (10 ou 13 dígitos)
- Ano deve estar em faixa válida
- Título e autor devem ter pelo menos 2 caracteres
- Cópias devem ser número positivo

### 🔍 Sistema de Busca

**Tipos de busca disponíveis:**
- **Por Título**: Busca parcial case-insensitive
- **Por Autor**: Busca parcial case-insensitive  
- **Por ISBN**: Busca parcial case-insensitive
- **Por Ano**: Busca exata por ano de publicação

**Características:**
- Busca em dois passos com confirmação após a digitação
- Resultados exibidos na tabela após confirmação
- Busca funciona em qualquer parte do texto

### ✏️ Edição de Livros

**Processo de edição:**
1. Selecionar livro na tabela
2. Clicar em "Editar"
3. Formulário é preenchido com dados atuais
4. Modificar campos desejados
5. Salvar alterações ou cancelar

**Feedback visual:**
- Mensagem de confirmação "Editando livro [título]"
- Botão muda de "Cadastrar" para "Atualizar"
- Botão "Cancelar" fica visível

### 🗑️ Exclusão de Livros

**Processo de exclusão:**
1. Selecionar livro na tabela
2. Clicar em "Excluir"
3. **Pop-up de confirmação** aparece com:
   - Título do livro
   - Nome do autor
   - Pergunta de confirmação
4. Confirmar ou cancelar a exclusão

**Regras de negócio:**
- Não permite excluir livros com empréstimos ativos sem confirmação
- Confirmação obrigatória para evitar exclusões acidentais
- Mensagem de sucesso/erro após operação

## 🎨 Interface do Usuário

### Sistema de Mensagens

O plugin implementa um sistema consistente de feedback visual:

```java
// Mensagens de erro (vermelho)
BookMessageUtils.displayErrorMessage(lblMessage, "Título é obrigatório");

// Mensagens de sucesso (verde)  
BookMessageUtils.displaySuccessMessage(lblMessage, "Livro cadastrado com sucesso!");

// Mensagens de confirmação/aviso (amarelo)
BookMessageUtils.displayConfirmationMessage(lblMessage, "Editando livro...");

// Limpar mensagens
BookMessageUtils.clearMessage(lblMessage);
```

**Classes CSS aplicadas:**
- `.message-error`: Texto vermelho para erros
- `.message-success`: Texto verde para sucesso
- `.message-warning`: Texto amarelo para avisos
- `.message-info`: Texto azul para informações

## 🚀 Uso

- Plugin aparece na aba "Gerenciamento" como "Gerenciar Usuários"
- Interface carrega automaticamente
- Pronto para cadastrar livros!

## 🔄 Ciclo de Vida do Plugin

### Inicialização
1. **Descoberta**: Core encontra JAR na pasta plugins
2. **Carregamento**: ClassLoader carrega classes do plugin  
3. **Instanciação**: Cria instância da classe principal
4. **Inicialização**: Chama método `initialize()`
5. **Integração**: Adiciona aba na interface principal

### Durante Execução
1. **Eventos**: Plugin processa cliques e interações
2. **Validação**: Dados são validados antes de persistir
3. **Persistência**: Operações são salvas no banco
4. **Feedback**: Mensagens informam resultado das operações

### Finalização
1. **Shutdown**: Método `shutdown()` é chamado
2. **Limpeza**: Recursos são liberados
3. **Persistência**: Estado final é salvo

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