package br.edu.ifba.inf008.plugins.persistence;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.models.Loan;
import br.edu.ifba.inf008.persistence.util.JPAUtil;
import br.edu.ifba.inf008.plugins.persistence.interfaces.IReportDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class ReportDAO implements IReportDAO {

    private EntityManagerFactory emf;

    public ReportDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    @Override
    public List<Loan> findActiveLoans() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Loan> query = em.createQuery(
                "SELECT l FROM Loan l JOIN FETCH l.book JOIN FETCH l.user WHERE l.returnDate IS NULL ORDER BY l.loanDate ASC", 
                Loan.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> findBookLoanRanking() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                "SELECT l.book.title, l.book.author, l.book.isbn, COUNT(l.book) as loanCount " +
                "FROM Loan l " +
                "GROUP BY l.book.title, l.book.author, l.book.isbn " +
                "ORDER BY loanCount DESC", 
                Object[].class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> findUserLoanRanking() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                "SELECT l.user.name, l.user.email, COUNT(l.user) as loanCount " +
                "FROM Loan l " +
                "GROUP BY l.user.name, l.user.email " +
                "ORDER BY loanCount DESC", 
                Object[].class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    
    @Override
    public Optional<Loan> findById(Integer id) {
        System.out.println("findById method not implemented in ReportDAO");
        return Optional.empty();
    }

    @Override
    public Loan update(Loan loan) {
        System.out.println("Update method not implemented in ReportDAO");
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        System.out.println("Delete method not implemented in ReportDAO");
        return false;
    }

        @Override
    public Loan save(Loan loan) {
        System.out.println("Save method not implemented in ReportDAO");
        return null;
    }
}
