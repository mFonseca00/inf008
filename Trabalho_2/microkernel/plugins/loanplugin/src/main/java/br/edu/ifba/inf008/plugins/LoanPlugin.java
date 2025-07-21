package br.edu.ifba.inf008.plugins;

import java.io.IOException;
import java.time.LocalDate;

import br.edu.ifba.inf008.interfaces.ILibraryPlugin;
import br.edu.ifba.inf008.interfaces.IPluginUI;
import br.edu.ifba.inf008.interfaces.models.Book;
import br.edu.ifba.inf008.interfaces.models.Loan;
import br.edu.ifba.inf008.interfaces.models.User;
import br.edu.ifba.inf008.plugins.controller.LoanController;
import br.edu.ifba.inf008.plugins.service.LoanBookService;
import br.edu.ifba.inf008.plugins.service.LoanService;
import br.edu.ifba.inf008.plugins.service.LoanUserService;
import br.edu.ifba.inf008.plugins.ui.UIUtils;
import br.edu.ifba.inf008.plugins.ui.components.LoanTableFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class LoanPlugin implements IPluginUI, ILibraryPlugin
{
    @FXML private ComboBox<User> cmbUser;
    @FXML private ComboBox<Book> cmbBook;
    @FXML private DatePicker dtpLoanDate;
    @FXML private DatePicker dtpReturnDate;
    @FXML private Label lblReturnDate;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Label lblMessage;
    @FXML private TextField txtSearch;
    @FXML private TableView<Loan> tableLoans;
    @FXML private ComboBox<String> cmbSearchType;
    @FXML private Button btnSearch;
    @FXML private Button btnClear;
    @FXML private Button btnRefresh;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnReturn;
    
    private LoanController controller;
    private LoanService loanService = new LoanService();
    private LoanUserService loanUserService = new LoanUserService();
    private LoanBookService loanBookService = new LoanBookService();
    
    @Override
    public boolean init() {
        System.out.println("LoanPlugin inicializado!");
        controller = new LoanController(loanService, loanUserService, loanBookService);
        return true;
    }
    
    @Override
    public String getLibraryFeatureType() {
        return "loan";
    }

    @Override
    public String getMenuCategory() {
        return "Empréstimos";
    }

    @Override
    public String getMenuItemName() {
        return "Gerenciar Empréstimos";
    }

    @Override
    public String getTabTitle() {
        return "Empréstimos";
    }
    
    @Override
    public Node createTabContent() {
        try {
            java.net.URL fxmlUrl = getClass().getResource("/fxml/LoanView.fxml");
            
            if (fxmlUrl == null) {
                throw new IOException("Arquivo FXML não encontrado: /fxml/LoanView.fxml");
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
                "Ocorreu um erro ao carregar a interface do plugin de empréstimos.",
                e.getMessage()
            );
        }
    }
    
    private void setupComponents() {
        LoanTableFactory.configureTable(tableLoans);
        
        setupComboBoxes();
        setupSearchOptions();
        setupButtonActions();
        
        controller.initialize(
            cmbUser, cmbBook, dtpLoanDate, dtpReturnDate, lblReturnDate,
            btnSave, btnCancel, lblMessage, txtSearch, tableLoans, cmbSearchType
        );
    }

    private void setupComboBoxes() {
        // Configurar ComboBox de Usuários
        cmbUser.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user != null ? user.getName() + " (" + user.getEmail() + ")" : "";
            }
            
            @Override
            public User fromString(String string) {
                return null;
            }
        });
        
        // Configurar ComboBox de Livros
        cmbBook.setConverter(new StringConverter<Book>() {
            @Override
            public String toString(Book book) {
                return book != null ? book.getTitle() + " - " + book.getAuthor() + " ( ISBN:" + book.getIsbn() + ")": "";
            }
            
            @Override
            public Book fromString(String string) {
                return null;
            }
        });
        
        // Configurar o DatePicker com a data atual
        dtpLoanDate.setValue(LocalDate.now());
        
    }
    
    private void setupSearchOptions() {
        cmbSearchType.getItems().addAll("Usuário", "Livro", "Data");
        cmbSearchType.setValue("Usuário");
    }
    
    private void setupButtonActions() {
        btnSave.setOnAction(event -> controller.handleSave());
        btnCancel.setOnAction(event -> controller.handleCancel());
        btnSearch.setOnAction(event -> controller.handleSearch());
        btnClear.setOnAction(event -> controller.handleClear());
        btnRefresh.setOnAction(event -> controller.handleRefresh());
        
        if (btnDelete != null) {
            btnDelete.setOnAction(event -> controller.handleDelete());
            // Adiciona uma dica para explicar a restrição de exclusão
            javafx.scene.control.Tooltip tooltip = new javafx.scene.control.Tooltip(
                "Somente empréstimos já devolvidos podem ser excluídos.\n" +
                "Empréstimos ativos precisam ser devolvidos primeiro."
            );
            tooltip.setStyle("-fx-font-size: 12px;");
            btnDelete.setTooltip(tooltip);
        }
        
        if (btnEdit != null) {
            btnEdit.setOnAction(event -> controller.handleEdit());
        }
        
        if (btnReturn != null) {
            btnReturn.setOnAction(event -> controller.handleReturn());
        }
    }
}
