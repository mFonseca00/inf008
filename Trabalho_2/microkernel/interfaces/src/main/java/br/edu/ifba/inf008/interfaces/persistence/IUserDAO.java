package br.edu.ifba.inf008.interfaces.persistence;

import br.edu.ifba.inf008.interfaces.models.User;
import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    // Criar
    User save(User user);
    
    // Ler
    Optional<User> findById(Long id);
    List<User> findAll();
    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
    
    // Atualizar
    User update(User user);
    
    // Deletar
    boolean delete(Long id);
}
