package br.edu.ifba.inf008.interfaces.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.edu.ifba.inf008.interfaces.models.Loan;

public interface ILoanDAO {
    Loan save(Loan loan);

    Optional<Loan> findById(Integer id);
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

    Loan update(Loan loan);

    boolean delete(Integer id);
}
