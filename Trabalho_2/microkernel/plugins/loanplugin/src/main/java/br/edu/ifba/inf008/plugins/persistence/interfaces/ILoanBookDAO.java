package br.edu.ifba.inf008.plugins.persistence.interfaces;

import java.util.List;

import br.edu.ifba.inf008.interfaces.IGenericDAO;
import br.edu.ifba.inf008.models.Book;

public interface ILoanBookDAO extends IGenericDAO<Book, Integer> {
    List<Book> findByTitle(String title);
    List<Book> findAll();
}
