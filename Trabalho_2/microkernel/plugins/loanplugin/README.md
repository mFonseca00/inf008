# Plugin de Gerenciamento de Empréstimos - README

Este documento descreve o plugin de gerenciamento de empréstimos (`LoanPlugin`) que faz parte do sistema baseado em microkernel para bibliotecas.

## Visão Geral

O `LoanPlugin` é um componente plugável que implementa funcionalidades de gestão de empréstimos para o sistema Alexandria, seguindo uma arquitetura de microkernel. Este plugin fornece uma interface gráfica para:

- Registrar novos empréstimos
- Buscar empréstimos existentes
- Visualizar detalhes dos empréstimos
- Renovar empréstimos
- Registrar devoluções
- Gerenciar atrasos e multas

## Estrutura do Plugin

```
loanplugin/
├── pom.xml                           # Configuração Maven
└── src/main/
    ├── java/br/edu/ifba/inf008/plugins/
    │   ├── LoanPlugin.java           # Classe principal do plugin
    │   ├── controller/
    │   │   └── LoanController.java   # Controlador de ações do usuário
    │   ├── service/
    │   │   ├── LoanService.java      # Serviço principal de empréstimos
    │   │   ├── LoanBookService.java  # Serviço de acesso aos livros
    │   │   └── LoanUserService.java  # Serviço de acesso aos usuários
    │   ├── ui/
    │   │   ├── UIUtils.java          # Utilitários de UI
    │   │   └── components/
    │   │       ├── LoanTableFactory.java  # Fábrica para tabela de empréstimos
    │   │       └── MessageUtils.java      # Utilitários para exibição de mensagens
    │   └── util/
    │       └── ValidationService.java     # Validação de dados
    └── resources/
        └── fxml/
            └── LoanView.fxml         # Layout da interface gráfica
```

## Componentes Principais

### 1. LoanPlugin

A classe `LoanPlugin` é o ponto de entrada do plugin, implementando as interfaces `IPluginUI` e `ILibraryPlugin` definidas pelo núcleo da aplicação. Esta classe:

- Inicializa o plugin
- Define metadados (nome, categoria, etc.)
- Carrega a interface FXML
- Conecta os componentes da UI ao controlador

### 2. LoanController

O `LoanController` gerencia a interação do usuário com a interface gráfica, processando eventos como:

- Registro de novos empréstimos
- Pesquisa de empréstimos
- Ações de renovação e devolução
- Validação de entradas de usuário

### 3. Serviços

O plugin utiliza três serviços principais:

- **LoanService**: Gerencia as operações principais de empréstimos, como registro, renovação e devolução
- **LoanBookService**: Integra-se com o sistema de gerenciamento de livros para verificar disponibilidade e atualizar status
- **LoanUserService**: Integra-se com o sistema de gerenciamento de usuários para verificar elegibilidade e histórico

### 4. Interface Gráfica

A interface é definida pelo arquivo FXML (`LoanView.fxml`) e inclui componentes para:

- Formulário de registro de empréstimos
- Tabela de visualização de empréstimos
- Campos de pesquisa e filtros
- Botões para ações como renovar e devolver

## Integração com o Sistema

O `LoanPlugin` se integra com outros componentes do sistema:

1. **Core da Aplicação**: Por meio das interfaces `IPluginUI` e `ILibraryPlugin`
2. **Banco de Dados**: Utilizando JPA para persistência dos dados de empréstimos
3. **Plugin de Usuários**: Para validar informações de usuários e verificar permissões
4. **Plugin de Livros**: Para verificar disponibilidade e atualizar o status dos livros

## Compilação e Implantação

O plugin é construído usando Maven:

```bash
cd loanplugin
mvn clean package
```

O arquivo JAR resultante deve ser colocado no diretório de plugins do sistema principal.

## Dependências

- JavaFX para a interface gráfica
- JPA/Hibernate para persistência
- Core da aplicação (interfaces e utilitários comuns)
