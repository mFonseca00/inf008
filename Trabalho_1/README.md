# AcadEvents

Sistema de Gerenciamento de Participantes e Eventos Acadêmicos

---

## Sumário

- Sobre o Projeto  
- Como Executar  
- Fluxo de Telas e Funcionalidades  
  - Menu Principal  
  - Menu de Participantes  
  - Menu de Eventos  
  - Menu de Relatórios  
- Funcionalidades de Geração de Relatórios de Eventos  
- Persistência de Dados  
- Validações  
- Estrutura do Projeto  
- Tecnologias Utilizadas  
- Autor  

---

## Sobre o Projeto

O **AcadEvents** é um sistema em Java para cadastro, listagem, remoção e gerenciamento de participantes (alunos, professores e externos) e eventos acadêmicos (palestras, cursos, workshops e feiras).  
O sistema é totalmente em linha de comando (console) e utiliza arquivos JSON para persistência dos dados.

---

## Como Executar

### Pré-requisitos

- Java 17 ou superior  
- Maven

### Passos

1. **Clone o repositório e acesse a pasta do projeto:**  
   git clone <url-do-repositorio>  
   cd Trabalho_1/acadevents  

2. **Compile o projeto:**  
   mvn clean package  

3. **Execute o sistema usando Maven:**  
   mvn exec:java  

4. **Ou execute diretamente pelo arquivo .class:**  
   Vá até a pasta onde está o arquivo compilado (por exemplo, `target/classes`) e execute:  
   java acad_events.acadevents.AcadEvents  

   Se necessário, inclua o caminho do classpath:  
   java -cp target/classes acad_events.acadevents.AcadEvents 

---

## Fluxo de Telas e Funcionalidades

### Menu Principal

Ao iniciar, você verá o menu principal com as opções:

1. **Manage Events**: Gerenciar eventos (criar, deletar, listar).  
2. **Manage Participants**: Gerenciar participantes (cadastrar, remover, listar, inscrever em evento, gerar certificado).  
3. **Generate Reports**: Gerar relatórios (por tipo, por data).  
4. **Generate Test Data**: Gerar dados de teste (eventos e participantes aleatórios).  
5. **Exit**: Sair do sistema.  

Selecione a opção desejada digitando o número correspondente.

---

### Menu de Participantes

Ao escolher "Manage Participants", você verá:

1. **Register new participant**: Cadastrar novo participante.  
2. **Delete participant**: Remover participante pelo CPF.  
3. **List participants**: Listar todos os participantes cadastrados.  
4. **Enroll participant in event**: Inscrever participante em evento.  
5. **Generate a event certificate**: Gerar certificado de participação.  
6. **Return to Main Menu**: Voltar ao menu principal.  

#### 1. Register new participant

- Informe o CPF (formato: `000.000.000-00`), nome, e-mail, telefone (ex: `71 91234-5678`).  
- Escolha o tipo: Student, Professor ou External.  
- Para Student: informe matrícula.  
- Para Professor: informe ID do funcionário e departamento.  
- Para External: informe organização (opcional) e papel (role).  
- O participante será cadastrado se os dados forem válidos e o CPF não estiver em uso.

#### 2. Delete participant

- Informe o CPF do participante a ser removido.
- O sistema confirmará a remoção ou informará se não encontrou o participante.

#### 3. List participants

- Exibe todos os participantes cadastrados, mostrando CPF, nome e e-mail.

#### 4. Enroll participant in event

- Informe o CPF do participante.
- Escolha o evento desejado (será exibida uma lista).
- O sistema verifica se há vagas e se o tipo do participante é permitido para o evento (ex: apenas alunos podem se inscrever em cursos).
- Se tudo estiver correto, a inscrição é realizada.

#### 5. Generate a event certificate

- Informe o CPF do participante.
- Escolha o evento em que ele está inscrito.
- O sistema gera um certificado textual com os dados do evento e do participante.

