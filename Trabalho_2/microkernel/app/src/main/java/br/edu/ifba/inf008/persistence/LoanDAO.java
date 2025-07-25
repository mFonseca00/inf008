package br.edu.ifba.inf008.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.models.Loan;
import br.edu.ifba.inf008.interfaces.persistence.ILoanDAO;
import br.edu.ifba.inf008.persistence.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class LoanDAO implements ILoanDAO {

    private EntityManagerFactory emf;

    public LoanDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    @Override
    public Loan save(Loan loan) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(loan);
            em.getTransaction().commit();
            return loan;
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
    public List<Loan> findByBookId(Integer bookId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Loan> query = em.createQuery(
                "SELECT l FROM Loan l WHERE l.book.bookId = :bookId", 
                Loan.class
            );
            query.setParameter("bookId", bookId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Loan> findByUserId(Integer userId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Loan> query = em.createQuery(
                "SELECT l FROM Loan l WHERE l.user.userId = :userId", 
                Loan.class
            );
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Loan> findByUserIdWithDetails(Integer userId) {
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
    public List<Loan> findByBookIdWithDetails(Integer bookId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Loan> query = em.createQuery(
                "SELECT u FROM Loan u JOIN FETCH u.user JOIN FETCH u.book WHERE u.book.bookId = :bookId", 
                Loan.class
            );
            query.setParameter("bookId", bookId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Loan> findByLoanDate(LocalDate loanDate) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Loan> query = em.createQuery(
                "SELECT l FROM Loan l LEFT JOIN FETCH l.user LEFT JOIN FETCH l.book WHERE l.loanDate = :loanDate", 
                Loan.class
            );
            query.setParameter("loanDate", loanDate);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Loan> findByReturnDate(LocalDate returnDate) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Loan> query = em.createQuery(
                "SELECT l FROM Loan l LEFT JOIN FETCH l.user LEFT JOIN FETCH l.book WHERE l.returnDate = :returnDate", 
                Loan.class
            );
            query.setParameter("returnDate", returnDate);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Loan> findByUserName(String userName) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Loan> query = em.createQuery(
                "SELECT l FROM Loan l JOIN FETCH l.book JOIN FETCH l.user " +
                "WHERE LOWER(l.user.name) LIKE LOWER(:userName)", 
                Loan.class
            );
            query.setParameter("userName", "%" + userName + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Loan> findByBookTitle(String bookTitle) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Loan> query = em.createQuery(
                "SELECT l FROM Loan l JOIN FETCH l.book JOIN FETCH l.user " +
                "WHERE LOWER(l.book.title) LIKE LOWER(:bookTitle)", 
                Loan.class
            );
            query.setParameter("bookTitle", "%" + bookTitle + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Loan> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Loan> query = em.createQuery(
                "SELECT l FROM Loan l JOIN FETCH l.book JOIN FETCH l.user", 
                Loan.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
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
}
