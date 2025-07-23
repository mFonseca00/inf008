# Plugin de Relatórios - README

Este documento descreve o plugin de relatórios (`ReportPlugin`) que faz parte do sistema baseado em microkernel para bibliotecas.

## 📊 Visão Geral

O `ReportPlugin` é um componente plugável que implementa funcionalidades de geração de relatórios para o sistema Alexandria, seguindo uma arquitetura de microkernel. Este plugin fornece uma interface gráfica completa para:

- 📈 Visualizar ranking de usuários com mais empréstimos
- 📚 Consultar ranking de livros mais emprestados
- 📋 Listar empréstimos ativos (não devolvidos)
- 💾 Exportar relatórios para CSV
- 🔄 Atualizar dados dos relatórios em 1 clique

## 🏗️ Estrutura do Plugin

```
reportplugin/
├── pom.xml                           # Configuração Maven
├── README.md                         # Esta documentação
└── src/main/
    ├── java/br/edu/ifba/inf008/plugins/
    │   ├── ReportPlugin.java         # Classe principal do plugin
    │   ├── controller/
    │   │   └── ReportController.java # Controlador MVC
    │   ├── service/
    │   │   └── ReportService.java    # Serviço de acesso aos dados de relatórios
    │   └── ui/
    │       ├── ReportUIUtils.java    # Utilitários de UI
    │       └── components/
    │           ├── ReportTableFactory.java    # Fábrica para tabelas de relatórios
    │           └── ReportMessageUtils.java    # Utilitários de mensagens
    │           └── ReportExporter.java        # Classe que realiza exportação de relatórios
    └── resources/
        ├── fxml/
        │   └── ReportView.fxml       # Interface FXML
        └── styles/
            └── report-theme.css      # Estilos específicos do plugin
```

## ⚙️ Funcionalidades Detalhadas

### 📈 Ranking de Usuários

**Informações exibidas:**
- **Posição no ranking** (listado do maior para o menor)
- **Nome do usuário**
- **Email do usuário**
- **Total de empréstimos realizados**

**Características:**
- Ordenação decrescente por número de empréstimos
- Inclui usuários com pelo menos 1 empréstimo
- Atualização em tempo em 1 passo
- Possibilidade de exportação para CSV

**Consulta SQL subjacente:**
```sql
SELECT u.name, u.email, COUNT(l.loan_id) as total_loans
FROM User u 
INNER JOIN Loan l ON u.user_id = l.user_id
GROUP BY u.name, u.email
ORDER BY total_loans DESC
```

### 📚 Ranking de Livros

**Informações exibidas:**
- **Posição no ranking** (listado do maior para o menor)
- **Título do livro**
- **Autor**
- **ISBN**
- **Total de empréstimos** históricos

**Características:**
- Ordenação decrescente por número de empréstimos
- Inclui livros com pelo menos 1 empréstimo
- Dados úteis para aquisição de novos exemplares

**Consulta SQL subjacente:**
```sql
SELECT b.title, b.author, b.isbn, COUNT(l.loan_id) as total_loans
FROM Book b 
INNER JOIN Loan l ON b.book_id = l.book_id
GROUP BY b.title, b.author, b.isbn
ORDER BY total_loans DESC
```

### 📋 Empréstimos Ativos

**Informações exibidas:**
- **ID do empréstimo**
- **Nome do usuário**
- **Título do livro**
- **Autor do livro**
- **Data de empréstimo**
- **Dias em aberto** (calculado automaticamente)

**Características:**
- Lista apenas empréstimos não devolvidos (`l.returnDate IS NULL`)
- Cálculo automático de dias decorridos
- Ordenação por data de empréstimo (mais antigos primeiro)

**Consulta SQL subjacente:**
```sql
SELECT l FROM Loan l
JOIN FETCH l.book
JOIN FETCH l.user
WHERE l.returnDate IS NULL
ORDER BY l.loanDate ASC
```

### 💾 Exportação para CSV

**Funcionalidades de exportação:**
- **Botão dedicado** para cada tipo de relatório
- **Seletor de arquivo** para escolher local de salvamento
- **Formato padrão CSV** com separador vírgula
- **Cabeçalhos descritivos** em português
- **Encoding UTF-8** para suporte a acentos

**Exemplo de arquivo CSV gerado:**
```csv
"Posição","Nome","Email","Total de Empréstimos","Porcentagem"
"1","João Silva","joao@email.com","15","23.08%"
"2","Maria Santos","maria@email.com","12","18.46%"
"3","Pedro Lima","pedro@email.com","8","12.31%"
```

## 🎨 Interface do Usuário

### Sistema de Abas

A interface é organizada em **três abas principais**:

1. **📈 Usuários Mais Ativos**
   - Tabela com ranking de usuários
   - Botão de exportação específico
   - Botão de atualização

2. **📚 Livros Mais Emprestados**
   - Tabela com ranking de livros
   - Botão de exportação específico
   - Botão de atualização

3. **📋 Empréstimos Ativos**
   - Lista de empréstimos não devolvidos
   - Cálculo de dias em aberto
   - Identificação de atrasos
   - Botão de exportação específico
   - Botão de atualização

### Sistema de Mensagens

```java
// Mensagens de sucesso (verde)
ReportUIUtils.displaySuccessMessage(lblMessage, "Relatório exportado com sucesso!");

// Mensagens de erro (vermelho)  
ReportUIUtils.displayErrorMessage(lblMessage, "Erro ao gerar relatório");

// Mensagens de informação (azul)
ReportUIUtils.displayInfoMessage(lblMessage, "Carregando dados...");

// Limpar mensagens
ReportUIUtils.clearMessage(lblMessage);
```

## 🚀 Uso

1. **Garantir dados existem**:
   - Cadastrar usuários (UserPlugin)
   - Cadastrar livros (BookPlugin)  
   - Registrar empréstimos (LoanPlugin)

2. **Acessar relatórios**:
   - Clicar na aba "Geral" e "Visualizar Relatórios"
   - Navegar entre as sub-abas
   - Usar botões de atualização e exportação

3. **Interpretar dados**:
   - Rankings mostram tendências de uso
   - Empréstimos ativos indicam gestão necessária

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