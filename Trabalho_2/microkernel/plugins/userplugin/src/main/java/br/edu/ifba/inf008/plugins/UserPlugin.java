package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IPluginUI;
import br.edu.ifba.inf008.interfaces.ILibraryPlugin;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.models.User;

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
import java.util.regex.Pattern;

public class UserPlugin implements IPluginUI, ILibraryPlugin
{
    // FXML components
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
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)\\.(\\w+)$");
    
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
            // Tenta encontrar o arquivo FXML
            java.net.URL fxmlUrl = getClass().getResource("/fxml/UserView.fxml");
            
            // Se o arquivo FXML não for encontrado, lança uma exceção imediatamente
            if (fxmlUrl == null) {
                throw new IOException("Arquivo FXML não encontrado: /fxml/UserView.fxml");
            }
            
            // Se chegou aqui, o arquivo FXML foi encontrado
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setController(this);
            
            // Carrega a raiz do FXML
            Node root = loader.load();
            
            // Configura os componentes após o carregamento do FXML
            setupComponents();
            
            return root;
        } catch (Exception e) {
            e.printStackTrace();
            
            VBox errorContainer = new VBox(15);
            errorContainer.setPadding(new Insets(30));
            errorContainer.setAlignment(javafx.geometry.Pos.CENTER);
            
            Label errorTitle = new Label("Não foi possível carregar a interface");
            errorTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
            
            Label errorMessage = new Label("Ocorreu um erro ao carregar a interface do plugin de usuários.");
            Label errorDetail = new Label("Detalhes: " + e.getMessage());
            errorDetail.setStyle("-fx-font-style: italic;");
            
            Label contactAdmin = new Label("Entre em contato com o administrador do sistema.");
            
            errorContainer.getChildren().addAll(
                errorTitle,
                new Label(""),  
                errorMessage,
                errorDetail,
                new Label(""),  
                contactAdmin
            );
            
            return errorContainer;
        }
    }
    
    // Configuração dos componentes após o FXML ser carregado
    private void setupComponents() {
        // Configura a tabela
        setupTable();

        // Configura o combo de tipo de busca
        setupSearchOptions();
        
        // Configura os eventos dos botões
        setupButtonActions();
        
        // Carrega dados iniciais
        loadInitialData();
    }

    private void setupSearchOptions() {
        // Adiciona opções ao ComboBox
        cmbSearchType.getItems().addAll("Nome", "Email");
        cmbSearchType.setValue("Nome"); // Valor padrão
    }
    
    private void setupTable() {
        // Configuração das colunas da tabela
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
        // Ação do botão Salvar/Cadastrar
        btnSave.setOnAction(event -> {
            try {
                String name = txtName.getText();
                String email = txtEmail.getText();
                
                // Validações
                if (name == null || name.trim().isEmpty()) {
                    lblMessage.setStyle("-fx-text-fill: #d31414;");
                    lblMessage.setText("O campo \"Nome\" é obrigatório");
                    return;
                }
                if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
                    lblMessage.setStyle("-fx-text-fill: #d31414;");
                    lblMessage.setText("O campo \"Email\" é inválido");
                    return;
                }
                
                boolean isEditing = editingUserId != null;
                
                if (isEditing) {
                    // Modo de edição - atualiza o usuário existente
                    Optional<User> existingUser = ICore.getInstance().getUserDAO().findById(editingUserId);
                    
                    if (existingUser.isPresent()) {
                        User user = existingUser.get();
                        user.setName(name);
                        user.setEmail(email);
                        
                        User updatedUser = ICore.getInstance().getUserDAO().update(user);
                        
                        if (updatedUser != null) {
                            resetForm();
                            loadInitialData();
                        } else {
                            lblMessage.setStyle("-fx-text-fill: #d31414;");
                            lblMessage.setText("Erro ao atualizar usuário");
                        }
                    } else {
                        lblMessage.setStyle("-fx-text-fill: #d31414;");
                        lblMessage.setText("Usuário não encontrado. ID: " + editingUserId);
                        resetForm();
                    }
                } else {
                    // Modo de cadastro - cria um novo usuário
                    User newUser = new User(name, email);
                    User savedUser = ICore.getInstance().getUserDAO().save(newUser);
                    
                    if (savedUser != null) {
                        resetForm();
                        loadInitialData();
                    } else {
                        lblMessage.setStyle("-fx-text-fill: #d31414;");
                        lblMessage.setText("Erro ao cadastrar usuário");
                    }
                }
            } catch (Exception e) {
                lblMessage.setStyle("-fx-text-fill: #d31414;");
                lblMessage.setText("Erro: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // Ação do botão Cancelar
        btnCancel.setOnAction(event -> {
            resetForm();
            lblMessage.setText("Edição cancelada");
        });
        
        // Ação do botão Buscar
        btnSearch.setOnAction(event -> {
            String query = txtSearch.getText();
            String searchType = cmbSearchType.getValue();
            List<User> users;
            
            if (query.isEmpty()) {
                users = ICore.getInstance().getUserDAO().findAll();
            } else if ("Email".equals(searchType)) {
                // Busca por email
                Optional<User> userOpt = ICore.getInstance().getUserDAO().findByEmail(query);
                users = userOpt.isPresent() ? List.of(userOpt.get()) : List.of();
            } else {
                // Busca por nome
                users = ICore.getInstance().getUserDAO().findByName(query);
            }
            
            tableUsers.getItems().clear();
            tableUsers.getItems().addAll(users);
        });

        // Ação do botão Limpar
        btnClear.setOnAction(event -> {
            txtSearch.clear();
            loadInitialData();
        });

        // Ação do botão Atualizar
        btnRefresh.setOnAction(event -> loadInitialData());
        
        // Ação do botão Excluir (se existir)
        if (btnDelete != null) {
            btnDelete.setOnAction(event -> {
                User selectedUser = tableUsers.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    boolean deleted = ICore.getInstance().getUserDAO().delete(selectedUser.getUserId());
                    if (deleted) {
                        tableUsers.getItems().remove(selectedUser);
                        resetForm();
                    } else {
                        lblMessage.setText("Erro ao remover usuário");
                    }
                } else {
                    lblMessage.setText("Selecione um usuário para remover");
                }
            });
        }
        
        // Ação do botão Editar (se existir)
        if (btnEdit != null) {
            btnEdit.setOnAction(event -> {
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
            });
        }
    }
    
    // Método para resetar o formulário e voltar ao modo de cadastro
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
        List<User> users = ICore.getInstance().getUserDAO().findAll();
        tableUsers.getItems().clear();
        tableUsers.getItems().addAll(users);
    }
    
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}