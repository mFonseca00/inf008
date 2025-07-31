package br.edu.ifba.inf008.plugins.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.models.User;
import br.edu.ifba.inf008.plugins.persistence.interfaces.IUserDAO;

public class LoanUserService {

    private IUserDAO getUserDAO() {
        return ICore.getInstance().getDAO(IUserDAO.class);
    }

    public List<User> getAllUsers() {
        return getUserDAO().findAll();
    }

    public Optional<User> findUserById(Integer userId) {
        return getUserDAO().findById(userId);
    }

    public List<User> findUsersByName(String name) {
        return getUserDAO().findByName(name);
    }
}