# Plugin de Gerenciamento de Usuários - README

Este documento descreve o plugin de gerenciamento de usuários (`UserPlugin`) que faz parte do sistema baseado em microkernel para bibliotecas.

## Visão Geral

O `UserPlugin` é um componente plugável que implementa funcionalidades de gestão de usuários para o sistema Alexandria, seguindo uma arquitetura de microkernel. Este plugin fornece uma interface gráfica para:

- Cadastrar novos usuários
- Buscar usuários existentes (por nome ou email)
- Visualizar detalhes de usuários
- Editar informações de usuários
- Excluir usuários

## Estrutura do Plugin

```
userplugin/
├── pom.xml                           # Configuração Maven
└── src/main/
    ├── java/br/edu/ifba/inf008/plugins/
    │   ├── UserPlugin.java           # Classe principal do plugin
    │   ├── controller/
    │   │   └── UserController.java   # Controlador de ações do usuário
    │   ├── model/
    │   │   └── UserViewModel.java    # Modelo de visualização
    │   ├── service/
    │   │   └── UserService.java      # Serviço de acesso a dados
    │   ├── ui/
    │   │   ├── UIUtils.java          # Utilitários de UI
    │   │   └── components/
    │   │       └── UserTableFactory.java  # Fábrica para tabela de usuários
    │   |       └── MessageUtils.java      # Utilitários para exibição de mensagens
    │   └── util/
    │       └── ValidationService.java     # Validação de dados
    └── resources/
        └── fxml/
            └── UserView.fxml         # Layout da interface gráfica
```

## Componentes Principais

### 1. UserPlugin

A classe `UserPlugin` é o ponto de entrada do plugin, implementando as interfaces `IPluginUI` e `ILibraryPlugin` definidas pelo núcleo da aplicação. Esta classe:

- Inicializa o plugin
- Define metadados (nome, categoria, etc.)
- Carrega a interface FXML
- Conecta os componentes da UI ao controlador

### 2. UserController

A classe `UserController` é responsável por:

- Gerenciar as interações do usuário
- Validar dados de entrada
- Coordenar as operações de CRUD através do UserService
- Atualizar a interface gráfica conforme necessário

### 3. UserService

A classe `UserService` serve como camada de serviço que:

- Encapsula o acesso aos dados de usuários
- Acessa o DAO de usuários do núcleo da aplicação através da interface `ICore`
- Implementa operações de negócio relacionadas aos usuários

### 4. UserTableFactory

A classe `UserTableFactory` é uma fábrica para criar e configurar tabelas de visualização de usuários com as colunas e formatos apropriados.

### 5. ValidationService

A classe `ValidationService` fornece métodos de validação para dados de usuários, como validação de email.

### 6. UserViewModel

A classe `UserViewModel` é um modelo de visualização que adapta a entidade `User` para uso na interface gráfica, utilizando propriedades observáveis do JavaFX.

### 7. MessageUtils

A classe `MessageUtils` fornece métodos utilitários para exibição de mensagens na interface gráfica, com estilos visuais distintos para diferentes tipos de mensagens (erro, sucesso, confirmação).

## Integração com o Sistema

O `UserPlugin` se integra ao sistema central Alexandria através de:

1. **Interfaces do Microkernel**: Implementa as interfaces `IPluginUI` e `ILibraryPlugin` definidas no módulo `interfaces`
2. **Carregamento Dinâmico**: É carregado dinamicamente pelo `PluginController` do núcleo
3. **Serviços do Núcleo**: Utiliza o `ICore` para acessar serviços como o `UserDAO`
4. **Interface Gráfica**: Fornece sua própria UI através do método `createTabContent()`

## Ciclo de Vida

1. O plugin é compilado e empacotado como um JAR
2. O JAR é colocado na pasta `plugins/` do sistema principal
3. Durante a inicialização, o `PluginController` carrega o plugin
4. O método `init()` do plugin é chamado
5. Um item de menu é adicionado à interface principal
6. Quando o usuário seleciona o item de menu, a UI do plugin é carregada como uma nova aba

## Funcionalidades

- **Cadastro de Usuários**: Formulário para criar novos usuários com nome e email
- **Busca Avançada**: Filtro por nome ou email
- **Gestão de Dados**: Visualização em tabela com opções para editar e excluir
- **Validação**: Verificação de campos obrigatórios e formato de email
- **Feedback Visual**: Mensagens de erro, sucesso e confirmação para o usuário
- **Confirmação de Exclusão**: Solicita confirmação antes de excluir um usuário

## Tecnologias Utilizadas

- JavaFX para interface gráfica
- FXML para definição de layout
- Padrão MVC para organização do código
- Injeção de dependências simples

## Dependências

- Módulo `interfaces` do sistema principal
- JavaFX (controles e FXML)
- API JPA (indiretamente através das interfaces)

Este plugin serve como exemplo de extensibilidade do sistema Alexandria, demonstrando como funcionalidades podem ser adicionadas ao núcleo sem modificar seu código base.