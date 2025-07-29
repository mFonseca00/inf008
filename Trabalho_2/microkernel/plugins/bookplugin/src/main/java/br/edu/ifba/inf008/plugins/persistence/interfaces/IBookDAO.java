package br.edu.ifba.inf008.plugins.persistence.interfaces;

import java.util.List;

import br.edu.ifba.inf008.interfaces.IGenericDAO;
import br.edu.ifba.inf008.models.Book;
import br.edu.ifba.inf008.models.Loan;

public interface IBookDAO extends IGenericDAO<Book, Integer> {
    List<Book> findByIsbn(String isbn);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByPublishedYear(int year);
    List<Book> findAll();
    List<Loan> findLoansByBookIdWithDetails(Integer bookId);
}
