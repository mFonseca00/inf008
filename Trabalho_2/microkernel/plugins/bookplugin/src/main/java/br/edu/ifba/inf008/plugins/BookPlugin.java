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

public class BookPlugin implements IPluginUI, ILibraryPlugin
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
        System.out.println("BookPlugin inicializado!");
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
            // Carrega o FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookView.fxml"));
            loader.setController(this); // Define este objeto como controlador
            
            // Carrega a raiz do FXML
            Node root = loader.load();
            
            // Configura os componentes após o carregamento do FXML
            setupComponents();
            
            return root;
        } catch (IOException e) {
            e.printStackTrace();
            // Em caso de erro, retorna uma interface básica
            return createFallbackContent();
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
        
        TableColumn<User, LocalDateTime> dateColumn = new TableColumn<>("Data Registro");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("registeredAt"));
        
        tableUsers.getColumns().addAll(idColumn, nameColumn, emailColumn, dateColumn);
    }
    
    private void setupButtonActions() {
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
                    loadInitialData(); // Recarrega a tabela
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
                    // Aqui você poderia mudar o botão Salvar para um modo de edição
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
    
    // Interface de contingência caso o FXML não seja encontrado
    private Node createFallbackContent() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        
        // Título
        Label title = new Label("Gerenciamento de Usuários");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Formulário de cadastro básico
        VBox registerForm = new VBox(10);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        txtName = new TextField();
        txtEmail = new TextField();
        grid.add(new Label("Nome:"), 0, 0);
        grid.add(txtName, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(txtEmail, 1, 1);
        
        btnSave = new Button("Cadastrar");
        lblMessage = new Label();
        
        // Busca básica
        HBox searchBox = new HBox(10);
        txtSearch = new TextField();
        btnSearch = new Button("Buscar");
        searchBox.getChildren().addAll(txtSearch, btnSearch);
        
        // Tabela básica
        tableUsers = new TableView<>();
        setupTable();
        
        // Botão de atualizar
        btnRefresh = new Button("Atualizar");
        
        // Montar interface
        registerForm.getChildren().addAll(new Label("Cadastro de Usuários"), grid, btnSave, lblMessage);
        
        container.getChildren().addAll(
            title, 
            registerForm,
            new Label("Buscar Usuários"),
            searchBox,
            tableUsers,
            btnRefresh
        );
        
        // Configurar ações
        setupButtonActions();
        loadInitialData();
        
        return container;
    }
}