#### 6. Return to Main Menu

- Retorna ao menu principal do sistema.

---

### Menu de Eventos

Ao escolher "Manage Events", você verá:

1. **Create Event**: Criar um novo evento acadêmico.
2. **Delete Event**: Remover um evento existente.
3. **List Events**: Listar todos os eventos cadastrados.
4. **Return to Main Menu**: Voltar ao menu principal.

#### 1. Create Event

- O sistema solicitará os dados comuns do evento:  
  - Título  
  - Data (formato: dd/MM/yyyy)  
  - Local  
  - Capacidade (número máximo de participantes)  
  - Descrição  
  - Modalidade (Presencial, Online ou Híbrido)
- Em seguida, você escolherá o tipo de evento:  
  - **Course**: Informe coordenador, área de conhecimento e carga horária total.  
  - **Lecture**: Informe o palestrante.  
  - **Workshop**: Informe o instrutor e a duração em horas.  
  - **Fair**: Informe o organizador e o número de estandes.
- O evento será criado e salvo se não houver outro evento com o mesmo título e data.

#### 2. Delete Event

- Você pode escolher como deseja remover o evento:
  - **Remove by an attribute list**:  
    - Escolha um atributo (ex: título, data, modalidade).
    - Informe o valor do atributo.
    - O sistema listará os eventos encontrados para você selecionar qual deseja remover.
  - **Remove from all events list**:  
    - O sistema exibirá todos os eventos cadastrados.
    - Selecione o evento desejado para remover.
  - **Remove from an ID**:  
    - Informe o ID do evento a ser removido.
- O sistema confirmará a remoção ou informará se não encontrou o evento.

#### 3. List Events

- Exibe todos os eventos cadastrados, mostrando:
  - Tipo do evento (Course, Lecture, Workshop, Fair)
  - Título
  - Modalidade
  - Data
  - Local

#### 4. Return to Main Menu

- Retorna ao menu principal do sistema.

---

### Menu de Relatórios

Ao escolher "Generate Reports", você verá:

1. **Report by event type**: Gera um relatório de eventos filtrando por tipo (Course, Lecture, Workshop, Fair).
2. **Report by date**: Gera um relatório de eventos filtrando por data.
3. **Return to Main Menu**: Volta ao menu principal.

#### 1. Report by event type

- Escolha o tipo de evento desejado.
- O sistema exibirá todos os eventos cadastrados daquele tipo.

#### 2. Report by date

- Informe a data desejada (formato: dd/MM/yyyy).
- O sistema exibirá todos os eventos cadastrados para aquela data.

#### 3. Return to Main Menu

- Retorna ao menu principal do sistema.

---

### Funcionalidades de Geração de Relatórios de Eventos

O sistema AcadEvents permite gerar relatórios de eventos acadêmicos de duas formas principais: **por tipo de evento** (Course, Lecture, Workshop, Fair) ou **por data**. Ambas as funcionalidades são acessíveis pelo menu "Generate Reports" e foram implementadas de forma unificada, utilizando um único método para facilitar a manutenção e a expansão futura.

#### Passo a passo do funcionamento

1. **Acesso ao Menu de Relatórios**
   - No menu principal, selecione "Generate Reports".
   - Escolha entre:
     - **Report by event type**: Relatório filtrando por tipo de evento.
     - **Report by date**: Relatório filtrando por data.

2. **Seleção do Filtro**
   - Para relatório por tipo, escolha o tipo de evento desejado (Course, Lecture, Workshop, Fair).
   - Para relatório por data, informe a data no formato `dd/MM/yyyy`.

3. **Geração e Exibição**
   - O sistema busca os eventos conforme o filtro selecionado, utilizando o método único `generateReport`.
   - Os eventos encontrados são exibidos na tela, mostrando informações como tipo, título, modalidade, data e local.

