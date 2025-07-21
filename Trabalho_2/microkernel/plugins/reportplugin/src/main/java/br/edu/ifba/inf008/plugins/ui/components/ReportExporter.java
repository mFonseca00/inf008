package br.edu.ifba.inf008.plugins.ui.components;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.edu.ifba.inf008.interfaces.models.Loan;
import javafx.collections.ObservableList;

public class ReportExporter {
    
    private static final DateTimeFormatter DATETIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * Exporta o ranking de usuários para CSV
     */
    public static void exportUserRanking(ObservableList<Object[]> data) throws IOException {
        String timestamp = LocalDateTime.now().format(DATETIME_FORMATTER);
        String filename = "ranking_usuarios_" + timestamp + ".csv";
        
        try (FileWriter writer = new FileWriter(filename)) {
            // Cabeçalho
            writer.write("Nome,Email,Quantidade de Empréstimos\n");
            
            // Dados
            for (Object[] row : data) {
                writer.write(String.format("\"%s\",\"%s\",%s\n", 
                    row[0], row[1], row[2]));
            }
        }
        
        System.out.println("Relatório exportado: " + filename);
    }
    
    /**
     * Exporta o ranking de livros para CSV
     */
    public static void exportBookRanking(ObservableList<Object[]> data) throws IOException {
        String timestamp = LocalDateTime.now().format(DATETIME_FORMATTER);
        String filename = "ranking_livros_" + timestamp + ".csv";
        
        try (FileWriter writer = new FileWriter(filename)) {
            // Cabeçalho
            writer.write("Título,Autor,ISBN,Quantidade de Empréstimos\n");
            
            // Dados
            for (Object[] row : data) {
                writer.write(String.format("\"%s\",\"%s\",\"%s\",%s\n", 
                    row[0], row[1], row[2], row[3]));
            }
        }
        
        System.out.println("Relatório exportado: " + filename);
    }
    
    /**
     * Exporta os empréstimos ativos para CSV
     */
    public static void exportActiveLoans(ObservableList<Loan> data) throws IOException {
        String timestamp = LocalDateTime.now().format(DATETIME_FORMATTER);
        String filename = "emprestimos_ativos_" + timestamp + ".csv";
        
        try (FileWriter writer = new FileWriter(filename)) {
            // Cabeçalho
            writer.write("ID,Usuário,Email,Livro,Autor,Data do Empréstimo,Dias em Aberto\n");
            
            // Dados
            for (Loan loan : data) {
                long daysOpen = java.time.temporal.ChronoUnit.DAYS.between(
                    loan.getLoanDate(), java.time.LocalDate.now());
                
                writer.write(String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",%s,%d\n",
                    loan.getLoanId(),
                    loan.getUser().getName(),
                    loan.getUser().getEmail(),
                    loan.getBook().getTitle(),
                    loan.getBook().getAuthor(),
                    loan.getLoanDate().format(DATE_FORMATTER),
                    daysOpen
                ));
            }
        }
        
        System.out.println("Relatório exportado: " + filename);
    }
}