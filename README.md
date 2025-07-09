# AcadEvents

Sistema de Gerenciamento de Participantes e Eventos Acadêmicos

---

## Sumário

- [Sobre o Projeto](#sobre-o-projeto)
- [Como Executar](#como-executar)
- [Fluxo de Telas e Funcionalidades](#fluxo-de-telas-e-funcionalidades)
- [Sistema de Relatórios](#sistema-de-relatórios)
- [Persistência de Dados](#persistência-de-dados)
- [Validações](#validações)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Autor](#autor)

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
   ```bash
   git clone https://github.com/mFonseca00/inf008.git
   cd Trabalho_1/acadevents
   ```

2. **Compile o projeto:**  
   ```bash
   mvn clean package
   ```

3. **Execute o sistema usando Maven:**  
   ```bash
   mvn exec:java
   ```

   **Observação:** Se o comando `mvn exec:java` não funcionar, certifique-se de que o `pom.xml` contém a configuração do plugin:

   ```xml
   <plugin>
     <groupId>org.codehaus.mojo</groupId>
     <artifactId>exec-maven-plugin</artifactId>
     <version>3.1.0</version>
     <configuration>
       <mainClass>acad_events.acadevents.AcadEvents</mainClass>
     </configuration>
   </plugin>
   ```

---

## Fluxo de Telas e Funcionalidades

### Menu Principal

Ao iniciar, você verá o menu principal com as opções:

1. **Manage Events**: Gerenciar eventos (criar, deletar, listar)
2. **Manage Participants**: Gerenciar participantes (cadastrar, remover, listar, inscrever em evento, gerar certificado)
3. **Generate Reports**: Gerar relatórios por tipo ou data
4. **Generate Test Data**: Gerar dados de teste aleatórios
5. **Exit**: Sair do sistema

Selecione a opção desejada digitando o número correspondente.

---

### Menu de Participantes

Ao escolher "Manage Participants", você verá:

1. **Register new participant**: Cadastrar novo participante
2. **Delete participant**: Remover participante pelo CPF
3. **List participants**: Listar todos os participantes cadastrados
4. **Enroll participant in event**: Inscrever participante em evento
5. **Generate a event certificate**: Gerar certificado de participação
6. **Return to Main Menu**: Voltar ao menu principal

#### 1. Register new participant

- Informe o CPF (formato: `000.000.000-00`), nome, e-mail, telefone (ex: `71 91234-5678`)
- Escolha o tipo: Student, Professor ou External
- Para Student: informe matrícula
- Para Professor: informe ID do funcionário e departamento
- Para External: informe organização (opcional) e papel (role)
- O participante será cadastrado se os dados forem válidos e o CPF não estiver em uso

#### 2. Delete participant

- Informe o CPF do participante a ser removido
- O sistema confirmará a remoção ou informará se não encontrou o participante

#### 3. List participants

- Exibe todos os participantes cadastrados, mostrando CPF, nome e e-mail

#### 4. Enroll participant in event

- Informe o CPF do participante
- Escolha o evento desejado (será exibida uma lista)
- O sistema verifica se há vagas e se o tipo do participante é permitido para o evento
- Se tudo estiver correto, a inscrição é realizada

#### 5. Generate a event certificate

- Informe o CPF do participante
- Escolha o evento em que ele está inscrito
- O sistema gera um certificado textual com os dados do evento e do participante
- Poderá ser selecionada a opção de exportar o certificado em TXT ou não

#### 6. Return to Main Menu

- Retorna ao menu principal do sistema

---

### Menu de Eventos

Ao escolher "Manage Events", você verá:

1. **Create Event**: Criar um novo evento acadêmico
2. **Delete Event**: Remover um evento existente
3. **List Events**: Listar todos os eventos cadastrados
4. **Return to Main Menu**: Voltar ao menu principal

#### 1. Create Event

- O sistema solicitará os dados comuns do evento:
  - Título
  - Data (formato: dd/MM/yyyy)
  - Local
  - Capacidade (número máximo de participantes)
  - Descrição
  - Modalidade (Presencial, Online ou Híbrido)
- Em seguida, você escolherá o tipo de evento:
  - **Course**: Informe coordenador, área de conhecimento e carga horária total
  - **Lecture**: Informe o palestrante
  - **Workshop**: Informe o instrutor e a duração em horas
  - **Fair**: Informe o organizador e o número de estandes
- O evento será criado e salvo se não houver outro evento com o mesmo título e data

#### 2. Delete Event

- Você pode escolher como deseja remover o evento:
  - **Remove by an attribute list**: Escolha um atributo, informe o valor e selecione da lista de eventos encontrados
  - **Remove from all events list**: Selecione da lista completa de eventos
  - **Remove from an ID**: Informe o ID do evento a ser removido
- O sistema confirmará a remoção ou informará se não encontrou o evento

#### 3. List Events

- Exibe todos os eventos cadastrados, mostrando tipo, título, modalidade, data e local

#### 4. Return to Main Menu

- Retorna ao menu principal do sistema

---

### Menu de Relatórios

Ao escolher "Generate Reports", você verá:

1. **Report by event type**: Gera relatório filtrando por tipo (Course, Lecture, Workshop, Fair)
2. **Report by date**: Gera relatório filtrando por data
3. **Return to Main Menu**: Volta ao menu principal

#### 1. Report by event type

- Escolha o tipo de evento desejado
- O sistema exibirá todos os eventos cadastrados daquele tipo

#### 2. Report by date

- Informe a data desejada (formato: dd/MM/yyyy)
- O sistema exibirá todos os eventos cadastrados para aquela data

#### 3. Return to Main Menu

- Retorna ao menu principal do sistema

---

### Generate Test Data

- O sistema solicitará a quantidade de eventos e participantes que você deseja gerar
- Para cada tipo de dado:
  - **Eventos**: Serão gerados eventos aleatórios com títulos, datas, modalidades e outros atributos fictícios
  - **Participantes**: Serão gerados participantes aleatórios com nomes, CPFs, e-mails e outros atributos fictícios
- Após a geração, o sistema exibirá uma mensagem de sucesso indicando a quantidade de dados gerados

---

### Exit

- Encerra o sistema e salva os dados nos arquivos JSON (`participants.json` e `events.json`)

---

## Sistema de Relatórios

O sistema permite gerar relatórios de eventos acadêmicos com duas opções de filtro: **por tipo de evento** ou **por data**.

### Implementação Unificada

- Utiliza o método único `generateReport(Scanner scan, EventReportOption reportOption)` da classe `EventFunctionalities`
- Recebe como parâmetro o tipo de relatório desejado e executa o fluxo completo
- Busca eventos usando métodos do `EventController` (`listByType` ou `listByAttribute`)
- Centraliza toda a lógica de interação e exportação em um único método

### Passo a Passo do Funcionamento

1. **Acesso ao Menu de Relatórios**: Selecione "Generate Reports" no menu principal
2. **Seleção do Filtro**: Escolha entre relatório por tipo ou por data
3. **Geração e Exibição**: Sistema busca e exibe eventos conforme filtro selecionado
4. **Exportação do Relatório**: Opção de salvar relatório em arquivo JSON

### Exportação e Organização

- **Formato**: Arquivos JSON
- **Estrutura de pastas automática**: 
  - `Events_reports_by_type/` para relatórios por tipo
  - `Events_reports_by_date/` para relatórios por data
- **Nomenclatura**: `[Filtro]_[yyyyMMdd_HHmmss].json`
- **Sanitização**: Caracteres especiais e espaços são removidos automaticamente
- **Versionamento**: Timestamp único para evitar sobrescritas

**Exemplos de arquivos gerados:**
- `Course_20250527_234757.json` (relatório por tipo Course)
- `01_12_2025_20250527_235039.json` (relatório por data 01/12/2025)

### Características Adicionais

- Criação automática das pastas de destino se não existirem
- Histórico de versões para auditoria e acompanhamento
- Sistema de backup automático para evitar perda de dados

---

## Persistência de Dados

- **Arquivos**: `participants.json` e `events.json` na raiz do projeto
- **Carregamento**: Automático na inicialização (cria arquivos vazios se não existirem)
- **Salvamento**: Automático ao sair do sistema
- **Integridade**: Preservação de IDs únicos entre sessões para evitar conflitos
- **Backup**: Sistema mantém integridade dos dados mesmo após reinicializações

---

## Validações

- **CPF**: Validação completa de formato (`000.000.000-00`) e dígito verificador
- **E-mail**: Validação de formato padrão
- **Data**: Formato dd/MM/yyyy obrigatório
- **Telefone**: Múltiplos formatos aceitos (`71 91234-5678`, `081 91234-5678`)

---

## Estrutura do Projeto

```
acadevents/
├── participants.json
├── events.json
├── certificates/                          # Certificados gerados
├── reports/                               # Relatórios exportados
│   ├── Events_reports_by_type/
│   └── Events_reports_by_date/
└── src/main/java/acad_events/acadevents/
    ├── AcadEvents.java                    # Ponto de entrada
    ├── models/                            # Entidades
    │   ├── event/                         # Modelos de eventos
    │   └── participant/                   # Modelos de participantes
    ├── controllers/                       # Lógica de negócio
    ├── repositories/                      # Persistência JSON
    ├── ui/                                # Interface do usuário
    │   ├── menu/                          # Sistema de menus
    │   │   ├── subMenus/                  # Menus específicos
    │   │   └── enums/                     # Enums de navegação
    │   └── functionalities/               # Funcionalidades principais
    │       ├── forms/                     # Formulários de entrada
    │       ├── enums/                     # Enums de funcionalidades
    │       ├── BaseFunctionalities.java
    │       ├── EventFunctionalities.java
    │       ├── ParticipantFunctionalities.java
    │       └── IntegrationFunctionalities.java
    └── common/                            # Utilitários e DTOs
        ├── DTOs/                          # Objetos de transferência
        └── utils/                         # Utilitários gerais
            ├── enums/                     # Enums compartilhados
            ├── interfaces/                # Interfaces do sistema
            ├── ValidatorInputs.java       # Validações
            ├── StyleStrings.java          # Formatação de texto
            ├── TextBoxUtils.java          # Mensagens formatadas
            ├── MenuUtils.java             # Utilitários de menu
            └── TestDataGenerator.java     # Gerador de dados teste
```

### Responsabilidades das Camadas:

- **models/**: Entidades de domínio (eventos e participantes)
- **controllers/**: Lógica de negócios e coordenação entre camadas
- **repositories/**: Persistência e manipulação de dados em JSON
- **ui/**: Interface do usuário, sistema de menus e funcionalidades interativas
- **common/**: DTOs, utilitários compartilhados e componentes auxiliares

---

## Tecnologias Utilizadas

- **Java 17**
- **Maven** (gerenciamento de dependências e build)
- **Gson** (manipulação de JSON para persistência)

---

## Autor

Projeto desenvolvido para a disciplina INF008 - IFBA  
**Marcus Vinicius Silva da Fonseca**

---