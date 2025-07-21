# LoanPlugin - Documentação

## Descrição
O LoanPlugin é um plugin para o sistema de microkernel da biblioteca que gerencia o empréstimo de livros para usuários. Ele permite registrar empréstimos, controlar devolução de livros e consultar o status dos empréstimos.

## Funcionalidades
- Registro de novos empréstimos
- Listagem de todos os empréstimos
- Busca de empréstimos por diversos critérios
- Atualização de informações de empréstimos
- Registro de devolução de livros
- Exclusão de empréstimos
- Controle automático da disponibilidade de livros

## Dependências
- interfaces-1.0-SNAPSHOT.jar - Contém as interfaces e modelos do sistema
- JavaFX 17.0.10 - Para a interface gráfica

## Estrutura do Projeto
```
loanplugin/
  ├── pom.xml                               # Configuração do Maven
  ├── src/
  │   ├── main/
  │   │   ├── java/
  │   │   │   └── br/edu/ifba/inf008/plugins/
  │   │   │       ├── LoanPlugin.java       # Classe principal do plugin
  │   │   │       ├── controller/
  │   │   │       │   └── LoanController.java  # Controlador para operações de empréstimo
  │   │   │       ├── service/
  │   │   │       │   └── LoanService.java  # Serviços para manipulação de empréstimos
  │   │   │       ├── ui/
  │   │   │       │   ├── UIUtils.java      # Utilitários para a interface
  │   │   │       │   └── components/
  │   │   │       │       ├── LoanTableFactory.java  # Configuração da tabela de empréstimos
  │   │   │       │       └── MessageUtils.java      # Utilitários para mensagens
  │   │   │       └── util/
  │   │   │           └── ValidationService.java  # Validações de empréstimo
  │   │   └── resources/
  │   │       └── fxml/
  │   │           └── LoanView.fxml         # Layout da interface do plugin
```

## Compilação e Empacotamento
Para compilar o plugin, execute o seguinte comando na raiz do projeto:

```bash
mvn clean package
```

Este comando irá gerar o arquivo JAR do plugin no diretório `../` com o nome `LoanPlugin.jar`.

## Instalação
Para instalar o plugin, copie o arquivo JAR gerado para a pasta de plugins do aplicativo principal.

## Uso
Após instalar o plugin, inicie o aplicativo principal. O plugin de empréstimos estará disponível no menu "Empréstimos" > "Gerenciar Empréstimos".
