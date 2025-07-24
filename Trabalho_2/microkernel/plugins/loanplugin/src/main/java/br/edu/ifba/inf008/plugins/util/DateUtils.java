package br.edu.ifba.inf008.plugins.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    
    private static final DateTimeFormatter BRAZILIAN_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static LocalDate parseFlexibleDate(String dateText) throws IllegalArgumentException {
        if (dateText == null || dateText.trim().isEmpty()) {
            throw new IllegalArgumentException("Data não pode estar vazia");
        }
        
        dateText = dateText.trim();
        
        if (dateText.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            try {
                return LocalDate.parse(dateText, BRAZILIAN_FORMAT);
            } catch (DateTimeParseException e) {
                String[] parts = dateText.split("/");
                if (parts.length == 3) {
                    String formattedDate = String.format("%02d/%02d/%s", 
                        Integer.parseInt(parts[0]), 
                        Integer.parseInt(parts[1]), 
                        parts[2]);
                    return LocalDate.parse(formattedDate, BRAZILIAN_FORMAT);
                }
            }
        }
        
        if (dateText.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
            try {
                return LocalDate.parse(dateText, ISO_FORMAT);
            } catch (DateTimeParseException e) {
                return LocalDate.parse(dateText);
            }
        }
        
        throw new IllegalArgumentException("Formato de data inválido. Use DD/MM/AAAA (ex: 12/09/2025) ou AAAA-MM-DD");
    }
    
    public static String formatToBrazilian(LocalDate date) {
        return date != null ? date.format(BRAZILIAN_FORMAT) : "";
    }
}