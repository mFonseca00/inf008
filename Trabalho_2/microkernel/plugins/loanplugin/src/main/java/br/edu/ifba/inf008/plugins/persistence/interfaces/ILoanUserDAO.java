package br.edu.ifba.inf008.plugins.persistence.interfaces;

import java.util.List;

import br.edu.ifba.inf008.interfaces.IGenericDAO;
import br.edu.ifba.inf008.models.User;


public interface ILoanUserDAO extends IGenericDAO<User, Integer> {
    List<User> findByName(String name);
    List<User> findAll();
}
