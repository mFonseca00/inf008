package br.edu.ifba.inf008.plugins.service;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.models.User;

import java.util.List;
import java.util.Optional;


public class UserService {

    public List<User> getAllUsers() {
        return ICore.getInstance().getUserDAO().findAll();
    }
    
   
    public Optional<User> findUserById(Integer userId) {
        return ICore.getInstance().getUserDAO().findById(userId);
    }
    

    public Optional<User> findUserByEmail(String email) {
        return ICore.getInstance().getUserDAO().findByEmail(email);
    }
    

    public List<User> findUsersByName(String name) {
        return ICore.getInstance().getUserDAO().findByName(name);
    }
    

    public User createUser(String name, String email) {
        User newUser = new User(name, email);
        return ICore.getInstance().getUserDAO().save(newUser);
    }
    

    public User updateUser(User user) {
        return ICore.getInstance().getUserDAO().update(user);
    }
    

    public boolean deleteUser(Integer userId) {
        return ICore.getInstance().getUserDAO().delete(userId);
    }
}
