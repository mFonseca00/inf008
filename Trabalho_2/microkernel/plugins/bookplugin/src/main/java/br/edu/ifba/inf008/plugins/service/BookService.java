package br.edu.ifba.inf008.plugins.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.models.Book;
import br.edu.ifba.inf008.models.Loan;
import br.edu.ifba.inf008.plugins.persistence.interfaces.IBookDAO;

public class BookService {

    private IBookDAO getBookDAO() {
        return ICore.getInstance().getDAO(IBookDAO.class);
    }

    public List<Book> getAllBooks() {
        return getBookDAO().findAll();
    }

    public Optional<Book> findBookById(Integer bookId) {
        return getBookDAO().findById(bookId);
    }

    public List<Book> findBookByIsbn(String isbn) {
        return getBookDAO().findByIsbn(isbn);
    }

    public List<Book> findBooksByTitle(String title) {
        return getBookDAO().findByTitle(title);
    }

    public List<Book> findBooksByAuthor(String author) {
        return getBookDAO().findByAuthor(author);
    }

    public List<Book> findBooksByPublishedYear(int publishedYear) {
        return getBookDAO().findByPublishedYear(publishedYear);
    }

    public Book createBook(String title, String author, String isbn, int publicationYear, int availableCopies) {
        Book newBook = new Book(title, author, isbn, publicationYear, availableCopies);
        return getBookDAO().save(newBook);
    }

    public Book updateBook(Book book) {
        return getBookDAO().update(book);
    }

    public boolean deleteBook(Integer bookId) {
        return getBookDAO().delete(bookId);
    }
    
    public boolean isbnExists(String isbn) {
        List<Book> booksWithSameIsbn = findBookByIsbn(isbn);
        return !booksWithSameIsbn.isEmpty();
    }
    
    public boolean isbnExistsExcludingBook(String isbn, Integer bookId) {
        List<Book> booksWithSameIsbn = findBookByIsbn(isbn);
        return booksWithSameIsbn.stream()
                .anyMatch(book -> !book.getBookId().equals(bookId));
    }

    public String getActiveLoansWarning(Integer bookId) {
        List<Loan> activeLoans = ICore.getInstance().getDAO(IBookDAO.class).findLoansByBookIdWithDetails(bookId);

        List<Loan> unreturnedLoans = activeLoans.stream()
            .filter(loan -> loan.getReturnDate() == null)
            .toList();
        
        if (unreturnedLoans.isEmpty()) {
            return null;
        }
        
        StringBuilder warning = new StringBuilder();
        warning.append("ATENÇÃO: O livro possui ").append(unreturnedLoans.size())
               .append(" empréstimo(s) ativo(s):\n\n");
        
        for (Loan loan : unreturnedLoans) {
            warning.append("• Emprestado para: ").append(loan.getUser().getName())
                   .append(" - Empréstimo em: ").append(loan.getLoanDate())
                   .append("\n");
        }

        warning.append("\nAo excluir o livro, estes empréstimos serão automaticamente finalizados.\n");
        
        return warning.toString();
    }
}