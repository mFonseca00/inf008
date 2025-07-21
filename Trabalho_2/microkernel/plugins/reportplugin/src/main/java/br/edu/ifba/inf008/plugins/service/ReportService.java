package br.edu.ifba.inf008.plugins.service;

import java.util.List;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.models.Loan;

public class ReportService {
    
    /**
     * Obtém o ranking de usuários com mais empréstimos
     * @return Lista de arrays com [nome, email, quantidade_emprestimos]
     */
    public List<Object[]> getUserLoanRanking() {
        return ICore.getInstance().getLoanDAO().findUserLoanRanking();
    }
    
    /**
     * Obtém o ranking de livros mais emprestados
     * @return Lista de arrays com [titulo, autor, isbn, quantidade_emprestimos]
     */
    public List<Object[]> getBookLoanRanking() {
        return ICore.getInstance().getLoanDAO().findBookLoanRanking();
    }
    
    /**
     * Obtém todos os empréstimos ativos (não devolvidos)
     * @return Lista de empréstimos ativos
     */
    public List<Loan> getActiveLoans() {
        return ICore.getInstance().getLoanDAO().findActiveLoans();
    }
}