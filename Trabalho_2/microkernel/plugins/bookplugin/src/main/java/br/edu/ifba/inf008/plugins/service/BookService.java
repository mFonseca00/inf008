package br.edu.ifba.inf008.plugins.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.models.Book;


public class BookService {

    public List<Book> getAllBooks() {
        return ICore.getInstance().getBookDAO().findAll();
    }

    public Optional<Book> findBookById(Integer bookId) {
        return ICore.getInstance().getBookDAO().findById(bookId);
    }

    public List<Book> findBookByIsbn(String isbn) {
        return ICore.getInstance().getBookDAO().findByIsbn(isbn);
    }

    public List<Book> findBooksByTitle(String title) {
        return ICore.getInstance().getBookDAO().findByTitle(title);
    }

    public List<Book> findBooksByAuthor(String author) {
        return ICore.getInstance().getBookDAO().findByAuthor(author);
    }

    public List<Book> findBooksByPublishedYear(int publishedYear) {
        return ICore.getInstance().getBookDAO().findByPublishedYear(publishedYear);
    }

    public Book createBook(String title, String author, String isbn, int publicationYear, int availableCopies) {
        Book newBook = new Book(title, author, isbn, publicationYear, availableCopies);
        return ICore.getInstance().getBookDAO().save(newBook);
    }

    public Book updateBook(Book book) {
        return ICore.getInstance().getBookDAO().update(book);
    }

    public boolean deleteBook(Integer bookId) {
        return ICore.getInstance().getBookDAO().delete(bookId);
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
}