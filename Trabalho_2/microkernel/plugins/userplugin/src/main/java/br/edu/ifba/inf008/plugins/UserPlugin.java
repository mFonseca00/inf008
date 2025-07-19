package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IPluginUI;
import br.edu.ifba.inf008.interfaces.ILibraryPlugin;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.models.User;
import br.edu.ifba.inf008.plugins.service.UserService;
import br.edu.ifba.inf008.plugins.util.ValidationService;
import br.edu.ifba.inf008.plugins.ui.UIUtils;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    
    private Integer editingUserId = null;
    private UserService userService = new UserService();
    
    @Override
    public boolean init() {
        System.out.println("UserPlugin inicializado!");
        return true;
    }
    
    @Override
    public String getLibraryFeatureType() {
        return "user";
    }

    @Override
    public String getMenuCategory() {
        return "Usuários";
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
            
            return UIUtils.createErrorContainer(
                "Não foi possível carregar a interface",
                "Ocorreu um erro ao carregar a interface do plugin de usuários.",
                e.getMessage()
            );
        }
    }
    
    private void setupComponents() {
        setupTable();

        setupSearchOptions();
        
        setupButtonActions();
        
        loadInitialData();
    }

    private void setupSearchOptions() {
        cmbSearchType.getItems().addAll("Nome", "Email");
        cmbSearchType.setValue("Nome");
    }
    
    private void setupTable() {
        TableColumn<User, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        
        TableColumn<User, String> nameColumn = new TableColumn<>("Nome");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(200);
        
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setPrefWidth(200);
        
        TableColumn<User, LocalDateTime> dateColumn = new TableColumn<>("Data de Registro");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("registeredAt"));
        dateColumn.setPrefWidth(325);
        
        tableUsers.getColumns().addAll(idColumn, nameColumn, emailColumn, dateColumn);
    }
    
    private void setupButtonActions() {
        btnSave.setOnAction(event -> {
            try {
                String name = txtName.getText();
                String email = txtEmail.getText();
                
                if (!ValidationService.isNotEmpty(name)) {
                    displayErrorMessage("O campo \"Nome\" é obrigatório");
                    return;
                }
                if (!ValidationService.isValidEmail(email)) {
                    displayErrorMessage("O campo \"Email\" é inválido");
                    return;
                }
                
                boolean isEditing = editingUserId != null;
                
                if (isEditing) {
                    handleUpdateUser(name, email);
                } else {
                    handleCreateUser(name, email);
                }
            } catch (Exception e) {
                displayErrorMessage("Erro: " + e.getMessage());
                e.printStackTrace();
            }
        });

        btnCancel.setOnAction(event -> {
            resetForm();
            lblMessage.setText("Edição cancelada");
        });
        
        btnSearch.setOnAction(event -> {
            handleSearch();
        });

        btnClear.setOnAction(event -> {
            txtSearch.clear();
            loadInitialData();
        });

        btnRefresh.setOnAction(event -> loadInitialData());
        
        if (btnDelete != null) {
            btnDelete.setOnAction(event -> handleDelete());
        }
        
        if (btnEdit != null) {
            btnEdit.setOnAction(event -> handleEdit());
        }
    }
    
    private void handleCreateUser(String name, String email) {
        User savedUser = userService.createUser(name, email);
        
        if (savedUser != null) {
            resetForm();
            loadInitialData();
        } else {
            displayErrorMessage("Erro ao cadastrar usuário");
        }
    }
    
    private void handleUpdateUser(String name, String email) {
        Optional<User> existingUser = userService.findUserById(editingUserId);
        
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(name);
            user.setEmail(email);
            
            User updatedUser = userService.updateUser(user);
            
            if (updatedUser != null) {
                resetForm();
                loadInitialData();
            } else {
                displayErrorMessage("Erro ao atualizar usuário");
            }
        } else {
            displayErrorMessage("Usuário não encontrado. ID: " + editingUserId);
            resetForm();
        }
    }
    
    private void handleSearch() {
        String query = txtSearch.getText();
        String searchType = cmbSearchType.getValue();
        List<User> users;
        
        if (query.isEmpty()) {
            users = userService.getAllUsers();
        } else if ("Email".equals(searchType)) {
            Optional<User> userOpt = userService.findUserByEmail(query);
            users = userOpt.isPresent() ? List.of(userOpt.get()) : List.of();
        } else {
            users = userService.findUsersByName(query);
        }
        
        tableUsers.getItems().clear();
        tableUsers.getItems().addAll(users);
    }
    
    private void handleDelete() {
        User selectedUser = tableUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            boolean deleted = userService.deleteUser(selectedUser.getUserId());
            if (deleted) {
                tableUsers.getItems().remove(selectedUser);
                resetForm();
            } else {
                lblMessage.setText("Erro ao remover usuário");
            }
        } else {
            lblMessage.setText("Selecione um usuário para remover");
        }
    }
    
    private void handleEdit() {
        User selectedUser = tableUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            txtName.setText(selectedUser.getName());
            txtEmail.setText(selectedUser.getEmail());
            
            editingUserId = selectedUser.getUserId();
            
            btnSave.setText("Atualizar");
            btnCancel.setVisible(true);
            lblMessage.setStyle("-fx-text-fill: #d1a000ff;");
            lblMessage.setText("Editando usuário #" + editingUserId);
        } else {
            lblMessage.setText("Selecione um usuário para editar");
        }
    }
    
    private void displayErrorMessage(String message) {
        lblMessage.setStyle("-fx-text-fill: #d31414;");
        lblMessage.setText(message);
    }
    
    private void resetForm() {
        txtName.clear();
        txtEmail.clear();
        editingUserId = null;
        btnSave.setText("Cadastrar");
        btnCancel.setVisible(false);
        lblMessage.setText("");
        lblMessage.setStyle("");
    }
    
    private void loadInitialData() {
        List<User> users = userService.getAllUsers();
        tableUsers.getItems().clear();
        tableUsers.getItems().addAll(users);
    }
}