4. **Exportação do Relatório**
   - Após visualizar o relatório, o sistema pergunta se você deseja exportar o relatório para um arquivo `.json`.
   - Se confirmado, o relatório é salvo automaticamente em uma pasta específica, com um nome que reflete o filtro utilizado e a data/hora da exportação.

#### Implementação com método único

A geração dos relatórios é feita por meio do método `generateReport(Scanner scan, EventReportOption reportOption)` da classe `EventFunctionalities`. Esse método recebe como parâmetro o tipo de relatório desejado (por tipo ou por data) e executa o fluxo de seleção, busca, exibição e exportação dos eventos.  
A busca dos eventos é feita por métodos do `EventController` que filtram por tipo (`listByType`) ou por data (`listByAttribute`), mas toda a lógica de interação e exportação está centralizada em um único método, tornando o código mais limpo e fácil de manter.

#### Geração e organização dos arquivos .json dos relatórios

Quando o usuário opta por exportar um relatório, o sistema salva o arquivo `.json` em uma estrutura de pastas organizada da seguinte forma:
```
<raiz-do-projeto>
├── relatorios/
│   ├── por_tipo/
│   │   ├── relatorio_tipo_<tipo>_<data>.json
│   │   └── ...
│   └── por_data/
│       ├── relatorio_data_<data>.json
│       └── ...
└── ...
```
- A pasta `relatorios/` contém subpastas `por_tipo/` e `por_data/` para organizar os relatórios gerados por tipo e por data, respectivamente.
- Os arquivos de relatório são nomeados indicando o tipo de evento ou a data filtrada, facilitando a identificação pelo usuário.

#### Detalhes sobre a nomeação dos arquivos de relatório

- **Nome da pasta:**  
  O nome da pasta reflete o tipo de relatório gerado, por exemplo, `Events_reports_by_type` ou `Events_reports_by_date`.
- **Nome do arquivo:**  
  O nome do arquivo é composto pelo valor do filtro (ex: `Course` ou a data escolhida), seguido de um carimbo de data e hora no formato `yyyyMMdd_HHmmss`, garantindo que cada relatório exportado seja único e versionado automaticamente.
- **Sanitização:**  
  Caracteres especiais e espaços são removidos do nome do arquivo para evitar problemas de compatibilidade com o sistema operacional.

#### Exemplo de nome de arquivo gerado

- Relatório por tipo:  
  `Course_20250527_234757.json`  
  (Relatório de eventos do tipo "Course", exportado em 27/05/2025 às 23:47:57)

- Relatório por data:  
  `01_12_2025_20250527_235039.json`  
  (Relatório de eventos do dia 01/12/2025, exportado em 27/05/2025 às 23:50:39)

#### Versionamento automático

O uso do carimbo de data e hora no nome do arquivo garante que cada relatório exportado seja único, evitando sobrescritas e permitindo o histórico de versões dos relatórios gerados. Isso é especialmente útil para auditoria, acompanhamento de alterações e organização dos arquivos exportados.

---

### 4. Generate Test Data

- O sistema solicitará a quantidade de eventos e participantes que você deseja gerar.  
- Para cada tipo de dado:
  - **Eventos**: Serão gerados eventos aleatórios com títulos, datas, modalidades e outros atributos fictícios.  
  - **Participantes**: Serão gerados participantes aleatórios com nomes, CPFs, e-mails e outros atributos fictícios.  
- Após a geração, o sistema exibirá uma mensagem de sucesso indicando a quantidade de dados gerados.  

---

### 5. Exit

- Encerra o sistema e salva os dados nos arquivos JSON (`participants.json` e `events.json`).

---

## Persistência de Dados

- Todos os dados de participantes são salvos no arquivo `participants.json` na raiz do projeto.  
- Todos os dados de eventos são salvos no arquivo `events.json` na raiz do projeto.  
- Ao iniciar, o sistema carrega os dados desses arquivos (ou cria novos se não existirem).  
- Ao sair, os dados são salvos automaticamente.  
- O sistema garante que IDs de participantes e eventos não sejam sobrescritos, mesmo após reiniciar.

