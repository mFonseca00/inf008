package br.edu.ifba.inf008.plugins.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.models.Book;
import br.edu.ifba.inf008.models.Loan;
import br.edu.ifba.inf008.models.User;
import br.edu.ifba.inf008.plugins.persistence.interfaces.ILoanBookDAO;
import br.edu.ifba.inf008.plugins.persistence.interfaces.ILoanDAO;

public class LoanService {

    private ILoanDAO getLoanDAO() {
        return ICore.getInstance().getDAO(ILoanDAO.class);
    }

    private ILoanBookDAO getBookDAO() {
        return ICore.getInstance().getDAO(ILoanBookDAO.class);
    }

    public List<Loan> getAllLoans() {
        return getLoanDAO().findAll();
    }

    public Optional<Loan> findLoanById(Integer loanId) {
        return getLoanDAO().findById(loanId);
    }

    public List<Loan> findLoansByUserName(String userName) {
        return getLoanDAO().findByUserName(userName);
    }
    
    public List<Loan> findLoansByBookTitle(String bookTitle) {
        return getLoanDAO().findByBookTitle(bookTitle);
    }

    public List<Loan> findLoansByLoanDate(LocalDate date) {
        return getLoanDAO().findByLoanDate(date);
    }

    public List<Loan> findLoansByReturnDate(LocalDate date) {
        return getLoanDAO().findByReturnDate(date);
    }

    public Loan createLoan(User user, Book book, LocalDate loanDate) {
        if (book.getCopiesAvailable() <= 0) {
            throw new IllegalArgumentException("Não há cópias disponíveis deste livro para empréstimo");
        }
        
        Loan loan = new Loan(book, user, loanDate, null);
        
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        getBookDAO().update(book);

        return getLoanDAO().save(loan);
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
        
        Optional<Book> originalBookOpt = getBookDAO().findById(originalLoan.getBook().getBookId());
        Optional<Book> newBookOpt = getBookDAO().findById(loan.getBook().getBookId());
        
        if (!originalBookOpt.isPresent() || !newBookOpt.isPresent()) {
            throw new IllegalArgumentException("Livro não encontrado");
        }
        
        Book originalBook = originalBookOpt.get();
        Book newBook = newBookOpt.get();
        
        if (originalLoan.getReturnDate() == null && 
                !originalBook.getBookId().equals(newBook.getBookId())) {
            
            originalBook.setCopiesAvailable(originalBook.getCopiesAvailable() + 1);
            getBookDAO().update(originalBook);
            
            if (newBook.getCopiesAvailable() <= 0) {
                throw new IllegalArgumentException("Não há cópias disponíveis do livro selecionado");
            }
            
            newBook.setCopiesAvailable(newBook.getCopiesAvailable() - 1);
            getBookDAO().update(newBook);
        }
        
        if (originalLoan.getReturnDate() == null && loan.getReturnDate() != null) {
            Book bookToReturn = newBook;
            bookToReturn.setCopiesAvailable(bookToReturn.getCopiesAvailable() + 1);
            getBookDAO().update(bookToReturn);
        }
        
        if (originalLoan.getReturnDate() != null && loan.getReturnDate() == null) {
            if (newBook.getCopiesAvailable() <= 0) {
                throw new IllegalArgumentException("Não há cópias disponíveis do livro para reativar o empréstimo");
            }
            newBook.setCopiesAvailable(newBook.getCopiesAvailable() - 1);
            getBookDAO().update(newBook);
        }
        
        return getLoanDAO().update(loan);
    }

    public boolean deleteLoan(Integer loanId) throws IllegalStateException {
        Optional<Loan> loanOpt = findLoanById(loanId);
        
        if (loanOpt.isPresent()) {
            Loan loan = loanOpt.get();
            
            if (loan.getReturnDate() == null) {
                throw new IllegalStateException("Não é permitido excluir empréstimos que ainda não foram devolvidos. Efetue a devolução antes de excluir.");
            }
            
            return getLoanDAO().delete(loanId);
        }
        
        return false;
    }
}
