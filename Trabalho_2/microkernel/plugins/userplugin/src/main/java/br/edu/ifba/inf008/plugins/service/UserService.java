package br.edu.ifba.inf008.plugins.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.models.Book;
import br.edu.ifba.inf008.models.Loan;
import br.edu.ifba.inf008.models.User;
import br.edu.ifba.inf008.plugins.persistence.interfaces.IUserBookDAO;
import br.edu.ifba.inf008.plugins.persistence.interfaces.IUserDAO;
import br.edu.ifba.inf008.plugins.persistence.interfaces.IUserLoanDAO;

public class UserService {

    private IUserDAO getUserDAO() {
        return ICore.getInstance().getDAO(IUserDAO.class);
    }

    private IUserLoanDAO getLoanDAO() {
        return ICore.getInstance().getDAO(IUserLoanDAO.class);
    }

    private IUserBookDAO getBookDAO() {
        return ICore.getInstance().getDAO(IUserBookDAO.class);
    }

    public List<User> getAllUsers() {
        return getUserDAO().findAll();
    }

    public Optional<User> findUserById(Integer userId) {
        return getUserDAO().findById(userId);
    }

    public Optional<User> findUserByEmail(String email) {
        return getUserDAO().findByEmail(email);
    }

    public List<User> findUsersByName(String name) {
        return getUserDAO().findByName(name);
    }

    public List<User> findUsersByEmail(String email) {
        return getUserDAO().findByEmailLike(email);
    }

    public User createUser(String name, String email) {
        User newUser = new User(name, email);
        return getUserDAO().save(newUser);
    }

    public User updateUser(User user) {
        return getUserDAO().update(user);
    }

    public String getActiveLoansWarning(Integer userId) {
        List<Loan> activeLoans = getLoanDAO().findByUserId(userId);

        List<Loan> unreturnedLoans = activeLoans.stream()
            .filter(loan -> loan.getReturnDate() == null)
            .toList();

        if (unreturnedLoans.isEmpty()) {
            return null;
        }

        StringBuilder warning = new StringBuilder();
        warning.append("ATENÇÃO: O usuário possui ").append(unreturnedLoans.size())
               .append(" empréstimo(s) ativo(s):\n\n");

        for (Loan loan : unreturnedLoans) {
            warning.append("• ").append(loan.getBook().getTitle())
                   .append(" (").append(loan.getBook().getAuthor()).append(")")
                   .append(" - Emprestado em: ").append(loan.getLoanDate())
                   .append("\n");
        }

        warning.append("\nAo excluir o usuário, estes empréstimos serão automaticamente finalizados ")
               .append("e as cópias dos livros serão devolvidas ao estoque.\n");

        return warning.toString();
    }

    public boolean deleteUser(Integer userId) {
        List<Loan> activeLoans = getLoanDAO().findByUserId(userId);

        List<Loan> unreturnedLoans = activeLoans.stream()
            .filter(loan -> loan.getReturnDate() == null)
            .toList();

        if (!unreturnedLoans.isEmpty()) {
            for (Loan loan : unreturnedLoans) {
                Book book = loan.getBook();
                if (book != null) {
                    book.setCopiesAvailable(book.getCopiesAvailable() + 1);
                    getBookDAO().update(book);
                }
                getLoanDAO().delete(loan.getLoanId());
            }
        }

        return getUserDAO().delete(userId);
    }

    public boolean isEmailExists(String email) {
        Optional<User> existingUser = findUserByEmail(email);
        return existingUser.isPresent();
    }

    public boolean isEmailExistsExcludingUser(String email, Integer userId) {
        Optional<User> existingUser = findUserByEmail(email);
        return existingUser.isPresent() && !existingUser.get().getUserId().equals(userId);
    }
}