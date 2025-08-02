package br.edu.ifba.inf008.plugins.persistence;

import java.util.Optional;

import br.edu.ifba.inf008.models.Book;
import br.edu.ifba.inf008.persistence.util.JPAUtil;
import br.edu.ifba.inf008.plugins.persistence.interfaces.IUserBookDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


public class UserBookDAO implements IUserBookDAO {

    private EntityManagerFactory emf;

    public UserBookDAO() {
        this.emf = JPAUtil.getEntityManagerFactory();
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
        System.out.println("Save method not implemented in UserBookDAO");
        return null;
    }

    @Override
    public Optional<Book> findById(Integer id) {
        System.out.println("Find by ID method not implemented in UserBookDAO");
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        System.out.println("Delete method not implemented in UserBookDAO");
        return false;
    }
}
