package br.edu.ifba.inf008.plugins.persistence;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.models.Loan;
import br.edu.ifba.inf008.persistence.util.JPAUtil;
import br.edu.ifba.inf008.plugins.persistence.interfaces.IUserLoanDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class UserLoanDAO implements IUserLoanDAO {

    private EntityManagerFactory emf;

    public UserLoanDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    @Override
    public Optional<Loan> findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            Loan loan = em.find(Loan.class, id);
            return Optional.ofNullable(loan);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Loan> findByUserId(Integer userId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Loan> query = em.createQuery(
                "SELECT l FROM Loan l JOIN FETCH l.book JOIN FETCH l.user WHERE l.user.userId = :userId", 
                Loan.class
            );
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Loan update(Loan loan) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Loan updatedLoan = em.merge(loan);
            em.getTransaction().commit();
            return updatedLoan;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Loan loan = em.find(Loan.class, id);
            if (loan != null) {
                em.remove(loan);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public Loan save(Loan loan) {
        System.out.println("Save method not implemented in UserLoanDAO");
        return null;
    }
}
