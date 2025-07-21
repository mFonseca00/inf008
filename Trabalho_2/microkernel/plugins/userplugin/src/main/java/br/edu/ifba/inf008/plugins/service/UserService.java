package br.edu.ifba.inf008.plugins.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.models.Book;
import br.edu.ifba.inf008.interfaces.models.Loan;
import br.edu.ifba.inf008.interfaces.models.User;


public class UserService {

    public List<User> getAllUsers() {
        return ICore.getInstance().getUserDAO().findAll();
    }
    
   
    public Optional<User> findUserById(Integer userId) {
        return ICore.getInstance().getUserDAO().findById(userId);
    }
    

    public Optional<User> findUserByEmail(String email) {
        return ICore.getInstance().getUserDAO().findByEmail(email);
    }
    

    public List<User> findUsersByName(String name) {
        return ICore.getInstance().getUserDAO().findByName(name);
    }
    

    public User createUser(String name, String email) {
        User newUser = new User(name, email);
        return ICore.getInstance().getUserDAO().save(newUser);
    }
    

    public User updateUser(User user) {
        return ICore.getInstance().getUserDAO().update(user);
    }
    

    /**
     * Verifica se o usuário possui empréstimos ativos e retorna uma mensagem de aviso
     * @param userId ID do usuário
     * @return Mensagem de aviso se há empréstimos ativos, null caso contrário
     */
    public String getActiveLoansWarning(Integer userId) {
        List<Loan> activeLoans = ICore.getInstance().getLoanDAO().findByUserIdWithDetails(userId);
        
        // Filtrar apenas empréstimos realmente ativos (sem data de devolução)
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
               .append("e as cópias dos livros serão devolvidas ao estoque.");
        
        return warning.toString();
    }

    public boolean deleteUser(Integer userId) {
        // Verificar se o usuário possui empréstimos ativos
        List<Loan> activeLoans = ICore.getInstance().getLoanDAO().findByUserIdWithDetails(userId);
        
        // Filtrar apenas empréstimos realmente ativos (sem data de devolução)
        List<Loan> unreturnedLoans = activeLoans.stream()
            .filter(loan -> loan.getReturnDate() == null)
            .toList();
        
        // Se há empréstimos não devolvidos, incrementar cópias dos livros antes de excluir
        if (!unreturnedLoans.isEmpty()) {
            for (Loan loan : unreturnedLoans) {
                Book book = loan.getBook();
                if (book != null) {
                    // Incrementar o número de cópias disponíveis
                    book.setCopiesAvailable(book.getCopiesAvailable() + 1);
                    ICore.getInstance().getBookDAO().update(book);
                }
                
                // Remover o empréstimo ativo
                ICore.getInstance().getLoanDAO().delete(loan.getLoanId());
            }
        }
        
        // Após tratar os empréstimos ativos, excluir o usuário
        return ICore.getInstance().getUserDAO().delete(userId);
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