---

## Validações

- **CPF:** Validação completa de formato e dígito verificador.  
- **E-mail:** Validação de formato.  
- **Data:** Validação de formato.  
- **Telefone:** Aceita formatos como `71 91234-5678`, `081 91234-5678`, etc.  

---

## Estrutura do Projeto

O projeto está organizado para facilitar a manutenção, entendimento e expansão do sistema. Abaixo, uma explicação das principais pastas e arquivos:

```
acadevents/  
├── participants.json  
├── events.json  
└── src/  
    └── main/  
        └── java/  
            └── acad_events/  
                └── acadevents/  
                    ├── AcadEvents.java  
                    ├── models/  
                    │   ├── event/  
                    │   ├── participant/  
                    ├── controllers/  
                    ├── repositories/  
                    ├── ui/  
                    │   ├── menu/  
                    │   │   ├── subMenus/  
                    │   │   └── enums/  
                    │   └── functionalities/  
                    │       ├── forms/  
                    │       ├── enums/  
                    └── common/  
                        ├── DTOs/  
                        └── utils/  
```

### Resumo das responsabilidades:

- **models/**  
  - **event/**: Entidades relacionadas a eventos.  
  - **participant/**: Entidades relacionadas a participantes.  
 

- **controllers/**  
  Contém os controladores responsáveis por gerenciar a lógica de negócios e intermediar a comunicação entre as camadas de interface e repositórios.  

- **repositories/**  
  Contém a lógica de persistência e manipulação de dados nos arquivos JSON.  

- **ui/**  
  - **menu/**:  
    - **subMenus/**: Contém os menus específicos para cada funcionalidade (ex: `EventMenu`, `ParticipantMenu`, `ReportMenu`).  
    - **enums/**: Enums auxiliares para navegação e seleção de opções nos menus.  
    - **MenuController.java**: Controlador principal que gerencia a navegação entre os menus.  
  - **functionalities/**:  
    - **forms/**: Formulários para entrada de dados de eventos e participantes.  
    - **enums/**: Enums auxiliares para funcionalidades específicas (ex: `EventReportOption`, `InputResult`).  
    - **BaseFunctionalities.java**: Classe base para funcionalidades compartilhadas entre diferentes tipos de operações.  
    - **EventFunctionalities.java**: Funcionalidades relacionadas a eventos (criação, listagem, remoção, relatórios).  
    - **ParticipantFunctionalities.java**: Funcionalidades relacionadas a participantes (cadastro, remoção, listagem, inscrição).  
    - **IntegrationFunctionalities.java**: Funcionalidades de integração entre eventos e participantes (inscrição, geração de certificados).  

- **common/**  
  - **DTOs/**: Objetos de transferência de dados (Data Transfer Objects) usados para comunicação entre camadas.  
  - **utils/**:  
    - **enums/**: Enums compartilhados pelo sistema.  
    - **interfaces/**: Interfaces para padronização de comportamentos no sistema.  
    - **ValidatorInputs.java**: Classe utilitária para validação de entradas do usuário.  
    - **StyleStrings.java**: Strings de estilo para formatação de texto no console.  
    - **TextBoxUtils.java**: Funções utilitárias para exibição de mensagens formatadas no console.  
    - **MenuUtils.java**: Funções utilitárias para manipulação de menus.  
    - **TestDataGenerator.java**: Gerador de dados de participantes e eventos com finalidade de facilitar testes.  

- **AcadEvents.java**  
  Ponto de entrada do sistema.

---

## Tecnologias Utilizadas

- Java 17  
- Maven  
- Gson (para manipulação de JSON)  
- JUnit (para testes)  

---

## Autor

- Projeto desenvolvido para a disciplina INF008 - IFBA  
- Marcus Vinicius Silva da Fonseca 

---