package br.edu.ifba.inf008.plugins;

import java.io.IOException;

import br.edu.ifba.inf008.interfaces.ILibraryPlugin;
import br.edu.ifba.inf008.interfaces.IPluginUI;
import br.edu.ifba.inf008.interfaces.models.Book;
import br.edu.ifba.inf008.plugins.controller.BookController;
import br.edu.ifba.inf008.plugins.service.BookService;
import br.edu.ifba.inf008.plugins.ui.UIUtils;
import br.edu.ifba.inf008.plugins.ui.components.BookTableFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BookPlugin implements IPluginUI, ILibraryPlugin
{
    @FXML private TextField txtTitle;
    @FXML private TextField txtAuthor;
    @FXML private TextField txtIsbn;
    @FXML private TextField txtPublicationYear;
    @FXML private TextField txtAvailableCopies;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Label lblMessage;
    @FXML private TextField txtSearch;
    @FXML private TableView<Book> tableBooks;
    @FXML private ComboBox<String> cmbSearchType;
    @FXML private Button btnSearch;
    @FXML private Button btnClear;
    @FXML private Button btnRefresh;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    
    private BookController controller;
    private BookService bookService = new BookService();

    @Override
    public boolean init() {
        System.out.println("BookPlugin inicializado!");
        controller = new BookController(bookService);
        return true;
    }
    
    @Override
    public String getLibraryFeatureType() {
        return "book";
    }

    @Override
    public String getMenuCategory() {
        return "Livros";
    }

    @Override
    public String getMenuItemName() {
        return "Gerenciar Livros";
    }

    @Override
    public String getTabTitle() {
        return "Livros";
    }
    
    @Override
    public Node createTabContent() {
        try {
            java.net.URL fxmlUrl = getClass().getResource("/fxml/BookView.fxml");
            
            if (fxmlUrl == null) {
                throw new IOException("Arquivo FXML não encontrado: /fxml/BookView.fxml");
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setController(this);
            
            Node root = loader.load();
            
            setupComponents();
            
            return root;
        } catch (Exception e) {
            e.printStackTrace();
            
            return UIUtils.createErrorContainer(
                "Não foi possível carregar a interface",
                "Ocorreu um erro ao carregar a interface do plugin de livros.",
                e.getMessage()
            );
        }
    }
    
    private void setupComponents() {
        BookTableFactory.configureTable(tableBooks);

        setupSearchOptions();
        
        setupButtonActions();
        
        controller.initialize(
            txtTitle, txtAuthor, txtIsbn, txtPublicationYear, txtAvailableCopies,
            btnSave, btnCancel, lblMessage, txtSearch, tableBooks, cmbSearchType
        );
    }

    private void setupSearchOptions() {
        cmbSearchType.getItems().addAll("Título", "Autor", "ISBN", "Ano de Publicação");
        cmbSearchType.setValue("Título");
    }
    
    private void setupButtonActions() {
        btnSave.setOnAction(event -> controller.handleSave());
        btnCancel.setOnAction(event -> controller.handleCancel());
        btnSearch.setOnAction(event -> controller.handleSearch());
        btnClear.setOnAction(event -> controller.handleClear());
        btnRefresh.setOnAction(event -> controller.handleRefresh());
        
        if (btnDelete != null) {
            btnDelete.setOnAction(event -> controller.handleDelete());
        }
        
        if (btnEdit != null) {
            btnEdit.setOnAction(event -> controller.handleEdit());
        }
    }
}