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
import br.edu.ifba.inf008.plugins.ui.LoanUIUtils;
import br.edu.ifba.inf008.plugins.ui.components.LoanTableFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.scene.control.Tooltip;
import br.edu.ifba.inf008.interfaces.ITabRefreshable;

public class LoanPlugin implements IPluginUI, ILibraryPlugin, ITabRefreshable
{
    @FXML private ComboBox<User> cmbUser;
    @FXML private ComboBox<Book> cmbBook;
    @FXML private TextField txtUserFilter;
    @FXML private TextField txtBookFilter;
    @FXML private CheckBox chkShowFilters;
    @FXML private VBox filtersContainer;
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
        return "Gerenciamento";
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
            
            return LoanUIUtils.createErrorContainer(
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
            cmbUser, cmbBook, txtUserFilter, txtBookFilter, dtpLoanDate, dtpReturnDate, lblReturnDate,
            btnSave, btnCancel, lblMessage, txtSearch, tableLoans, cmbSearchType
        );
    }

    private void setupComboBoxes() {
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
        
        dtpLoanDate.setValue(LocalDate.now());
        
    }
    
    private void setupSearchOptions() {
        cmbSearchType.getItems().addAll("Usuário", "Livro", "Data de empréstimo", "Data de devolução");
        cmbSearchType.setValue("Usuário");
    }
    
    private void setupButtonActions() {
        btnSave.setOnAction(event -> controller.handleSave());
        btnCancel.setOnAction(event -> controller.handleCancel());
        btnSearch.setOnAction(event -> controller.handleSearch());
        btnClear.setOnAction(event -> controller.handleClear());
        btnRefresh.setOnAction(event -> controller.handleRefresh());
        
        chkShowFilters.setOnAction(event -> {
            boolean showFilters = chkShowFilters.isSelected();
            filtersContainer.setVisible(showFilters);
            filtersContainer.setManaged(showFilters);
            if (!showFilters) {
                controller.clearFilters();
            }
        });
        
        if (btnDelete != null) {
            btnDelete.setOnAction(event -> controller.handleDelete());
            Tooltip tooltip = new javafx.scene.control.Tooltip(
                "Somente empréstimos já devolvidos podem ser excluídos.\n" +
                "Empréstimos ativos precisam ser devolvidos primeiro."
            );
            tooltip.getStyleClass().add("tooltip");
            btnDelete.setTooltip(tooltip);
        }
        
        if (btnEdit != null) {
            btnEdit.setOnAction(event -> controller.handleEdit());
        }
        
        if (btnReturn != null) {
            btnReturn.setOnAction(event -> controller.handleReturn());
        }
    }

    @Override
    public void refreshTab() {
        if (controller != null) {
            controller.loadUsers();
            controller.loadBooks();
            controller.handleRefresh(); // Se desejar atualizar a tabela de empréstimos também
        }
    }
}
