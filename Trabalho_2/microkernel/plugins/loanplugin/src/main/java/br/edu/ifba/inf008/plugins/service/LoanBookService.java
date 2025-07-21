package br.edu.ifba.inf008.plugins.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.models.Book;

public class LoanBookService {
    
    public List<Book> getAllBooks() {
        return ICore.getInstance().getBookDAO().findAll();
    }
    
    public Optional<Book> findBookById(Integer bookId) {
        return ICore.getInstance().getBookDAO().findById(bookId);
    }
    
    public List<Book> findBooksByTitle(String title) {
        return ICore.getInstance().getBookDAO().findByTitle(title);
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