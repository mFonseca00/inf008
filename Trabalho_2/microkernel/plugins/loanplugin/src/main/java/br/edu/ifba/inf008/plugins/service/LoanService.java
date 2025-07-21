package br.edu.ifba.inf008.plugins.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.models.Book;
import br.edu.ifba.inf008.interfaces.models.Loan;
import br.edu.ifba.inf008.interfaces.models.User;

public class LoanService {

    public List<Loan> getAllLoans() {
        return ICore.getInstance().getLoanDAO().findAll();
    }

    public Optional<Loan> findLoanById(Integer loanId) {
        return ICore.getInstance().getLoanDAO().findById(loanId);
    }

    public List<Loan> findLoansByUserName(String userName) {
        return ICore.getInstance().getLoanDAO().findByUserName(userName);
    }
    
    public List<Loan> findLoansByBookTitle(String bookTitle) {
        return ICore.getInstance().getLoanDAO().findByBookTitle(bookTitle);
    }

    public List<Loan> findLoansByDate(LocalDate date) {
        return ICore.getInstance().getLoanDAO().findByLoanDate(date);
    }

    public Loan createLoan(User user, Book book, LocalDate loanDate) {
        // Verificar se há cópias disponíveis antes de criar o empréstimo
        if (book.getCopiesAvailable() <= 0) {
            throw new IllegalArgumentException("Não há cópias disponíveis deste livro para empréstimo");
        }
        
        Loan loan = new Loan(book, user, loanDate, null);
        
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        ICore.getInstance().getBookDAO().update(book);

        return ICore.getInstance().getLoanDAO().save(loan);
    }

    public Loan updateLoan(Loan loan) {
        if (loan == null || loan.getLoanId() == null) {
            throw new IllegalArgumentException("Empréstimo inválido");
        }
        
        Optional<Loan> originalLoanOpt = findLoanById(loan.getLoanId());
        if (!originalLoanOpt.isPresent()) {
            throw new IllegalArgumentException("Empréstimo não encontrado para atualização");
        }
        
        Loan originalLoan = originalLoanOpt.get();
        Book originalBook = originalLoan.getBook();
        Book newBook = loan.getBook();
        
        // Se a data de retorno está vazia (empréstimo ainda ativo) e houve troca de livro
        if (originalLoan.getReturnDate() == null && 
                !originalBook.getBookId().equals(newBook.getBookId())) {
            
            originalBook.setCopiesAvailable(originalBook.getCopiesAvailable() + 1);
            ICore.getInstance().getBookDAO().update(originalBook);
            
            if (newBook.getCopiesAvailable() <= 0) {
                throw new IllegalArgumentException("Não há cópias disponíveis do livro selecionado");
            }
            newBook.setCopiesAvailable(newBook.getCopiesAvailable() - 1);
            ICore.getInstance().getBookDAO().update(newBook);
        }
        
        // Se a data de retorno foi adicionada agora (devolução de livro)
        if (originalLoan.getReturnDate() == null && loan.getReturnDate() != null) {
            Book bookToReturn = newBook;
            bookToReturn.setCopiesAvailable(bookToReturn.getCopiesAvailable() + 1);
            ICore.getInstance().getBookDAO().update(bookToReturn);
        }
        
        // Se a data de retorno foi removida (reativação de empréstimo)
        if (originalLoan.getReturnDate() != null && loan.getReturnDate() == null) {
            Book bookToReborrow = newBook;
            if (bookToReborrow.getCopiesAvailable() <= 0) {
                throw new IllegalArgumentException("Não há cópias disponíveis do livro para reativar o empréstimo");
            }
            bookToReborrow.setCopiesAvailable(bookToReborrow.getCopiesAvailable() - 1);
            ICore.getInstance().getBookDAO().update(bookToReborrow);
        }
        
        return ICore.getInstance().getLoanDAO().update(loan);
    }

    public boolean deleteLoan(Integer loanId) throws IllegalStateException {
        Optional<Loan> loanOpt = findLoanById(loanId);
        
        if (loanOpt.isPresent()) {
            Loan loan = loanOpt.get();
            
            if (loan.getReturnDate() == null) {
                // Impede a exclusão de empréstimos ativos como uma feature de segurança
                throw new IllegalStateException("Não é permitido excluir empréstimos que ainda não foram devolvidos. Efetue a devolução antes de excluir.");
            }
            
            return ICore.getInstance().getLoanDAO().delete(loanId);
        }
        
        return false;
    }
}
