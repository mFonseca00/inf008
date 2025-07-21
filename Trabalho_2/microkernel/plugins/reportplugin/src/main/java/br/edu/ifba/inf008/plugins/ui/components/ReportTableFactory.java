package br.edu.ifba.inf008.plugins.ui.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.edu.ifba.inf008.interfaces.models.Loan;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportTableFactory {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * Configura a tabela de ranking de usuários
     */
    public static void configureUserRankingTable(TableView<Object[]> table) {
        table.getColumns().clear();
        
        // Coluna Nome
        TableColumn<Object[], String> nameColumn = new TableColumn<>("Nome do Usuário");
        nameColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[0]));
        nameColumn.setPrefWidth(200);
        
        // Coluna Email
        TableColumn<Object[], String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[1]));
        emailColumn.setPrefWidth(250);
        
        // Coluna Quantidade de Empréstimos
        TableColumn<Object[], String> countColumn = new TableColumn<>("Empréstimos");
        countColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2].toString()));
        countColumn.setPrefWidth(120);
        
        table.getColumns().addAll(nameColumn, emailColumn, countColumn);
    }
    
    /**
     * Configura a tabela de ranking de livros
     */
    public static void configureBookRankingTable(TableView<Object[]> table) {
        table.getColumns().clear();
        
        // Coluna Título
        TableColumn<Object[], String> titleColumn = new TableColumn<>("Título");
        titleColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[0]));
        titleColumn.setPrefWidth(250);
        
        // Coluna Autor
        TableColumn<Object[], String> authorColumn = new TableColumn<>("Autor");
        authorColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[1]));
        authorColumn.setPrefWidth(200);
        
        // Coluna ISBN
        TableColumn<Object[], String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[2]));
        isbnColumn.setPrefWidth(150);
        
        // Coluna Quantidade de Empréstimos
        TableColumn<Object[], String> countColumn = new TableColumn<>("Empréstimos");
        countColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3].toString()));
        countColumn.setPrefWidth(120);
        
        table.getColumns().addAll(titleColumn, authorColumn, isbnColumn, countColumn);
    }
    
    /**
     * Configura a tabela de empréstimos ativos
     */
    public static void configureActiveLoansTable(TableView<Loan> table) {
        table.getColumns().clear();
        
        // Coluna ID
        TableColumn<Loan, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        idColumn.setPrefWidth(80);
        
        // Coluna Usuário
        TableColumn<Loan, String> userColumn = new TableColumn<>("Usuário");
        userColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getUser().getName()));
        userColumn.setPrefWidth(200);
        
        // Coluna Livro
        TableColumn<Loan, String> bookColumn = new TableColumn<>("Livro");
        bookColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getBook().getTitle()));
        bookColumn.setPrefWidth(200);
        
        // Coluna Autor
        TableColumn<Loan, String> authorColumn = new TableColumn<>("Autor");
        authorColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getBook().getAuthor()));
        authorColumn.setPrefWidth(150);
        
        // Coluna Data do Empréstimo
        TableColumn<Loan, String> loanDateColumn = new TableColumn<>("Data do Empréstimo");
        loanDateColumn.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getLoanDate();
            return new javafx.beans.property.SimpleStringProperty(
                date != null ? date.format(DATE_FORMATTER) : "");
        });
        loanDateColumn.setPrefWidth(150);
        
        // Coluna Dias em Aberto
        TableColumn<Loan, String> daysOpenColumn = new TableColumn<>("Dias em Aberto");
        daysOpenColumn.setCellValueFactory(cellData -> {
            LocalDate loanDate = cellData.getValue().getLoanDate();
            if (loanDate != null) {
                long days = java.time.temporal.ChronoUnit.DAYS.between(loanDate, LocalDate.now());
                return new javafx.beans.property.SimpleStringProperty(String.valueOf(days));
            }
            return new javafx.beans.property.SimpleStringProperty("0");
        });
        daysOpenColumn.setPrefWidth(120);
        
        table.getColumns().addAll(idColumn, userColumn, bookColumn, authorColumn, loanDateColumn, daysOpenColumn);
    }
}