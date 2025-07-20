package br.edu.ifba.inf008.plugins.ui.components;

import br.edu.ifba.inf008.interfaces.models.Book;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookTableFactory {

    public static void configureTable(TableView<Book> tableView) {
        TableColumn<Book, Integer> idColumn = createIdColumn();
        TableColumn<Book, String> titleColumn = createTitleColumn();
        TableColumn<Book, String> authorColumn = createAuthorColumn();
        TableColumn<Book, String> isbnColumn = createIsbnColumn();
        TableColumn<Book, Integer> yearColumn = createYearColumn();
        TableColumn<Book, Integer> copiesColumn = createCopiesColumn();

        tableView.getColumns().addAll(idColumn, titleColumn, authorColumn, isbnColumn, yearColumn, copiesColumn);
    }

    private static TableColumn<Book, Integer> createIdColumn() {
        TableColumn<Book, Integer> column = new TableColumn<>("ID");
        column.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        return column;
    }

    private static TableColumn<Book, String> createTitleColumn() {
        TableColumn<Book, String> column = new TableColumn<>("Título");
        column.setCellValueFactory(new PropertyValueFactory<>("title"));
        return column;
    }

    private static TableColumn<Book, String> createAuthorColumn() {
        TableColumn<Book, String> column = new TableColumn<>("Autor");
        column.setCellValueFactory(new PropertyValueFactory<>("author"));
        return column;
    }

    private static TableColumn<Book, String> createIsbnColumn() {
        TableColumn<Book, String> column = new TableColumn<>("ISBN");
        column.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        return column;
    }

    private static TableColumn<Book, Integer> createYearColumn() {
        TableColumn<Book, Integer> column = new TableColumn<>("Ano de Publicação");
        column.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
        return column;
    }

    private static TableColumn<Book, Integer> createCopiesColumn() {
        TableColumn<Book, Integer> column = new TableColumn<>("Cópias Disponíveis");
        column.setCellValueFactory(new PropertyValueFactory<>("copiesAvailable"));
        return column;
    }
}
