package br.edu.ifba.inf008.plugins.controller;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifba.inf008.interfaces.models.Book;
import br.edu.ifba.inf008.interfaces.models.Loan;
import br.edu.ifba.inf008.interfaces.models.User;
import br.edu.ifba.inf008.plugins.service.LoanBookService;
import br.edu.ifba.inf008.plugins.service.LoanService;
import br.edu.ifba.inf008.plugins.service.LoanUserService;
import br.edu.ifba.inf008.plugins.ui.UserUIUtils;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LoanController {

    private final LoanService loanService;
    private final LoanUserService userService;
    private final LoanBookService bookService;
    
    private ComboBox<User> cmbUser;
    private ComboBox<Book> cmbBook;
    private DatePicker dtpLoanDate;
    private DatePicker dtpReturnDate;
    private Label lblReturnDate;
    private Button btnSave;
    private Button btnCancel;
    private Label lblMessage;
    private TextField txtSearch;
    private TableView<Loan> tableLoans;
    private ComboBox<String> cmbSearchType;
    
    private Loan currentLoan;
    
    public LoanController(LoanService loanService, LoanUserService userService, LoanBookService bookService) {
        this.loanService = loanService;
        this.userService = userService;
        this.bookService = bookService;
    }
    
    public void initialize(
            ComboBox<User> cmbUser, 
            ComboBox<Book> cmbBook, 
            DatePicker dtpLoanDate,
            DatePicker dtpReturnDate,
            Label lblReturnDate,
            Button btnSave, 
            Button btnCancel, 
            Label lblMessage,
            TextField txtSearch, 
            TableView<Loan> tableLoans, 
            ComboBox<String> cmbSearchType) {
        
        this.cmbUser = cmbUser;
        this.cmbBook = cmbBook;
        this.dtpLoanDate = dtpLoanDate;
        this.dtpReturnDate = dtpReturnDate;
        this.lblReturnDate = lblReturnDate;
        this.btnSave = btnSave;
        this.btnCancel = btnCancel;
        this.lblMessage = lblMessage;
        this.txtSearch = txtSearch;
        this.tableLoans = tableLoans;
        this.cmbSearchType = cmbSearchType;
        
        // Carregar dados iniciais
        loadLoans();
        loadUsers();
        loadBooks();
    }
    
    private void loadLoans() {
        try {
            List<Loan> loans = loanService.getAllLoans();
            tableLoans.setItems(FXCollections.observableArrayList(loans));
            UserUIUtils.clearMessage(lblMessage);
        } catch (Exception e) {
            UserUIUtils.showMessage(lblMessage, "Erro ao carregar empréstimos: " + e.getMessage(), true);
        }
    }
    
    public void handleSave() {
        try {
            User selectedUser = cmbUser.getValue();
            Book selectedBook = cmbBook.getValue();
            LocalDate loanDate = dtpLoanDate.getValue();
            
            if (selectedUser == null) {
                UserUIUtils.showMessage(lblMessage, "Selecione um usuário", true);
                return;
            }
            
            if (selectedBook == null) {
                UserUIUtils.showMessage(lblMessage, "Selecione um livro", true);
                return;
            }
            
            if (loanDate == null) {
                UserUIUtils.showMessage(lblMessage, "Selecione a data de empréstimo", true);
                return;
            }

            if (loanDate.isAfter(LocalDate.now())) {
                UserUIUtils.showMessage(lblMessage, "A data de empréstimo não pode ser futura", true);
                return;
            }
            
            if (currentLoan == null) {
                // Verificar se o livro tem cópias disponíveis
                if (selectedBook.getCopiesAvailable() <= 0) {
                    UserUIUtils.showMessage(lblMessage, "Não há cópias disponíveis do livro '" + selectedBook.getTitle() + "'", true);
                    return;
                }
                
                // Criando um novo empréstimo
                loanService.createLoan(selectedUser, selectedBook, loanDate);
                UserUIUtils.showMessage(lblMessage, "Empréstimo cadastrado com sucesso!", false);
                clearForm();
            } else {
                // Atualizando um empréstimo existente
                LocalDate returnDate = dtpReturnDate.getValue();
                
                currentLoan.setUser(selectedUser);
                currentLoan.setBook(selectedBook);
                currentLoan.setLoanDate(loanDate);
                if (returnDate != null && !returnDate.isBefore(loanDate)) {
                    currentLoan.setReturnDate(returnDate);
                } else {
                    currentLoan.setReturnDate(null);
                    UserUIUtils.showMessage(lblMessage, "Data de devolução inválida. Deve ser posterior à data de empréstimo.", true);
                    return;
                }

                loanService.updateLoan(currentLoan);
                UserUIUtils.showMessage(lblMessage, "Empréstimo atualizado com sucesso!", false);
                clearForm();
            }
            
            loadLoans();
            loadBooks();
            loadUsers();
        } catch (Exception e) {
            UserUIUtils.showMessage(lblMessage, "Erro ao salvar empréstimo: " + e.getMessage(), true);
        }
    }
    
    public void handleCancel() {
        clearForm();
        UserUIUtils.clearMessage(lblMessage);
    }
    
    public void handleSearch() {
        try {
            String searchText = txtSearch.getText().trim();
            String searchType = cmbSearchType.getValue();
            
            if (searchText.isEmpty()) {
                loadLoans();
                loadBooks();
                loadUsers();
                return;
            }
            
            List<Loan> results;
            
            switch (searchType) {
                case "Usuário":
                    results = loanService.findLoansByUserName(searchText);
                    break;
                case "Livro":
                    results = loanService.findLoansByBookTitle(searchText);
                    break;
                case "Data":
                    try {
                        LocalDate date = LocalDate.parse(searchText);
                        results = loanService.findLoansByDate(date);
                    } catch (Exception e) {
                        UserUIUtils.showMessage(lblMessage, "Formato de data inválido. Use o formato YYYY-MM-DD", true);
                        return;
                    }
                    break;
                default:
                    results = loanService.getAllLoans();
                    break;
            }
            
            tableLoans.setItems(FXCollections.observableArrayList(results));
            UserUIUtils.showMessage(lblMessage, results.size() + " empréstimo(s) encontrado(s)", false);
        } catch (Exception e) {
            UserUIUtils.showMessage(lblMessage, "Erro ao buscar empréstimos: " + e.getMessage(), true);
        }
    }
    
    public void handleClear() {
        txtSearch.clear();
        loadLoans();
        loadBooks();
        loadUsers();
        UserUIUtils.clearMessage(lblMessage);
    }
    
    public void handleRefresh() {
        loadLoans();
        loadBooks();
        loadUsers();
        UserUIUtils.clearMessage(lblMessage);
    }
    
    public void handleEdit() {
        Loan selectedLoan = tableLoans.getSelectionModel().getSelectedItem();
        
        if (selectedLoan == null) {
            UserUIUtils.showMessage(lblMessage, "Selecione um empréstimo para editar", true);
            return;
        }
        
        currentLoan = selectedLoan;
        
        // Preenche o formulário com os dados do empréstimo selecionado
        cmbUser.setValue(selectedLoan.getUser());
        cmbBook.setValue(selectedLoan.getBook());
        dtpLoanDate.setValue(selectedLoan.getLoanDate());
        dtpReturnDate.setValue(selectedLoan.getReturnDate());
        
        // Torna visível o campo de data de devolução e seu rótulo
        dtpReturnDate.setVisible(true);
        lblReturnDate.setVisible(true);
        
        // Altera o texto do botão para indicar que estamos editando
        btnSave.setText("Atualizar");
        btnCancel.setVisible(true);
        
        UserUIUtils.clearMessage(lblMessage);
    }
    
    public void handleDelete() {
        Loan selectedLoan = tableLoans.getSelectionModel().getSelectedItem();
        
        if (selectedLoan == null) {
            UserUIUtils.showMessage(lblMessage, "Selecione um empréstimo para excluir", true);
            return;
        }
        
        if (selectedLoan.getReturnDate() == null) {
            UserUIUtils.showMessage(lblMessage, "ATENÇÃO: Não é permitido excluir empréstimos ativos. Efetue a devolução antes de excluir.", true);
            return;
        }
        
        try {
            boolean deleted = loanService.deleteLoan(selectedLoan.getLoanId());
            
            if (deleted) {
                UserUIUtils.showMessage(lblMessage, "Empréstimo excluído com sucesso!", false);
                loadLoans();
                loadBooks();
                loadUsers();
            } else {
                UserUIUtils.showMessage(lblMessage, "Não foi possível excluir o empréstimo", true);
            }
        } catch (Exception e) {
            UserUIUtils.showMessage(lblMessage, "Erro ao excluir empréstimo: " + e.getMessage(), true);
        }
    }
    
    public void loadUsers() {
        try {
            List<User> users = userService.getAllUsers();
            cmbUser.setItems(FXCollections.observableArrayList(users));
        } catch (Exception e) {
            UserUIUtils.showMessage(lblMessage, "Erro ao carregar usuários: " + e.getMessage(), true);
        }
    }
    
    public void loadBooks() {
        try {
            // Carregar apenas livros com cópias disponíveis
            List<Book> availableBooks = bookService.getAvailableBooks();
            if (availableBooks.isEmpty()) {
                UserUIUtils.showMessage(lblMessage, "Não há livros disponíveis para empréstimo", true);
            }
            cmbBook.setItems(FXCollections.observableArrayList(availableBooks));
        } catch (Exception e) {
            UserUIUtils.showMessage(lblMessage, "Erro ao carregar livros: " + e.getMessage(), true);
        }
    }
    
    private void clearForm() {
        cmbUser.setValue(null);
        cmbBook.setValue(null);
        dtpLoanDate.setValue(LocalDate.now());
        dtpReturnDate.setValue(null);
        
        btnSave.setText("Cadastrar");
        btnCancel.setVisible(false);
        dtpReturnDate.setVisible(false);
        lblReturnDate.setVisible(false);
        
        currentLoan = null;
    }
    
    public void handleReturn() {
        Loan selectedLoan = tableLoans.getSelectionModel().getSelectedItem();
        
        if (selectedLoan == null) {
            UserUIUtils.showMessage(lblMessage, "Selecione um empréstimo para efetuar a devolução", true);
            return;
        }
        
        if (selectedLoan.getReturnDate() != null) {
            UserUIUtils.showMessage(lblMessage, "Este empréstimo já foi devolvido em " + selectedLoan.getReturnDate(), true);
            return;
        }
        
        try {
            // Set the return date to today
            selectedLoan.setReturnDate(LocalDate.now());
            
            // Update the loan
            loanService.updateLoan(selectedLoan);
            
            UserUIUtils.showMessage(lblMessage, "Devolução registrada com sucesso!", false);
            
            // Reload the loans list AND available books list
            loadLoans();
            loadBooks();
            loadUsers();
        } catch (Exception e) {
            UserUIUtils.showMessage(lblMessage, "Erro ao registrar devolução: " + e.getMessage(), true);
        }
    }
}
