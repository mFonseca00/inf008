package br.edu.ifba.inf008.plugins.util;

import java.util.regex.Pattern;

public class ValidationService {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)\\.(\\w+)$");
        
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }
}
