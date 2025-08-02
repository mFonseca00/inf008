# Plugin de Relatórios

## 📊 Visão Geral

O `ReportPlugin` é um plugin do sistema Alexandria que fornece funcionalidades de geração de relatórios e estatísticas da biblioteca. Oferece visualizações sobre usuários mais ativos, livros mais emprestados e empréstimos em aberto.

Este plugin fornece uma interface gráfica completa para:

- 📈 Visualizar ranking de usuários com mais empréstimos
- 📚 Consultar ranking de livros mais emprestados
- 📋 Listar empréstimos ativos (não devolvidos)
- 💾 Exportar relatórios para CSV
- 🔁 Atualização automática de informações dos relatórios

## 🏗️ Estrutura do Plugin

```
reportplugin/
├── pom.xml
├── README.md
└── src/main/
    ├── java/br/edu/ifba/inf008/plugins/
    │   ├── ReportPlugin.java                  # Classe principal do plugin
    │   ├── controller/
    │   │   └── ReportController.java          # Controlador MVC
    │   ├── persistence/
    │   │   ├── ReportDAO.java                 # Data Access Object
    │   │   └── interfaces/                    # Interfaces de persistência
    │   ├── service/
    │   │   └── ReportService.java               # Serviços de negócio
    │   └── ui/                                # Componentes de interface
    └── resources/
        ├── fxml/
        │   └── ReportView.fxml                # Layout da interface
        └── styles/
            └── report-theme.css               # Estilos específicos
```

## ⚙️ Funcionalidades

### 📈 Ranking de Usuários Mais Empréstimos
- **Dados**: Nome, email, total de empréstimos
- **Ordenação**: Decrescente por número de empréstimos
- **Exemplo**:
  ```
  1º João Silva (joao@email.com) - 15 empréstimos
  2º Maria Santos (maria@email.com) - 12 empréstimos
  ```

### 📚 Ranking de Livros Mais Emprestados
- **Dados**: Título, autor, ISBN, total de empréstimos
- **Ordenação**: Decrescente por popularidade
- **Exemplo**:
  ```
  1º Clean Code (Robert Martin) - 25 empréstimos
  2º Design Patterns (Gang of Four) - 18 empréstimos
  ```

### 📋 Empréstimos Ativos
- **Dados**:Id do empréstimo, Usuário, livro, autor, data de empréstimo, dias em aberto
- **Características**: Apenas empréstimos não devolvidos
- **Cálculo**: Dias corridos desde o empréstimo

### 💾 Exportação para CSV
- **Formatos**: CSV com encoding UTF-8
- **Localização**: Diretório escolhido pelo usuário
- **Cabeçalhos**: Em português para melhor legibilidade

## 🖥️ Fluxo de Uso da Interface

1. **Acessar Plugin**
   - Menu "Geral" → "Visualizar Relatórios"
   - Interface carrega com três abas internas de relatórios

2. **Visualizar Ranking de Usuários**
   - Clicar na aba "Usuários Mais Ativos"
   - Analisar popularidade dos títulos
   - Clicar "Exportar" para salvar CSV

3. **Visualizar Ranking de Livros**
   - Clicar na aba "Livros Mais Emprestados"
   - Analisar popularidade dos títulos
   - Clicar "Exportar" para salvar CSV

4. **Monitorar Empréstimos Ativos**
   - Clicar na aba "Empréstimos Ativos"
   - Verificar empréstimos em aberto
   - Identificar possíveis atrasos
   - Clicar "Exportar" para salvar CSV

5. **Exportar Relatórios**
   - Usar botão "Exportar" em cada aba
   - Escolher local de salvamento
   - Arquivo CSV gerado automaticamente

## 📚 Links Relacionados

- [📖 README Principal](../../../README.md)
- [🏗️ README do Microkernel](../../README.md)
- [📚 Plugin de Livros](../bookplugin/README.md)
- [👥 Plugin de Usuários](../userplugin/README.md)
- [📋 Plugin de Empréstimos](../loanplugin/README.md)

---

**Desenvolvido por:** Marcus Vinicius Silva da Fonseca  
**Disciplina:** INF008 - POO  
**Instituição:** IFBA