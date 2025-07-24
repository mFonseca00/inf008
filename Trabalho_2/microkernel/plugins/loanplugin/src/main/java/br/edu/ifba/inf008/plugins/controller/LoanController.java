package br.edu.ifba.inf008.plugins.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifba.inf008.interfaces.models.Book;
import br.edu.ifba.inf008.interfaces.models.Loan;
import br.edu.ifba.inf008.interfaces.models.User;
import br.edu.ifba.inf008.plugins.service.LoanBookService;
import br.edu.ifba.inf008.plugins.service.LoanService;
import br.edu.ifba.inf008.plugins.service.LoanUserService;
import br.edu.ifba.inf008.plugins.ui.LoanUIUtils;
import br.edu.ifba.inf008.plugins.util.DateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private TextField txtUserFilter;
    private TextField txtBookFilter;
    private DatePicker dtpLoanDate;
    private DatePicker dtpReturnDate;
    private Label lblReturnDate;
    private Button btnSave;
    private Button btnCancel;
    private Label lblMessage;
    private TextField txtSearch;
    private TableView<Loan> tableLoans;
    private ComboBox<String> cmbSearchType;
    
    private ObservableList<User> allUsers = FXCollections.observableArrayList();
    private ObservableList<Book> allBooks = FXCollections.observableArrayList();
    
    private Loan currentLoan;
    
    public LoanController(LoanService loanService, LoanUserService userService, LoanBookService bookService) {
        this.loanService = loanService;
        this.userService = userService;
        this.bookService = bookService;
    }
    
    public void initialize(
            ComboBox<User> cmbUser, 
            ComboBox<Book> cmbBook, 
            TextField txtUserFilter,
            TextField txtBookFilter,
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
        this.txtUserFilter = txtUserFilter;
        this.txtBookFilter = txtBookFilter;
        this.dtpLoanDate = dtpLoanDate;
        this.dtpReturnDate = dtpReturnDate;
        this.lblReturnDate = lblReturnDate;
        this.btnSave = btnSave;
        this.btnCancel = btnCancel;
        this.lblMessage = lblMessage;
        this.txtSearch = txtSearch;
        this.tableLoans = tableLoans;
        this.cmbSearchType = cmbSearchType;
        
        setupFilters();
        
        loadLoans();
        loadUsers();
        loadBooks();
    }
    
    private void setupFilters() {
        txtUserFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filterUsers(newValue);
        });
        
        txtBookFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filterBooks(newValue);
        });
    }
    
    public void clearFilters() {
        if (txtUserFilter != null) {
            txtUserFilter.clear();
        }
        if (txtBookFilter != null) {
            txtBookFilter.clear();
        }
        if (cmbUser != null) {
            cmbUser.setItems(allUsers);
        }
        if (cmbBook != null) {
            cmbBook.setItems(allBooks);
        }
    }
    
    private void filterUsers(String filter) {
        if (filter == null || filter.trim().isEmpty()) {
            cmbUser.setItems(allUsers);
            return;
        }
        
        String filterLower = filter.toLowerCase();
        ObservableList<User> filteredUsers = allUsers.stream()
                .filter(user -> user.getName().toLowerCase().startsWith(filterLower) ||
                               user.getEmail().toLowerCase().startsWith(filterLower))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        
        cmbUser.setItems(filteredUsers);
    }
    
    private void filterBooks(String filter) {
        if (filter == null || filter.trim().isEmpty()) {
            cmbBook.setItems(allBooks);
            return;
        }
        
        String filterLower = filter.toLowerCase();
        ObservableList<Book> filteredBooks = allBooks.stream()
                .filter(book -> book.getTitle().toLowerCase().startsWith(filterLower) ||
                               book.getAuthor().toLowerCase().startsWith(filterLower) ||
                               book.getIsbn().toLowerCase().startsWith(filterLower))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        
        cmbBook.setItems(filteredBooks);
    }
    
    private void loadLoans() {
        try {
            List<Loan> loans = loanService.getAllLoans();
            tableLoans.setItems(FXCollections.observableArrayList(loans));
            LoanUIUtils.clearMessage(lblMessage);
        } catch (Exception e) {
            LoanUIUtils.showMessage(lblMessage, "Erro ao carregar empréstimos: " + e.getMessage(), true);
        }
    }
    
    public void handleSave() {
        try {
            User selectedUser = cmbUser.getValue();
            Book selectedBook = cmbBook.getValue();
            LocalDate loanDate = dtpLoanDate.getValue();
            
            if (selectedUser == null) {
                LoanUIUtils.displayErrorMessage(lblMessage, "Selecione um usuário");
                return;
            }
            
            if (selectedBook == null) {
                LoanUIUtils.displayErrorMessage(lblMessage, "Selecione um livro");
                return;
            }
            
            if (loanDate == null) {
                LoanUIUtils.displayErrorMessage(lblMessage, "Selecione a data de empréstimo");
                return;
            }

            if (loanDate.isAfter(LocalDate.now())) {
                LoanUIUtils.displayErrorMessage(lblMessage, "A data de empréstimo não pode ser futura");
                return;
            }
            
            if (currentLoan == null) {
                if (selectedBook.getCopiesAvailable() <= 0) {
                    LoanUIUtils.displayErrorMessage(lblMessage, "Não há cópias disponíveis do livro '" + selectedBook.getTitle() + "'");
                    return;
                }
                
                loanService.createLoan(selectedUser, selectedBook, loanDate);
                clearForm();
                LoanUIUtils.displaySuccessMessage(lblMessage, "Empréstimo cadastrado com sucesso!");
            } else {
                LocalDate returnDate = dtpReturnDate.getValue();
                
                currentLoan.setUser(selectedUser);
                currentLoan.setBook(selectedBook);
                currentLoan.setLoanDate(loanDate);
                if (returnDate == null) {
                    currentLoan.setReturnDate(null);
                } else if (!returnDate.isBefore(loanDate)) {
                    currentLoan.setReturnDate(returnDate);
                } else{
                    currentLoan.setReturnDate(null);
                    LoanUIUtils.displayErrorMessage(lblMessage, "Data de devolução inválida. Deve ser posterior à data de empréstimo.");
                    return;
                }

                loanService.updateLoan(currentLoan);
                clearForm();
                LoanUIUtils.displaySuccessMessage(lblMessage, "Empréstimo atualizado com sucesso!");
            }
            
            loadLoans();
            loadBooks();
            loadUsers();
        } catch (Exception e) {
            LoanUIUtils.displayErrorMessage(lblMessage, "Erro ao salvar empréstimo: " + e.getMessage());
        }
    }
    
    public void handleCancel() {
        clearForm();
        LoanUIUtils.displayConfirmationMessage(lblMessage, "Edição cancelada");
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
                case "Data de empréstimo":
                    try {
                        LocalDate date = DateUtils.parseFlexibleDate(searchText);
                        results = loanService.findLoansByLoanDate(date);
                    } catch (Exception e) {
                        LoanUIUtils.showMessage(lblMessage, "Formato de data inválido. Use DD/MM/AAAA (ex: 12/09/2025) ou AAAA-MM-DD", true);
                        return;
                    }
                    break;
                case "Data de devolução":
                    try {
                        LocalDate date = DateUtils.parseFlexibleDate(searchText);
                        results = loanService.findLoansByReturnDate(date);
                    } catch (Exception e) {
                        LoanUIUtils.showMessage(lblMessage, "Formato de data inválido. Use DD/MM/AAAA (ex: 12/09/2025) ou AAAA-MM-DD", true);
                        return;
                    }
                    break;
                default:
                    results = loanService.getAllLoans();
                    break;
            }
            
            tableLoans.setItems(FXCollections.observableArrayList(results));
            LoanUIUtils.showMessage(lblMessage, results.size() + " empréstimo(s) encontrado(s)", false);
        } catch (Exception e) {
            LoanUIUtils.showMessage(lblMessage, "Erro ao buscar empréstimos: " + e.getMessage(), true);
        }
    }
    
    public void handleClear() {
        txtSearch.clear();
        loadLoans();
        loadBooks();
        loadUsers();
        LoanUIUtils.clearMessage(lblMessage);
    }
    
    public void handleRefresh() {
        loadLoans();
        loadBooks();
        loadUsers();
        LoanUIUtils.clearMessage(lblMessage);
    }
    
    public void handleEdit() {
        Loan selectedLoan = tableLoans.getSelectionModel().getSelectedItem();
        
        if (selectedLoan == null) {
            LoanUIUtils.displayErrorMessage(lblMessage, "Selecione um empréstimo para editar");
            return;
        }
        
        currentLoan = selectedLoan;
        
        cmbUser.setValue(selectedLoan.getUser());
        cmbBook.setValue(selectedLoan.getBook());
        dtpLoanDate.setValue(selectedLoan.getLoanDate());
        dtpReturnDate.setValue(selectedLoan.getReturnDate());
        
        dtpReturnDate.setVisible(true);
        lblReturnDate.setVisible(true);
        
        btnSave.setText("Atualizar");
        btnCancel.setVisible(true);
        
        LoanUIUtils.displayConfirmationMessage(lblMessage, "Editando empréstimo do livro \"" + 
            selectedLoan.getBook().getTitle() + "\" para \"" + selectedLoan.getUser().getName() + "\"");
    }
    
    public void handleDelete() {
        Loan selectedLoan = tableLoans.getSelectionModel().getSelectedItem();
        
        if (selectedLoan == null) {
            LoanUIUtils.displayErrorMessage(lblMessage, "Selecione um empréstimo para excluir");
            return;
        }
        
        if (selectedLoan.getReturnDate() == null) {
            LoanUIUtils.displayErrorMessage(lblMessage, "ATENÇÃO: Não é permitido excluir empréstimos ativos. Efetue a devolução antes de excluir.");
            return;
        }
        
        String confirmationMessage = "Deseja continuar com a exclusão do empréstimo do livro \"" 
            + selectedLoan.getBook().getTitle() + "\" para o usuário \"" + selectedLoan.getUser().getName() + "\"?";
        
        boolean confirmed = LoanUIUtils.showConfirmation(confirmationMessage);
        
        if (!confirmed) {
            LoanUIUtils.displayConfirmationMessage(lblMessage, "Exclusão cancelada pelo usuário");
            return;
        }
        
        try {
            boolean deleted = loanService.deleteLoan(selectedLoan.getLoanId());
            
            if (deleted) {
                tableLoans.getItems().remove(selectedLoan);
                LoanUIUtils.displaySuccessMessage(lblMessage, "Empréstimo excluído com sucesso!");
                loadLoans();
                loadBooks();
                loadUsers();
            } else {
                LoanUIUtils.displayErrorMessage(lblMessage, "Erro ao excluir o empréstimo");
            }
        } catch (Exception e) {
            LoanUIUtils.displayErrorMessage(lblMessage, "Erro ao excluir empréstimo: " + e.getMessage());
        }
    }
    
    public void loadUsers() {
        try {
            List<User> users = userService.getAllUsers();
            allUsers.setAll(users);
            cmbUser.setItems(allUsers);
        } catch (Exception e) {
            LoanUIUtils.displayErrorMessage(lblMessage, "Erro ao carregar usuários: " + e.getMessage());
        }
    }
    
    public void loadBooks() {
        try {
            List<Book> availableBooks = bookService.getAvailableBooks();
            if (availableBooks.isEmpty()) {
                LoanUIUtils.displayErrorMessage(lblMessage, "Não há livros disponíveis para empréstimo");
            }
            allBooks.setAll(availableBooks);
            cmbBook.setItems(allBooks);
        } catch (Exception e) {
            LoanUIUtils.displayErrorMessage(lblMessage, "Erro ao carregar livros: " + e.getMessage());
        }
    }
    
    private void clearForm() {
        cmbUser.setValue(null);
        cmbBook.setValue(null);
        dtpLoanDate.setValue(LocalDate.now());
        dtpReturnDate.setValue(null);
        dtpReturnDate.setVisible(false);
        lblReturnDate.setVisible(false);
        btnSave.setText("Cadastrar");
        btnCancel.setVisible(false);
        currentLoan = null;
        LoanUIUtils.clearMessage(lblMessage);
    }
    
    public void handleReturn() {
        Loan selectedLoan = tableLoans.getSelectionModel().getSelectedItem();
        
        if (selectedLoan == null) {
            LoanUIUtils.displayErrorMessage(lblMessage, "Selecione um empréstimo para efetuar a devolução");
            return;
        }
        
        if (selectedLoan.getReturnDate() != null) {
            LoanUIUtils.displayErrorMessage(lblMessage, "Este empréstimo já foi devolvido em " + selectedLoan.getReturnDate());
            return;
        }
        
        try {
            selectedLoan.setReturnDate(LocalDate.now());
            
            loanService.updateLoan(selectedLoan);
            
            LoanUIUtils.displaySuccessMessage(lblMessage, "Devolução registrada com sucesso!");
            
            loadLoans();
            loadBooks();
            loadUsers();
        } catch (Exception e) {
            LoanUIUtils.displayErrorMessage(lblMessage, "Erro ao registrar devolução: " + e.getMessage());
        }
    }
}
