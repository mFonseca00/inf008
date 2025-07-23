package br.edu.ifba.inf008.plugins;

import java.io.IOException;

import br.edu.ifba.inf008.interfaces.ILibraryPlugin;
import br.edu.ifba.inf008.interfaces.IPluginUI;
import br.edu.ifba.inf008.interfaces.models.Loan;
import br.edu.ifba.inf008.plugins.controller.ReportController;
import br.edu.ifba.inf008.plugins.service.ReportService;
import br.edu.ifba.inf008.plugins.ui.ReportUIUtils;
import br.edu.ifba.inf008.plugins.ui.components.ReportTableFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class ReportPlugin implements IPluginUI, ILibraryPlugin {
    
    @FXML private TableView<Object[]> tableUserRanking;
    @FXML private TableView<Object[]> tableBookRanking;
    @FXML private TableView<Loan> tableActiveLoans;
    @FXML private Button btnRefreshUserRanking;
    @FXML private Button btnExportUserRanking;
    @FXML private Button btnRefreshBookRanking;
    @FXML private Button btnExportBookRanking;
    @FXML private Button btnRefreshActiveLoans;
    @FXML private Button btnExportActiveLoans;
    
    private ReportController controller;
    private ReportService reportService = new ReportService();
    
    @Override
    public boolean init() {
        System.out.println("ReportPlugin inicializado!");
        controller = new ReportController(reportService);
        return true;
    }
    
    @Override
    public String getLibraryFeatureType() {
        return "report";
    }

    @Override
    public String getMenuCategory() {
        return "Geral";
    }

    @Override
    public String getMenuItemName() {
        return "Visualizar Relatórios";
    }

    @Override
    public String getTabTitle() {
        return "Relatórios";
    }
    
    @Override
    public Node createTabContent() {
        try {
            java.net.URL fxmlUrl = getClass().getResource("/fxml/ReportView.fxml");
            
            if (fxmlUrl == null) {
                throw new IOException("Arquivo FXML não encontrado: /fxml/ReportView.fxml");
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setController(this);
            
            Node root = loader.load();
            
            setupComponents();
            
            return root;
        } catch (Exception e) {
            e.printStackTrace();
            
            return ReportUIUtils.createErrorContainer(
                "Não foi possível carregar a interface",
                "Ocorreu um erro ao carregar a interface do plugin de relatórios.",
                e.getMessage()
            );
        }
    }
    
    private void setupComponents() {
        ReportTableFactory.configureUserRankingTable(tableUserRanking);
        ReportTableFactory.configureBookRankingTable(tableBookRanking);
        ReportTableFactory.configureActiveLoansTable(tableActiveLoans);
        
        setupButtonActions();
        
        controller.initialize(
            tableUserRanking, tableBookRanking, tableActiveLoans,
            btnRefreshUserRanking, btnExportUserRanking,
            btnRefreshBookRanking, btnExportBookRanking,
            btnRefreshActiveLoans, btnExportActiveLoans
        );
    }
    
    private void setupButtonActions() {
        btnRefreshUserRanking.setOnAction(event -> controller.handleRefreshUserRanking());
        btnExportUserRanking.setOnAction(event -> controller.handleExportUserRanking());
        
        btnRefreshBookRanking.setOnAction(event -> controller.handleRefreshBookRanking());
        btnExportBookRanking.setOnAction(event -> controller.handleExportBookRanking());
        
        btnRefreshActiveLoans.setOnAction(event -> controller.handleRefreshActiveLoans());
        btnExportActiveLoans.setOnAction(event -> controller.handleExportActiveLoans());
    }
}