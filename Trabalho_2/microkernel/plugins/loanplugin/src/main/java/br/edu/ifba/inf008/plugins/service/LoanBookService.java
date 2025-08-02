package br.edu.ifba.inf008.plugins.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.models.Book;
import br.edu.ifba.inf008.plugins.persistence.interfaces.ILoanBookDAO;

public class LoanBookService {

    private ILoanBookDAO getBookDAO() {
        return ICore.getInstance().getDAO(ILoanBookDAO.class);
    }

    public List<Book> getAllBooks() {
        return getBookDAO().findAll();
    }

    public Optional<Book> findBookById(Integer bookId) {
        return getBookDAO().findById(bookId);
    }

    public List<Book> findBooksByTitle(String title) {
        return getBookDAO().findByTitle(title);
    }

    public List<Book> getAvailableBooks() {
        return getAllBooks().stream()
                .filter(book -> book.getCopiesAvailable() > 0)
                .collect(Collectors.toList());
    }

    public boolean isBookAvailable(Book book) {
        return book != null && book.getCopiesAvailable() > 0;
    }
}