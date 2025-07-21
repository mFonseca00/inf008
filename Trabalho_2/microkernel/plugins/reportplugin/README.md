# Plugin de Relatórios - README

Este documento descreve o plugin de relatórios (`ReportPlugin`) que faz parte do sistema baseado em microkernel para bibliotecas.

## Visão Geral

O `ReportPlugin` é um componente plugável que implementa funcionalidades de geração de relatórios para o sistema Alexandria, seguindo uma arquitetura de microkernel. Este plugin fornece uma interface gráfica para:

- Visualizar ranking de usuários com mais empréstimos
- Consultar ranking de livros mais emprestados
- Listar empréstimos ativos (não devolvidos)
- Exportar relatórios para CSV
- Atualizar dados dos relatórios em tempo real

## Estrutura do Plugin

```
reportplugin/
├── pom.xml                           # Configuração Maven
└── src/main/
    ├── java/br/edu/ifba/inf008/plugins/
    │   ├── ReportPlugin.java         # Classe principal do plugin
    │   ├── controller/
    │   │   └── ReportController.java # Controlador de ações do usuário
    │   ├── service/
    │   │   └── ReportService.java    # Serviço de acesso aos dados de relatórios
    │   ├── ui/
    │   │   ├── ReportUIUtils.java    # Utilitários de UI
    │   │   └── components/
    │   │       ├── ReportTableFactory.java    # Fábrica para tabelas de relatórios
    │   │       ├── ReportMessageUtils.java    # Utilitários para exibição de mensagens
    │   │       └── ReportExporter.java        # Utilitários para exportação de dados
    └── resources/
        └── fxml/
            └── ReportView.fxml       # Layout da interface gráfica
```

## Componentes Principais

### 1. ReportPlugin

A classe `ReportPlugin` é o ponto de entrada do plugin, implementando as interfaces `IPluginUI` e `ILibraryPlugin` definidas pelo núcleo da aplicação. Esta classe:

- Inicializa o plugin
- Define metadados (nome, categoria, etc.)
- Carrega a interface FXML
- Conecta os componentes da UI ao controlador

### 2. ReportController

A classe `ReportController` é responsável por:

- Gerenciar as interações do usuário
- Coordenar as operações de busca de dados através do ReportService
- Controlar as funcionalidades de atualização e exportação
- Atualizar as tabelas de relatórios conforme necessário

### 3. ReportService

A classe `ReportService` serve como camada de serviço que:

- Encapsula o acesso aos dados de empréstimos
- Acessa o LoanDAO do núcleo da aplicação através da interface `ICore`
- Implementa operações de busca para relatórios estatísticos

### 4. ReportTableFactory

A classe `ReportTableFactory` é uma fábrica para criar e configurar tabelas de visualização dos diferentes tipos de relatórios:

- **Ranking de Usuários**: Tabela com nome, email e quantidade de empréstimos
- **Ranking de Livros**: Tabela com título, autor, ISBN e quantidade de empréstimos
- **Empréstimos Ativos**: Tabela com ID, usuário, livro, autor, data do empréstimo e dias em aberto

### 5. ReportExporter

A classe `ReportExporter` fornece funcionalidades para:

- Exportar dados dos relatórios para arquivos CSV
- Gerar nomes de arquivos únicos com timestamp
- Formatar dados adequadamente para exportação

### 6. ReportMessageUtils

A classe `ReportMessageUtils` fornece métodos utilitários para exibição de mensagens na interface gráfica, com estilos visuais distintos para diferentes tipos de mensagens (erro, sucesso, confirmação).

## Integração com o Sistema

O `ReportPlugin` se integra ao sistema central Alexandria através de:

1. **Interfaces do Microkernel**: Implementa as interfaces `IPluginUI` e `ILibraryPlugin` definidas no módulo `interfaces`
2. **Carregamento Dinâmico**: É carregado dinamicamente pelo `PluginController` do núcleo
3. **Serviços do Núcleo**: Utiliza o `ICore` para acessar o `LoanDAO` e seus métodos de relatórios
4. **Interface Gráfica**: Fornece sua própria UI através do método `createTabContent()`

## Ciclo de Vida

1. O plugin é compilado e empacotado como um JAR
2. O JAR é colocado na pasta `plugins/` do sistema principal
3. Durante a inicialização, o `PluginController` carrega o plugin
4. O método `init()` do plugin é chamado
5. Um item de menu é adicionado à interface principal
6. Quando o usuário seleciona o item de menu, a UI do plugin é carregada como uma nova aba

## Funcionalidades

### Ranking de Usuários com Mais Empréstimos
- Lista os usuários ordenados por quantidade de empréstimos realizados
- Exibe nome, email e contagem total de empréstimos
- Permite atualização e exportação dos dados

### Ranking de Livros Mais Emprestados
- Lista os livros ordenados por quantidade de empréstimos
- Exibe título, autor, ISBN e contagem total de empréstimos
- Permite atualização e exportação dos dados

### Empréstimos Ativos
- Lista todos os empréstimos que ainda não foram devolvidos
- Exibe ID, usuário, livro, autor, data do empréstimo e dias em aberto
- Calcula automaticamente os dias desde o empréstimo
- Permite atualização e exportação dos dados

### Funcionalidades Gerais
- **Atualização em Tempo Real**: Botões de refresh para cada relatório
- **Exportação para CSV**: Todos os relatórios podem ser exportados
- **Interface Responsiva**: Layout organizado em containers colapsáveis
- **Feedback Visual**: Mensagens de erro e sucesso para o usuário

## Tecnologias Utilizadas

- JavaFX para interface gráfica
- FXML para definição de layout
- Padrão MVC para organização do código
- CSV para exportação de dados
- JPA/JPQL para consultas de relatórios

## Dependências

- Módulo `interfaces` do sistema principal
- JavaFX (controles e FXML)
- API JPA (indiretamente através das interfaces)

## Consultas Utilizadas

O plugin utiliza métodos específicos do `LoanDAO` para gerar os relatórios:

- `findUserLoanRanking()`: Consulta agregada que conta empréstimos por usuário
- `findBookLoanRanking()`: Consulta agregada que conta empréstimos por livro
- `findActiveLoans()`: Consulta que busca empréstimos sem data de devolução

Este plugin serve como exemplo de como funcionalidades analíticas podem ser adicionadas ao sistema Alexandria através da arquitetura de microkernel, fornecendo insights valiosos sobre o uso da biblioteca.
