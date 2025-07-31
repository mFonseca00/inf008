package br.edu.ifba.inf008.plugins.persistence.interfaces;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifba.inf008.interfaces.IGenericDAO;
import br.edu.ifba.inf008.models.Loan;

public interface ILoanDAO extends IGenericDAO<Loan, Integer> {
    List<Loan> findByBookId(Integer bookId);
    List<Loan> findByUserId(Integer userId);
    List<Loan> findByUserIdWithDetails(Integer userId);
    List<Loan> findByBookIdWithDetails(Integer bookId);
    List<Loan> findByLoanDate(LocalDate loanDate);
    List<Loan> findByReturnDate(LocalDate returnDate);
    List<Loan> findByUserName(String userName);
    List<Loan> findByBookTitle(String bookTitle);
    List<Loan> findAll();
    List<Loan> findActiveLoans();
    List<Object[]> findBookLoanRanking();
    List<Object[]> findUserLoanRanking();
}
