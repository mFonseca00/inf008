package br.edu.ifba.inf008.plugins.controller;

import br.edu.ifba.inf008.interfaces.models.User;
import br.edu.ifba.inf008.plugins.service.UserService;
import br.edu.ifba.inf008.plugins.util.ValidationService;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Optional;

public class UserController {
    
    private final UserService userService;
    private Integer editingUserId = null;
    
    // UI components references
    private TextField txtName;
    private TextField txtEmail;
    private Button btnSave;
    private Button btnCancel;
    private Label lblMessage;
    private TextField txtSearch;
    private TableView<User> tableUsers;
    private ComboBox<String> cmbSearchType;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    public void initialize(
            TextField txtName, TextField txtEmail, 
            Button btnSave, Button btnCancel, Label lblMessage,
            TextField txtSearch, TableView<User> tableUsers, 
            ComboBox<String> cmbSearchType) {
        
        this.txtName = txtName;
        this.txtEmail = txtEmail;
        this.btnSave = btnSave;
        this.btnCancel = btnCancel;
        this.lblMessage = lblMessage;
        this.txtSearch = txtSearch;
        this.tableUsers = tableUsers;
        this.cmbSearchType = cmbSearchType;
        
        loadInitialData();
    }
    
    public void handleSave() {
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
    }
    
    public void handleSearch() {
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
    
    public void handleClear() {
        txtSearch.clear();
        loadInitialData();
    }
    
    public void handleRefresh() {
        loadInitialData();
    }
    
    public void handleCancel() {
        resetForm();
        lblMessage.setText("Edição cancelada");
    }
    
    public void handleEdit() {
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
    
    public void handleDelete() {
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
