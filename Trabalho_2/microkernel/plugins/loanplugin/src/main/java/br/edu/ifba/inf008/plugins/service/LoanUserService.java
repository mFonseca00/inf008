package br.edu.ifba.inf008.plugins.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.models.User;

public class LoanUserService {
    
    /**
     * Recupera todos os usuários para exibição na combobox
     */
    public List<User> getAllUsers() {
        return ICore.getInstance().getUserDAO().findAll();
    }
    
    /**
     * Busca um usuário específico por ID
     */
    public Optional<User> findUserById(Integer userId) {
        return ICore.getInstance().getUserDAO().findById(userId);
    }
    
    /**
     * Busca usuários por nome (útil para filtrar a combobox conforme o usuário digita)
     */
    public List<User> findUsersByName(String name) {
        return ICore.getInstance().getUserDAO().findByName(name);
    }
}