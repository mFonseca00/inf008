package br.edu.ifba.inf008.interfaces.persistence;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.models.Book;

public interface IBookDAO {
    Book save(Book book);

    Optional<Book> findById(Integer id);
    List<Book> findByIsbn(String isbn);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByPublishedYear(int year);
    List<Book> findAll();

    Book update(Book book);

    boolean delete(Integer id);
}
