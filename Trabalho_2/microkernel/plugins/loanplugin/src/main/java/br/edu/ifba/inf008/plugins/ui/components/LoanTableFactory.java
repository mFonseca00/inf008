package br.edu.ifba.inf008.plugins.ui.components;

import java.time.format.DateTimeFormatter;

import br.edu.ifba.inf008.interfaces.models.Loan;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LoanTableFactory {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void configureTable(TableView<Loan> tableView) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Loan, Integer> loanIdColumn = new TableColumn<>("ID");
        loanIdColumn.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        loanIdColumn.setPrefWidth(50);
        
        TableColumn<Loan, String> userColumn = new TableColumn<>("Usuário");
        userColumn.setCellValueFactory(cellData -> {
            Loan loan = cellData.getValue();
            return javafx.beans.binding.Bindings.createStringBinding(
                () -> loan.getUser() != null ? loan.getUser().getName() : ""
            );
        });
        userColumn.setPrefWidth(150);
        
        TableColumn<Loan, String> userEmailColumn = new TableColumn<>("Email");
        userEmailColumn.setCellValueFactory(cellData -> {
            Loan loan = cellData.getValue();
            return javafx.beans.binding.Bindings.createStringBinding(
                () -> loan.getUser() != null ? loan.getUser().getEmail() : ""
            );
        });
        userEmailColumn.setPrefWidth(180);
        
        TableColumn<Loan, String> bookColumn = new TableColumn<>("Livro");
        bookColumn.setCellValueFactory(cellData -> {
            Loan loan = cellData.getValue();
            return javafx.beans.binding.Bindings.createStringBinding(
                () -> loan.getBook() != null ? loan.getBook().getTitle() : ""
            );
        });
        bookColumn.setPrefWidth(200);
        
        TableColumn<Loan, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> {
            Loan loan = cellData.getValue();
            return javafx.beans.binding.Bindings.createStringBinding(
                () -> loan.getBook() != null ? loan.getBook().getIsbn() : ""
            );
        });
        isbnColumn.setPrefWidth(120);
        
        TableColumn<Loan, String> loanDateColumn = new TableColumn<>("Data de Empréstimo");
        loanDateColumn.setCellValueFactory(cellData -> {
            Loan loan = cellData.getValue();
            return javafx.beans.binding.Bindings.createStringBinding(
                () -> loan.getLoanDate() != null ? loan.getLoanDate().format(DATE_FORMATTER) : ""
            );
        });
        loanDateColumn.setPrefWidth(120);
        
        TableColumn<Loan, String> returnDateColumn = new TableColumn<>("Data de Devolução");
        returnDateColumn.setCellValueFactory(cellData -> {
            Loan loan = cellData.getValue();
            return javafx.beans.binding.Bindings.createStringBinding(
                () -> loan.getReturnDate() != null ? loan.getReturnDate().format(DATE_FORMATTER) : ""
            );
        });
        returnDateColumn.setPrefWidth(120);
        
        TableColumn<Loan, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> {
            Loan loan = cellData.getValue();
            return javafx.beans.binding.Bindings.createStringBinding(
                () -> loan.getReturnDate() != null ? "Finalizado" : "Ativo"
            );
        });
        statusColumn.setCellFactory(column -> new javafx.scene.control.TableCell<Loan, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ("Ativo".equals(item)) {
                        setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                    }
                }
            }
        });
        statusColumn.setPrefWidth(80);
        
        tableView.getColumns().setAll(loanIdColumn, userColumn, userEmailColumn, bookColumn, isbnColumn, loanDateColumn, returnDateColumn, statusColumn);
        
    }
}
