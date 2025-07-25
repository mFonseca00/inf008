package br.edu.ifba.inf008.plugins.service;

import java.time.Year;

public class BookValidationService {
    
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean isValidPublicationYear(int publicationYear) {
        return publicationYear <= Year.now().getValue();
    }

    public static boolean isValidCopiesNumber(int availableCopies) {
        return availableCopies >= 0;
    }
}
