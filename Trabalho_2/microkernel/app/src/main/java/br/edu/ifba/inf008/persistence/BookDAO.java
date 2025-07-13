package br.edu.ifba.inf008.persistence;

import br.edu.ifba.inf008.interfaces.models.Book;
import br.edu.ifba.inf008.interfaces.persistence.IBookDAO;
import br.edu.ifba.inf008.persistence.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class BookDAO implements IBookDAO {

    private EntityManagerFactory emf;

    public BookDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    @Override
    public Book save(Book book) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            return book;
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
    public Optional<Book> findByIsbn(String isbn) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE b.isbn = :isbn", 
                Book.class
            );
            query.setParameter("isbn", isbn);
            try {
                Book book = query.getSingleResult();
                return Optional.of(book);
            } catch (NoResultException e) {
                return Optional.empty();
            }
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
    public List<Book> findByAuthor(String author) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(:author)", 
                Book.class
            );
            query.setParameter("author", "%" + author + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Book> findByPublishedYear(int year) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE b.publishedYear = :year", 
                Book.class
            );
            query.setParameter("year", year);
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
    public boolean delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Book book = em.find(Book.class, id);
            if (book != null) {
                em.remove(book);
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
