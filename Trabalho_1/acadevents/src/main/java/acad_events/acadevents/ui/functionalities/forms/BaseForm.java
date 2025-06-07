package acad_events.acadevents.ui.functionalities.forms;

import java.util.Scanner;

import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.ValidatorInputs;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.YesOrNoOption;

/**
 * Base class providing common form functionality for all user input forms in the AcadEvents system.
 * Implements standardized field validation, error handling, and user interaction patterns.
 * Serves as foundation for all specialized form classes to ensure consistent user experience.
 * 
 * Key features:
 * - Generic field validation with Brazilian document format support (CPF validation)
 * - Standardized error handling with retry loops for invalid inputs
 * - Cancellation support throughout all input operations ("cancel" keyword)
 * - Yes/No confirmation dialogs with enum-based option selection
 * - Integer input validation for quantities and numeric fields
 * 
 * Used by: All form classes including EventForm, ParticipantForm, StudentForm, ProfessorForm, etc.
 * Integration: Works with ValidatorInputs for data validation and TextBoxUtils for consistent UI presentation
 */
public class BaseForm {
    
    // Core field validation method supporting multiple validation types and Brazilian document formats
    protected static String readField(Scanner scan, String prompt, String errorMsg, boolean required, FieldValidatorType validatorType) {
        while (true) {
            String input = TextBoxUtils.inputLine(scan, prompt);
            
            // Global cancellation support - allows users to back out of any input operation
            if ("cancel".equalsIgnoreCase(input)) return null;
            
            // Optional field handling - empty inputs allowed for non-required fields
            if (!required && (input == null || input.isBlank())) return "";
            
            boolean valid;
            switch (validatorType) {
                case CPF:
                    // Brazilian tax ID validation with official algorithm
                    valid = ValidatorInputs.isValidCPF(input);
                    break;
                case EMAIL:
                    valid = ValidatorInputs.isValidEmail(input);
                    break;
                case PHONE:
                    // Brazilian phone number format validation
                    valid = ValidatorInputs.isValidPhone(input);
                    break;
                case NOT_BLANK:
                    valid = input != null && !input.isBlank();
                    break;
                case POSITIVE_INT:
                    // Capacity, hours, and quantity validation
                    valid = ValidatorInputs.isPositiveInteger(input);
                    break;
                case DATE:
                    // Brazilian date format (dd/MM/yyyy) validation
                    valid = ValidatorInputs.isValidDate(input);
                    break;
                default:
                    valid = true;
            }
            
            if (valid) return input;
            TextBoxUtils.printError(errorMsg);
        }
    }

    // Standardized Yes/No confirmation dialog used throughout the system for critical operations
    public static YesOrNoOption selectYesOrNo(Scanner scan, String label){
        while (true) {
            TextBoxUtils.printTitle(label);
            MenuUtils.listEnumOptions(YesOrNoOption.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select yes or no: ");
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (YesOrNoOption response : YesOrNoOption.values()) {
                    if (response.getValue() == input) {
                        return response;
                    }
                }
            }
            TextBoxUtils.printError("Invalid response. Please select a valid number.");
        }
    }

    // Quantity input validation for test data generation and capacity settings
    public static int readQuantity(Scanner scan, String prompt) {
        TextBoxUtils.printLeftText(prompt);
        TextBoxUtils.printUnderLineDisplayDivisor();
        Integer quantity = readInt(scan);
        if (quantity == null) {
            return -1; // Indicates cancellation
        }
        return quantity;
    }

    // Generic integer input with cancellation support and validation
    public static Integer readInt(Scanner scan) {
        while (true) {
            String input = TextBoxUtils.inputLine(scan, "Enter the quantity or type 'cancel' to cancel: ");
            if ("cancel".equalsIgnoreCase(input)) return null;
            if (input.matches("\\d+")) return Integer.parseInt(input);
            TextBoxUtils.printError("Invalid input. Please enter a valid integer or type 'cancel' to cancel.");
        }
    }
}