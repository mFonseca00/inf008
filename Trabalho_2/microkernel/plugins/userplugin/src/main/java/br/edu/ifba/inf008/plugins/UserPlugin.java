package br.edu.ifba.inf008.plugins;

import java.io.IOException;

import br.edu.ifba.inf008.interfaces.ILibraryPlugin;
import br.edu.ifba.inf008.interfaces.IPluginUI;
import br.edu.ifba.inf008.interfaces.models.User;
import br.edu.ifba.inf008.plugins.controller.UserController;
import br.edu.ifba.inf008.plugins.service.UserService;
import br.edu.ifba.inf008.plugins.ui.UserUIUtils;
import br.edu.ifba.inf008.plugins.ui.components.UserTableFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UserPlugin implements IPluginUI, ILibraryPlugin
{
    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Label lblMessage;
    @FXML private TextField txtSearch;
    @FXML private TableView<User> tableUsers;
    @FXML private ComboBox<String> cmbSearchType;
    @FXML private Button btnSearch;
    @FXML private Button btnClear;
    @FXML private Button btnRefresh;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    
    private UserController controller;
    private UserService userService = new UserService();
    
    @Override
    public boolean init() {
        System.out.println("UserPlugin inicializado!");
        controller = new UserController(userService);
        return true;
    }
    
    @Override
    public String getLibraryFeatureType() {
        return "user";
    }

    @Override
    public String getMenuCategory() {
        return "Gerenciamento";
    }

    @Override
    public String getMenuItemName() {
        return "Gerenciar Usuários";
    }

    @Override
    public String getTabTitle() {
        return "Usuários";
    }
    
    @Override
    public Node createTabContent() {
        try {
            java.net.URL fxmlUrl = getClass().getResource("/fxml/UserView.fxml");
            
            if (fxmlUrl == null) {
                throw new IOException("Arquivo FXML não encontrado: /fxml/UserView.fxml");
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setController(this);
            
            Node root = loader.load();
            
            setupComponents();
            
            return root;
        } catch (Exception e) {
            e.printStackTrace();
            
            return UserUIUtils.createErrorContainer(
                "Não foi possível carregar a interface",
                "Ocorreu um erro ao carregar a interface do plugin de usuários.",
                e.getMessage()
            );
        }
    }
    
    private void setupComponents() {
        UserTableFactory.configureTable(tableUsers);

        setupSearchOptions();
        
        setupButtonActions();
        
        controller.initialize(
            txtName, txtEmail, btnSave, btnCancel, lblMessage,
            txtSearch, tableUsers, cmbSearchType
        );
    }

    private void setupSearchOptions() {
        cmbSearchType.getItems().addAll("Nome", "Email");
        cmbSearchType.setValue("Nome");
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