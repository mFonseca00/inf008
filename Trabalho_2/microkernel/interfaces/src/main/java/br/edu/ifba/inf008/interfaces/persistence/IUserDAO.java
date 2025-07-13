package br.edu.ifba.inf008.interfaces.persistence;

import br.edu.ifba.inf008.interfaces.models.User;
import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    User save(User user);
    
    Optional<User> findById(Integer id);
    List<User> findAll();
    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
    
    User update(User user);
    
    boolean delete(Integer id);
}
