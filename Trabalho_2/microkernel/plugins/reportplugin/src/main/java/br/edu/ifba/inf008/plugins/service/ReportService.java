package br.edu.ifba.inf008.plugins.service;

import java.util.List;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.models.Loan;
import br.edu.ifba.inf008.plugins.persistence.interfaces.ILoanDAO;

public class ReportService {

    private ILoanDAO getLoanDAO() {
        return ICore.getInstance().getDAO(ILoanDAO.class);
    }

    public List<Object[]> getUserLoanRanking() {
        return getLoanDAO().findUserLoanRanking();
    }

    public List<Object[]> getBookLoanRanking() {
        return getLoanDAO().findBookLoanRanking();
    }

    public List<Loan> getActiveLoans() {
        return getLoanDAO().findActiveLoans();
    }
}