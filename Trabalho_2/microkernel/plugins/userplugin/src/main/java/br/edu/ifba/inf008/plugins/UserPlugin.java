package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IPluginUI;
import br.edu.ifba.inf008.interfaces.ILibraryPlugin;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.models.User;

import javafx.scene.Node;
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

public class UserPlugin implements IPluginUI, ILibraryPlugin
{
    // FXML components
    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private Button btnSave;
    @FXML private Label lblMessage;
    @FXML private TextField txtSearch;
    @FXML private TableView<User> tableUsers;
    @FXML private Button btnSearch;
    @FXML private Button btnRefresh;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    
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
            // Alterando de IOException para Exception para capturar todos os tipos de erro
            e.printStackTrace();
            
            // Cria uma tela simples informando o erro
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
        
        // Configura os eventos dos botões
        setupButtonActions();
        
        // Carrega dados iniciais
        loadInitialData();
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
        dateColumn.setPrefWidth(500);
        
        tableUsers.getColumns().addAll(idColumn, nameColumn, emailColumn, dateColumn);
    }
    
    private void setupButtonActions() {
        // FIXME: ajustar para edição
        // Ação do botão Salvar
        btnSave.setOnAction(event -> {
            try {
                String name = txtName.getText();
                String email = txtEmail.getText();
                
                // Validações
                if (name == null || name.trim().isEmpty()) {
                    lblMessage.setText("Nome é obrigatório");
                    return;
                }
                
                if (email == null || email.trim().isEmpty() || !email.contains("@")) {
                    lblMessage.setText("Email inválido");
                    return;
                }
                
                User user = new User(name, email);
                User savedUser = ICore.getInstance().getUserDAO().save(user);
                
                if (savedUser != null) {
                    lblMessage.setText("Usuário cadastrado com sucesso!");
                    txtName.clear();
                    txtEmail.clear();
                    loadInitialData();
                } else {
                    lblMessage.setText("Erro ao cadastrar usuário");
                }
            } catch (Exception e) {
                lblMessage.setText("Erro: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
        // Ação do botão Buscar
        btnSearch.setOnAction(event -> {
            String query = txtSearch.getText();
            List<User> users;
            
            if (query.isEmpty()) {
                users = ICore.getInstance().getUserDAO().findAll();
            } else {
                users = ICore.getInstance().getUserDAO().findByName(query);
            }
            
            tableUsers.getItems().clear();
            tableUsers.getItems().addAll(users);
        });

        // TODO: adicionar botão limpar ao lado da busca, que limpa e chama loadInitialData()
        
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
                        lblMessage.setText("Usuário removido com sucesso!");
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
                    // TODO: mudar o botão Salvar para um modo de edição
                } else {
                    lblMessage.setText("Selecione um usuário para editar");
                }
            });
        }
    }
    
    private void loadInitialData() {
        List<User> users = ICore.getInstance().getUserDAO().findAll();
        tableUsers.getItems().clear();
        tableUsers.getItems().addAll(users);
    }
}