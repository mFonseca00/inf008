package br.edu.ifba.inf008.plugins.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.models.User;

public class LoanUserService {
    
    public List<User> getAllUsers() {
        return ICore.getInstance().getUserDAO().findAll();
    }
    
    public Optional<User> findUserById(Integer userId) {
        return ICore.getInstance().getUserDAO().findById(userId);
    }
    
    public List<User> findUsersByName(String name) {
        return ICore.getInstance().getUserDAO().findByName(name);
    }
}