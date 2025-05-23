package acad_events.acadevents.utils;

public class ValidatorInputs {
    public static boolean isValidCPF(String cpf){
        if(cpf == null) return false;

        // Verifica a formatação inserida pelo usuário
        if(!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")) return false;

        // Remoção de caracteres especiais
        String digits = cpf.replaceAll("[^\\d]", "");

        // Verifica se todos os dígitos são iguais
        if(digits.chars().distinct().count()==1) return false;

        // Cálculo dos dígitos verificadores
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

    public static boolean isValidEmail(String email){
        if(email==null)
        if (email == null) return false;
        return email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$");
    }

    public static boolean isValidPhone(String phone){
        if (phone == null) return false;
        // Aceita (99) 99999-9999 ou (99) 9999-9999
        return phone.matches("\\(\\d{2}\\) \\d{4,5}-\\d{4}");
    }
}
