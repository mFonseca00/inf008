package br.edu.ifba.inf008.plugins.persistence.interfaces;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.IGenericDAO;
import br.edu.ifba.inf008.models.User;


public interface IUserDAO extends IGenericDAO<User, Integer> {
    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
    List<User> findByEmailLike(String email);
    List<User> findAll();
}
