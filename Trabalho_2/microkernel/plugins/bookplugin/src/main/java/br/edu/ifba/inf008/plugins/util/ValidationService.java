package br.edu.ifba.inf008.plugins.util;

public class ValidationService {
    
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean isValidPublicationYear(int publicationYear) {
        return publicationYear <= java.time.Year.now().getValue();
    }

    public static boolean isValidCopiesNumber(int availableCopies) {
        return availableCopies >= 0;
    }
}
