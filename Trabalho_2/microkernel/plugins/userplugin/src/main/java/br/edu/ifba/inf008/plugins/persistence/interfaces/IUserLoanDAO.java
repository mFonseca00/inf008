package br.edu.ifba.inf008.plugins.persistence.interfaces;

import java.util.List;

import br.edu.ifba.inf008.interfaces.IGenericDAO;
import br.edu.ifba.inf008.models.Loan;

public interface IUserLoanDAO extends IGenericDAO<Loan, Integer> {
    List<Loan> findByUserId(Integer userId);
}
