package br.edu.ifba.inf008.plugins.controller;

import java.util.List;

import br.edu.ifba.inf008.interfaces.models.Loan;
import br.edu.ifba.inf008.plugins.service.ReportService;
import br.edu.ifba.inf008.plugins.ui.components.ReportExporter;
import br.edu.ifba.inf008.plugins.ui.components.ReportMessageUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class ReportController {
    
    private ReportService reportService;
    
    private TableView<Object[]> tableUserRanking;
    private TableView<Object[]> tableBookRanking;
    private TableView<Loan> tableActiveLoans;
    
    private Button btnRefreshUserRanking;
    private Button btnExportUserRanking;
    private Button btnRefreshBookRanking;
    private Button btnExportBookRanking;
    private Button btnRefreshActiveLoans;
    private Button btnExportActiveLoans;
    
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    
    public void initialize(
            TableView<Object[]> tableUserRanking, TableView<Object[]> tableBookRanking,
            TableView<Loan> tableActiveLoans,
            Button btnRefreshUserRanking, Button btnExportUserRanking,
            Button btnRefreshBookRanking, Button btnExportBookRanking,
            Button btnRefreshActiveLoans, Button btnExportActiveLoans) {
        
        this.tableUserRanking = tableUserRanking;
        this.tableBookRanking = tableBookRanking;
        this.tableActiveLoans = tableActiveLoans;
        
        this.btnRefreshUserRanking = btnRefreshUserRanking;
        this.btnExportUserRanking = btnExportUserRanking;
        this.btnRefreshBookRanking = btnRefreshBookRanking;
        this.btnExportBookRanking = btnExportBookRanking;
        this.btnRefreshActiveLoans = btnRefreshActiveLoans;
        this.btnExportActiveLoans = btnExportActiveLoans;
        
        // Carrega dados iniciais
        loadUserRanking();
        loadBookRanking();
        loadActiveLoans();
    }
    
    public void handleRefreshUserRanking() {
        loadUserRanking();
    }
    
    public void handleExportUserRanking() {
        try {
            String filePath = ReportExporter.exportUserRanking(tableUserRanking.getItems());
            if (filePath != null) {
                ReportMessageUtils.showSuccess("Relatório exportado com sucesso em:\n" + filePath);
            }
        } catch (Exception e) {
            ReportMessageUtils.showError("Erro ao exportar relatório: " + e.getMessage());
        }
    }
    
    public void handleRefreshBookRanking() {
        loadBookRanking();
    }
    
    public void handleExportBookRanking() {
        try {
            String filePath = ReportExporter.exportBookRanking(tableBookRanking.getItems());
            if (filePath != null) {
                ReportMessageUtils.showSuccess("Relatório exportado com sucesso em:\n" + filePath);
            }
        } catch (Exception e) {
            ReportMessageUtils.showError("Erro ao exportar relatório: " + e.getMessage());
        }
    }
    
    public void handleRefreshActiveLoans() {
        loadActiveLoans();
    }
    
    public void handleExportActiveLoans() {
        try {
            String filePath = ReportExporter.exportActiveLoans(tableActiveLoans.getItems());
            if (filePath != null) {
                ReportMessageUtils.showSuccess("Relatório exportado com sucesso em:\n" + filePath);
            }
        } catch (Exception e) {
            ReportMessageUtils.showError("Erro ao exportar relatório: " + e.getMessage());
        }
    }
    
    private void loadUserRanking() {
        try {
            List<Object[]> userRanking = reportService.getUserLoanRanking();
            ObservableList<Object[]> observableList = FXCollections.observableArrayList(userRanking);
            tableUserRanking.setItems(observableList);
        } catch (Exception e) {
            ReportMessageUtils.showError("Erro ao carregar ranking de usuários: " + e.getMessage());
        }
    }
    
    private void loadBookRanking() {
        try {
            List<Object[]> bookRanking = reportService.getBookLoanRanking();
            ObservableList<Object[]> observableList = FXCollections.observableArrayList(bookRanking);
            tableBookRanking.setItems(observableList);
        } catch (Exception e) {
            ReportMessageUtils.showError("Erro ao carregar ranking de livros: " + e.getMessage());
        }
    }
    
    private void loadActiveLoans() {
        try {
            List<Loan> activeLoans = reportService.getActiveLoans();
            ObservableList<Loan> observableList = FXCollections.observableArrayList(activeLoans);
            tableActiveLoans.setItems(observableList);
        } catch (Exception e) {
            ReportMessageUtils.showError("Erro ao carregar empréstimos ativos: " + e.getMessage());
        }
    }
}