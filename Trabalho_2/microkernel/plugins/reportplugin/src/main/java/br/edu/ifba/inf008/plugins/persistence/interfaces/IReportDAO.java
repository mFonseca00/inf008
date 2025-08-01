package br.edu.ifba.inf008.plugins.persistence.interfaces;

import java.util.List;

import br.edu.ifba.inf008.interfaces.IGenericDAO;
import br.edu.ifba.inf008.models.Loan;

public interface IReportDAO extends IGenericDAO<Loan, Integer> {
    List<Loan> findActiveLoans();
    List<Object[]> findBookLoanRanking();
    List<Object[]> findUserLoanRanking();
}
