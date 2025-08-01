package br.edu.ifba.inf008.plugins.persistence;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.models.Book;
import br.edu.ifba.inf008.persistence.util.JPAUtil;
import br.edu.ifba.inf008.plugins.persistence.interfaces.ILoanBookDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;


public class LoanBookDAO implements ILoanBookDAO {

    private EntityManagerFactory emf;

    public LoanBookDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    @Override
    public Optional<Book> findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            Book book = em.find(Book.class, id);
            return Optional.ofNullable(book);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Book> findByTitle(String title) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(:title)", 
                Book.class
            );
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Book> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Book update(Book book) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Book updatedBook = em.merge(book);
            em.getTransaction().commit();
            return updatedBook;
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
    public Book save(Book book) {
        System.out.println("Save method not implemented in LoanBookDAO");
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        System.out.println("Delete method not implemented in LoanBookDAO");
        return false;
    }
}
