package acad_events.acadevents.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Input validation utility class for the AcadEvents system.
 * Provides validation methods for Brazilian document formats and common data types.
 * Essential for ensuring data integrity throughout the application.
 * 
 * Used by: Form classes for real-time input validation and TestDataGenerator for test data creation
 * Integration: Called from all user input forms to validate CPF, email, phone, and date formats
 */
public class ValidatorInputs {
    
    // Validates Brazilian CPF using official algorithm with check digits
    public static boolean isValidCPF(String cpf){
        if(cpf == null) return false;

        if(!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}") && !cpf.matches("\\d{11}")) return false;

        String digits = cpf.replaceAll("[^\\d]", "");

        if(digits.chars().distinct().count()==1) return false;

        int sum = 0, weight = 10;
        for (int i = 0; i < 9; i++) sum += (digits.charAt(i) - '0') * weight--;
        int firstCheck = 11 - (sum % 11);
        if (firstCheck >= 10) firstCheck = 0;
        if (firstCheck != (digits.charAt(9) - '0')) return false;

        sum = 0; weight = 11;
        for (int i = 0; i < 10; i++) sum += (digits.charAt(i) - '0') * weight--;
        int secondCheck = 11 - (sum % 11);
        if (secondCheck >= 10) secondCheck = 0;
        return secondCheck == (digits.charAt(10) - '0');
    }

    // Standard email format validation using regex pattern
    public static boolean isValidEmail(String email){
        if (email == null) return false;
        return email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$");
    }

    // Brazilian phone number format validation (supports multiple formats)
    public static boolean isValidPhone(String phone){
        if (phone == null) return false;

        if (!phone.matches("^\\d{2} \\d{4}-\\d{4}$") &&   // 11 1111-1111
            !phone.matches("^\\d{2} \\d{5}-\\d{4}$") &&   // 11 11111-1111
            !phone.matches("^\\d{3} \\d{4}-\\d{4}$") &&   // 111 1111-1111
            !phone.matches("^\\d{3} \\d{5}-\\d{4}$") &&   // 111 11111-1111
            !phone.matches("^\\d{10,11}$") &&             // (10 or 11 digits)
            !phone.matches("^\\d{2} \\d{8,9}$") &&        // (10 ou 11 digits)
            !phone.matches("^\\d{3} \\d{7,8}$"))          // (10 ou 11 digits)
            return false;

        return true;
    }

    // Validates positive integers for capacity, hours, and quantity fields
    public static boolean isPositiveInteger(String value){
        if (value == null) return false;
        return value.matches("^[1-9]\\d*$");
    }

    // Date validation for Brazilian format (dd/MM/yyyy) used in event scheduling
    public static boolean isValidDate(String date) {
        if (date == null) return false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Formats CPF to standard Brazilian display format (XXX.XXX.XXX-XX)
    public static String formatCPF(String cpf) {
        if (cpf == null) return null;
        
        cpf = cpf.replaceAll("[^\\d]", "");
        
        if (cpf.length() != 11) return null;
        
        try {
            return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
                   cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
    }

    // Formats phone numbers to standard Brazilian display patterns
    public static String formatPhone(String phone) {
        phone = phone.replaceAll("[^\\d]", "");
        if (phone.length() == 10) {
            return phone.substring(0, 2) + " " + phone.substring(2, 6) + "-" + phone.substring(6, 10);
        } else if (phone.length() == 11) {
            return phone.substring(0, 2) + " " + phone.substring(2, 7) + "-" + phone.substring(7, 11);
        } else if (phone.length() == 9){
            return "0" + phone.substring(0, 1) + " " + phone.substring(1, 5) + "-" + phone.substring(5, 9);
        } else if (phone.length() == 8){
            return "0" + phone.substring(0, 1) + " " + phone.substring(1, 4) + "-" + phone.substring(4, 8);
        }
        return null;
    }
}
