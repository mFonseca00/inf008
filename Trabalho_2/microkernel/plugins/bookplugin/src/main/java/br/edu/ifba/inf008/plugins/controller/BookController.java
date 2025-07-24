package br.edu.ifba.inf008.plugins.controller;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.inf008.interfaces.models.Book;
import br.edu.ifba.inf008.plugins.service.BookService;
import br.edu.ifba.inf008.plugins.service.BookValidationService;
import br.edu.ifba.inf008.plugins.ui.components.BookMessageUtils;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BookController {

    private final BookService bookService;
    private Integer editingBookId = null;

    private TextField txtTitle;
    private TextField txtAuthor;
    private TextField txtIsbn;
    private TextField txtPublicationYear;
    private TextField txtAvailableCopies;
    private Button btnSave;
    private Button btnCancel;
    private Label lblMessage;
    private TextField txtSearch;
    private TableView<Book> tableBooks;
    private ComboBox<String> cmbSearchType;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void initialize(
            TextField txtTitle, TextField txtAuthor, 
            TextField txtIsbn, TextField txtPublicationYear, 
            TextField txtAvailableCopies, Button btnSave, 
            Button btnCancel, Label lblMessage,
            TextField txtSearch, TableView<Book> tableBooks, 
            ComboBox<String> cmbSearchType) {

        this.txtTitle = txtTitle;
        this.txtAuthor = txtAuthor;
        this.txtIsbn = txtIsbn;
        this.txtPublicationYear = txtPublicationYear;
        this.txtAvailableCopies = txtAvailableCopies;
        this.btnSave = btnSave;
        this.btnCancel = btnCancel;
        this.lblMessage = lblMessage;
        this.txtSearch = txtSearch;
        this.tableBooks = tableBooks;
        this.cmbSearchType = cmbSearchType;

        loadInitialData();
    }

    public void handleSave() {
        try {
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            String isbn = txtIsbn.getText();
            
            int publicationYear;
            int availableCopies;
            
            try {
                publicationYear = Integer.parseInt(txtPublicationYear.getText());
            } catch (NumberFormatException e) {
                displayErrorMessage("O campo \"Ano de Publicação\" deve ser um número válido");
                return;
            }
            
            try {
                availableCopies = Integer.parseInt(txtAvailableCopies.getText());
            } catch (NumberFormatException e) {
                displayErrorMessage("O campo \"Cópias Disponíveis\" deve ser um número válido");
                return;
            }

            if (!BookValidationService.isNotEmpty(title)) {
                displayErrorMessage("O campo \"Título\" é obrigatório");
                return;
            }
            if (!BookValidationService.isNotEmpty(author)) {
                displayErrorMessage("O campo \"Autor\" é obrigatório");
                return;
            }
            if (!BookValidationService.isNotEmpty(isbn)) {
                displayErrorMessage("O campo \"ISBN\" é obrigatório");
                return;
            }
            if (!BookValidationService.isValidPublicationYear(publicationYear)) {
                displayErrorMessage("O campo \"Ano de Publicação\" é inválido");
                return;
            }
            if (!BookValidationService.isValidCopiesNumber(availableCopies)) {
                displayErrorMessage("O campo \"Cópias Disponíveis\" deve ser um número positivo");
                return;
            }

            boolean isEditing = editingBookId != null;
            
            if (isEditing) {
                handleUpdateBook(title, author, isbn, publicationYear, availableCopies);
            } else {
                handleCreateBook(title, author, isbn, publicationYear, availableCopies);
            }
        } catch (Exception e) {
            displayErrorMessage("Erro ao salvar o livro: " + e.getMessage());
        }
    }

    public void handleSearch() {
        String query = txtSearch.getText();
        String searchType = cmbSearchType.getValue();
        List<Book> results;

        switch (searchType) {
            case "Título":
                results = bookService.findBooksByTitle(query);
                break;
            case "Autor":
                results = bookService.findBooksByAuthor(query);
                break;
            case "ISBN":
                results = bookService.findBookByIsbn(query);
                break;
            case "Ano de Publicação":
                try {
                    int year = Integer.parseInt(query);
                    results = bookService.findBooksByPublishedYear(year);
                    if (results.isEmpty()) {
                        displayErrorMessage("Nenhum livro encontrado para o ano: " + year);
                    }
                } catch (NumberFormatException e) {
                    results = new ArrayList<>();
                    displayErrorMessage("O ano de publicação deve ser um número válido");
                }
                break;
            default:
                results = new ArrayList<>();
        }

        tableBooks.getItems().clear();
        tableBooks.getItems().addAll(results);
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
        Book selectedBook = tableBooks.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            txtTitle.setText(selectedBook.getTitle());
            txtAuthor.setText(selectedBook.getAuthor());
            txtIsbn.setText(selectedBook.getIsbn());
            txtPublicationYear.setText(String.valueOf(selectedBook.getPublishedYear()));
            txtAvailableCopies.setText(String.valueOf(selectedBook.getCopiesAvailable()));
            editingBookId = selectedBook.getBookId();
            btnSave.setText("Atualizar");
            btnCancel.setVisible(true);
            displayConfirmationMessage("Editando livro: " + selectedBook.getTitle());
        } else {
            displayErrorMessage("Selecione um livro para editar");
        }
    }

    public void handleDelete() {
        Book selectedBook = tableBooks.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            String warningMessage = bookService.getActiveLoansWarning(selectedBook.getBookId());
            if (warningMessage != null) {
                boolean confirmed = BookMessageUtils.showConfirmation(
                    warningMessage + "\n\nDeseja continuar com a exclusão do livro?"
                );
                
                if (!confirmed) {
                    displayConfirmationMessage("Exclusão cancelada pelo usuário");
                    return;
                }
            }
            else {
                String confirmationMessage = "Deseja continuar com a exclusão do livro \"" 
                    + selectedBook.getTitle() + "\" de " + selectedBook.getAuthor() + "?";
                
                boolean confirmed = BookMessageUtils.showConfirmation(confirmationMessage);
                
                if (!confirmed) {
                    displayConfirmationMessage("Exclusão cancelada pelo usuário");
                    return;
                }
            }
            
            boolean deleted = bookService.deleteBook(selectedBook.getBookId());
            if (deleted) {
                tableBooks.getItems().remove(selectedBook);
                resetForm();
                if (warningMessage != null) {
                    displaySuccessMessage("Livro excluído com sucesso! Os empréstimos ativos foram finalizados e as cópias dos livros foram atualizadas.");
                } else {
                    displaySuccessMessage("Livro excluído com sucesso!");
                }
            } else {
                displayErrorMessage("Erro ao remover livro");
            }
        } else {
            displayErrorMessage("Selecione um livro para remover");
        }
    }

    private void handleCreateBook(String title, String author, String isbn, int publicationYear, int availableCopies) {
        if (bookService.isbnExists(isbn)) {
            displayErrorMessage("ISBN já cadastrado. Por favor, verifique o código ISBN informado.");
            return;
        }
        
        Book savedBook = bookService.createBook(title, author, isbn, publicationYear, availableCopies);

        if (savedBook != null) {
            resetForm();
            loadInitialData();
            displaySuccessMessage("Livro cadastrado com sucesso!");
        } else {
            displayErrorMessage("Erro ao cadastrar livro");
        }
    }

    private void handleUpdateBook(String title, String author, String isbn, int publicationYear, int availableCopies) {
        if (editingBookId != null) {
            if (bookService.isbnExistsExcludingBook(isbn, editingBookId)) {
                displayErrorMessage("ISBN já cadastrado em outro livro. Por favor, verifique o código ISBN informado.");
                return;
            }
            
            Book bookToUpdate = new Book(editingBookId, title, author, isbn, publicationYear, availableCopies);
            Book updatedBook = bookService.updateBook(bookToUpdate);

            if (updatedBook != null) {
                resetForm();
                loadInitialData();
                displaySuccessMessage("Livro atualizado com sucesso!");
            } else {
                displayErrorMessage("Erro ao atualizar livro");
            }
        } else {
            displayErrorMessage("Nenhum livro selecionado para edição");
        }
    }

    private void displayErrorMessage(String message) {
        BookMessageUtils.displayErrorMessage(lblMessage, message);
    }
    
    private void displaySuccessMessage(String message) {
        BookMessageUtils.displaySuccessMessage(lblMessage, message);
    }
    
    private void displayConfirmationMessage(String message) {
        BookMessageUtils.displayConfirmationMessage(lblMessage, message);
    }

    private void resetForm() {
        txtTitle.clear();
        txtAuthor.clear();
        txtIsbn.clear();
        txtPublicationYear.clear();
        txtAvailableCopies.clear();
        editingBookId = null;
        btnSave.setText("Cadastrar");
        btnCancel.setVisible(false);
        BookMessageUtils.clearMessage(lblMessage);
    }

    public void loadInitialData() {
        List<Book> books = bookService.getAllBooks();
        tableBooks.getItems().clear();
        tableBooks.getItems().addAll(books);
    }
}
