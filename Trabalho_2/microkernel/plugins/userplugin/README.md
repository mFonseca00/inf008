# Plugin de Gerenciamento de Usuários - README

Este documento descreve o plugin de gerenciamento de usuários (`UserPlugin`) que faz parte do sistema baseado em microkernel para bibliotecas.

## 👥 Visão Geral

O `UserPlugin` é um componente plugável que implementa funcionalidades de gestão de usuários para o sistema Alexandria, seguindo uma arquitetura de microkernel. Este plugin fornece uma interface gráfica completa para:

- ✅ Cadastrar novos usuários
- 🔍 Buscar usuários existentes (por nome ou email)
- 👁️ Visualizar detalhes de usuários
- ✏️ Editar informações de usuários
- 🗑️ Excluir usuários (com confirmação obrigatória)
- 📧 Validar endereços de email

## 🏗️ Estrutura do Plugin

```
userplugin/
├── pom.xml                           # Configuração Maven
├── README.md                         # Esta documentação
└── src/main/
    ├── java/br/edu/ifba/inf008/plugins/
    │   ├── UserPlugin.java           # Classe principal do plugin
    │   ├── controller/
    │   │   └── UserController.java   # Controlador MVC
    │   ├── service/
    │   │   ├── UserService.java      # Serviço de acesso a dados
    │   │   └── UserValidationService.java # Validação de dados
    │   └── ui/
    │       ├── UserUIUtils.java      # Utilitários de UI  
    │       └── components/
    │           ├── UserTableFactory.java   # Fábrica para tabela de usuários
    │           └── UserMessageUtils.java   # Utilitários de mensagens
    └── resources/
        ├── fxml/
        │   └── UserView.fxml         # Interface FXML
        └── styles/
            └── user-theme.css        # Estilos específicos do plugin
```

## ⚙️ Funcionalidades Detalhadas

### 📝 Cadastro de Usuários

**Campos disponíveis:**
- **Nome**: Nome completo do usuário (obrigatório)
- **Email**: Endereço de email único (obrigatório, formato válido)

**Validações implementadas:**
- Nome não pode estar vazio e deve ter pelo menos 2 caracteres
- Email deve ter formato válido (regex pattern)
- Email deve ser único no sistema

**Exemplo de validação:**
```java
// Nome válido
"João Silva" ✅
"Ana" ✅  

// Email válido
"joao.silva@email.com" ✅
"ana@teste.com.br" ✅
"email_invalido" ❌
"@dominio.com" ❌
```

### 🔍 Sistema de Busca

**Tipos de busca disponíveis:**
- **Por Nome**: Busca parcial case-insensitive
- **Por Email**: Busca parcial case-insensitive

**Características:**
- Busca em dois passos com confirmação após a digitação
- Filtros selecionáveis via ComboBox
- Resultados exibidos na tabela após confirmação
- Busca funciona em qualquer parte do texto

**Exemplos de busca:**
```
Busca por nome: "Silva" → encontra "João Silva", "Maria Silva Santos"
Busca por email: "gmail" → encontra "user@gmail.com", "test@gmail.com.br"
```

### ✏️ Edição de Usuários

**Processo de edição:**
1. Selecionar usuário na tabela
2. Clicar em "Editar" ou duplo-clique na linha
3. Formulário é preenchido com dados atuais
4. Modificar campos desejados
5. Salvar alterações ou cancelar

**Feedback visual durante edição:**
- Mensagem de confirmação: "Editando usuário [nome]"
- Botão muda de "Cadastrar" para "Atualizar"
- Botão "Cancelar" fica visível
- ID do usuário sendo editado é armazenado

### 🗑️ Exclusão de Usuários

**Processo de exclusão com confirmação:**
1. Selecionar usuário na tabela
2. Clicar em "Excluir"
3. **Pop-up de confirmação obrigatório** aparece com:
   - Nome do usuário
   - Email do usuário
   - Pergunta de confirmação clara
4. Usuário deve confirmar ou cancelar

**Regras de negócio:**
- Não permite excluir usuários com empréstimos ativos
- Confirmação obrigatória para evitar exclusões acidentais
- Mensagem detalhada de sucesso/erro após operação

**Exemplo de confirmação:**
```
Título: "Confirmação"
Mensagem: "Tem certeza que deseja excluir o usuário 'João Silva' (joao@email.com)?"
Botões: [OK] [Cancelar]
```

## 🎨 Interface do Usuário

### Sistema de Mensagens

O plugin implementa sistema robusto de feedback visual:

```java
// Mensagens de erro (vermelho)
UserMessageUtils.displayErrorMessage(lblMessage, "Email já está em uso");

// Mensagens de sucesso (verde)  
UserMessageUtils.displaySuccessMessage(lblMessage, "Usuário cadastrado com sucesso!");

// Mensagens de confirmação/aviso (amarelo)
UserMessageUtils.displayConfirmationMessage(lblMessage, "Editando usuário João Silva");

// Limpar mensagens
UserMessageUtils.clearMessage(lblMessage);

// Pop-up de confirmação
boolean confirmed = UserMessageUtils.showConfirmation("Tem certeza que deseja excluir?");
```

**Classes CSS aplicadas:**
- `.message-error`: Texto vermelho para erros e validações
- `.message-success`: Texto verde para operações bem-sucedidas
- `.message-warning`: Texto amarelo para avisos e confirmações
- `.message-info`: Texto azul para informações gerais

## 🚀 Uso

### Usar Plugin

- Plugin aparece na aba "Gerenciamento" como "Gerenciar Usuários"
- Interface carrega automaticamente
- Pronto para cadastrar usuários!

## 🔄 Ciclo de Vida do Plugin

### Inicialização

1. **Descoberta**: Microkernel encontra UserPlugin.jar
2. **Carregamento**: ClassLoader carrega classes do plugin
3. **Instanciação**: Cria instância de UserPlugin
4. **Inicialização**: Chama `initialize()` 
5. **Integração**: Adiciona aba "Usuários" na interface
6. **Pronto**: Plugin disponível para uso

### Durante Execução

1. **Eventos de UI**: Plugin responde a cliques e digitação
2. **Validação**: Dados são validados antes de persistir
3. **Persistência**: Operações CRUD são executadas no banco
4. **Feedback**: Mensagens informam resultado das operações
5. **Estado**: Interface mantém estado consistente

### Finalização

1. **Shutdown**: Método `shutdown()` é chamado na finalização da aplicação
2. **Limpeza**: Recursos são liberados (conexões, cache, etc.)
3. **Persistência**: Estado final é salvo no banco de dados

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