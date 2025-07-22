package br.edu.ifba.inf008.plugins.service;

import java.util.List;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.models.Loan;

public class ReportService {
    
    public List<Object[]> getUserLoanRanking() {
        return ICore.getInstance().getLoanDAO().findUserLoanRanking();
    }
    
    public List<Object[]> getBookLoanRanking() {
        return ICore.getInstance().getLoanDAO().findBookLoanRanking();
    }
    
    public List<Loan> getActiveLoans() {
        return ICore.getInstance().getLoanDAO().findActiveLoans();
    }
}