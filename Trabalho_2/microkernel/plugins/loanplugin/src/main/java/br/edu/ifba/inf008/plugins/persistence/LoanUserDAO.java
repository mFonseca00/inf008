package br.edu.ifba.inf008.plugins.persistence;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.models.User;
import br.edu.ifba.inf008.persistence.util.JPAUtil;
import br.edu.ifba.inf008.plugins.persistence.interfaces.ILoanUserDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class LoanUserDAO implements ILoanUserDAO {

    private EntityManagerFactory emf;

    public LoanUserDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    @Override
    public Optional<User> findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, id);
            return Optional.ofNullable(user);
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> findByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(:name)", 
                User.class
            );
            query.setParameter("name", "%" + name + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public User save(User user) {
        System.out.println("Save method not implemented in LoanUserDAO");
        return null;
    }

    @Override
    public User update(User user) {
        System.out.println("Update method not implemented in LoanUserDAO");
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        System.out.println("Delete method not implemented in LoanUserDAO");
        return false;
    }
}