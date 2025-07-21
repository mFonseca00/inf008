package br.edu.ifba.inf008.plugins.ui.components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.edu.ifba.inf008.interfaces.models.Loan;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ReportExporter {
    
    private static final DateTimeFormatter DATETIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * Exporta o ranking de usuários para CSV
     */
    public static String exportUserRanking(ObservableList<Object[]> data) throws IOException {
        String timestamp = LocalDateTime.now().format(DATETIME_FORMATTER);
        String defaultFilename = "ranking_usuarios_" + timestamp + ".csv";
        
        File selectedFile = showSaveDialog("Exportar Ranking de Usuários", defaultFilename);
        if (selectedFile == null) {
            return null; // Usuário cancelou
        }
        
        try (FileWriter writer = new FileWriter(selectedFile)) {
            // Cabeçalho
            writer.write("Nome,Email,Quantidade de Empréstimos\n");
            
            // Dados
            for (Object[] row : data) {
                writer.write(String.format("\"%s\",\"%s\",%s\n", 
                    row[0], row[1], row[2]));
            }
        }
        
        return selectedFile.getAbsolutePath();
    }
    
    /**
     * Exporta o ranking de livros para CSV
     */
    public static String exportBookRanking(ObservableList<Object[]> data) throws IOException {
        String timestamp = LocalDateTime.now().format(DATETIME_FORMATTER);
        String defaultFilename = "ranking_livros_" + timestamp + ".csv";
        
        File selectedFile = showSaveDialog("Exportar Ranking de Livros", defaultFilename);
        if (selectedFile == null) {
            return null; // Usuário cancelou
        }
        
        try (FileWriter writer = new FileWriter(selectedFile)) {
            // Cabeçalho
            writer.write("Título,Autor,ISBN,Quantidade de Empréstimos\n");
            
            // Dados
            for (Object[] row : data) {
                writer.write(String.format("\"%s\",\"%s\",\"%s\",%s\n", 
                    row[0], row[1], row[2], row[3]));
            }
        }
        
        return selectedFile.getAbsolutePath();
    }
    
    /**
     * Exporta os empréstimos ativos para CSV
     */
    public static String exportActiveLoans(ObservableList<Loan> data) throws IOException {
        String timestamp = LocalDateTime.now().format(DATETIME_FORMATTER);
        String defaultFilename = "emprestimos_ativos_" + timestamp + ".csv";
        
        File selectedFile = showSaveDialog("Exportar Empréstimos Ativos", defaultFilename);
        if (selectedFile == null) {
            return null; // Usuário cancelou
        }
        
        try (FileWriter writer = new FileWriter(selectedFile)) {
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
        
        return selectedFile.getAbsolutePath();
    }
    
    /**
     * Mostra o dialog para o usuário escolher onde salvar o arquivo
     */
    private static File showSaveDialog(String title, String defaultFilename) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialFileName(defaultFilename);
        
        // Configurar filtros de extensão
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv");
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("Todos os arquivos", "*.*");
        fileChooser.getExtensionFilters().addAll(csvFilter, allFilter);
        
        // Definir diretório inicial (Documentos do usuário)
        String userHome = System.getProperty("user.home");
        File documentsDir = new File(userHome, "Documents");
        if (documentsDir.exists()) {
            fileChooser.setInitialDirectory(documentsDir);
        } else {
            fileChooser.setInitialDirectory(new File(userHome));
        }
        
        // Mostrar o dialog de salvamento
        return fileChooser.showSaveDialog(new Stage());
    }
}