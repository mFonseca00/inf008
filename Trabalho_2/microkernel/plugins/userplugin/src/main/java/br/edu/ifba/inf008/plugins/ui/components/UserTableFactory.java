package br.edu.ifba.inf008.plugins.ui.components;

import br.edu.ifba.inf008.interfaces.models.User;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

public class UserTableFactory {
    

    public static void configureTable(TableView<User> tableView) {
        TableColumn<User, Integer> idColumn = createIdColumn();
        TableColumn<User, String> nameColumn = createNameColumn();
        TableColumn<User, String> emailColumn = createEmailColumn();
        TableColumn<User, LocalDateTime> dateColumn = createDateColumn();
        
        tableView.getColumns().addAll(idColumn, nameColumn, emailColumn, dateColumn);
    }
    
    private static TableColumn<User, Integer> createIdColumn() {
        TableColumn<User, Integer> column = new TableColumn<>("ID");
        column.setCellValueFactory(new PropertyValueFactory<>("userId"));
        return column;
    }
    
    private static TableColumn<User, String> createNameColumn() {
        TableColumn<User, String> column = new TableColumn<>("Nome");
        column.setCellValueFactory(new PropertyValueFactory<>("name"));
        column.setPrefWidth(200);
        return column;
    }
    
    private static TableColumn<User, String> createEmailColumn() {
        TableColumn<User, String> column = new TableColumn<>("Email");
        column.setCellValueFactory(new PropertyValueFactory<>("email"));
        column.setPrefWidth(200);
        return column;
    }
    
    private static TableColumn<User, LocalDateTime> createDateColumn() {
        TableColumn<User, LocalDateTime> column = new TableColumn<>("Data de Registro");
        column.setCellValueFactory(new PropertyValueFactory<>("registeredAt"));
        column.setPrefWidth(325);
        return column;
    }
}
