package acad_events.acadevents.ui.functionalities.forms.participant_forms;

import java.util.Scanner;

import acad_events.acadevents.common.dtos.participant.ParticipantDTO;
import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.ValidatorInputs;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;
import acad_events.acadevents.ui.functionalities.enums.ParticipantTypeOption;
import acad_events.acadevents.ui.functionalities.forms.BaseForm;

/**
 * Core form class for participant data entry and validation in the AcadEvents system.
 * Extends BaseForm to inherit common validation patterns and user interaction methods.
 * Handles all common participant attributes shared across Student, Professor, and External types.
 * 
 * Key features:
 * - Brazilian document validation with automatic CPF formatting (XXX.XXX.XXX-XX)
 * - Phone number validation supporting multiple Brazilian formats with automatic formatting
 * - Email validation using standard regex patterns
 * - Participant type selection with enum-based menu system
 * - Consistent error handling and cancellation support throughout all input operations
 * 
 * Brazilian compliance:
 * - CPF validation using official algorithm with check digits
 * - Phone formatting supports landline and mobile patterns (XX XXXX-XXXX, XX XXXXX-XXXX)
 * - Automatic formatting ensures consistent data storage regardless of user input format
 * 
 * Used by: All participant-related form classes (StudentForm, ProfessorForm, ExternalForm) and
 * ParticipantFunctionalities for participant registration and management workflows
 * 
 * Integration: Works with ValidatorInputs for data validation, TextBoxUtils for UI consistency,
 * and MenuUtils for standardized option selection menus
 */
public class ParticipantForm extends BaseForm{

    // CPF input with automatic formatting - used for participant lookup and enrollment operations
    public static String readCpf(Scanner scan) {
        String cpfInput = readField(
            scan,
            "Enter CPF (format: xxx.xxx.xxx-xx) or 'cancel': ",
            "Invalid CPF.",
            true, FieldValidatorType.CPF);

        if (cpfInput == null) {
            return null;
        }
        // Automatic formatting ensures consistent storage (XXX.XXX.XXX-XX) regardless of input format
        return ValidatorInputs.formatCPF(cpfInput);
    }

    // CPF registration with Brazilian tax ID validation and automatic formatting
    public static InputResult registerCpf(Scanner scan, ParticipantDTO dto){
        String cpf = readField(
            scan,
            "Enter CPF (format: xxx.xxx.xxx-xx) or 'cancel': ",
            "Invalid CPF.",
            true,
            FieldValidatorType.CPF
        );
        if (cpf == null) return InputResult.CANCELLED;
        dto.setCpf(ValidatorInputs.formatCPF(cpf));
        return InputResult.SUCCESS;
    }

    // Email validation using standard regex patterns for participant communication
    public static InputResult registerEmail(Scanner scan, ParticipantDTO dto){
        String email = readField(
            scan,
            "Enter email or 'cancel': ",
            "Invalid email.",
            true,
            FieldValidatorType.EMAIL
        );
        if (email == null) return InputResult.CANCELLED;
        dto.setEmail(email);
        return InputResult.SUCCESS;
    }

    // Name input with non-blank validation for participant identification
    public static InputResult registerName(Scanner scan, ParticipantDTO dto){
        String name = readField(
            scan,
            "Enter name or 'cancel': ",
            "Name must be filled.",
            true,
            FieldValidatorType.NOT_BLANK
        );
        if (name == null) return InputResult.CANCELLED;
        dto.setName(name);
        return InputResult.SUCCESS;
    }

    // Brazilian phone number validation with automatic formatting supporting multiple patterns
    public static InputResult registerPhone(Scanner scan, ParticipantDTO dto){
        String phone = readField(
            scan,
            "Enter phone (xx xxxx-xxxx, xx xxxxx-xxxx, xxx xxxx-xxxx or xxx xxxxx-xxxx) or 'cancel': ",
            "Invalid phone format.",
            true,
            FieldValidatorType.PHONE
        );
        if (phone == null) return InputResult.CANCELLED;
        // Automatic formatting handles landline and mobile number patterns
        dto.setPhone(ValidatorInputs.formatPhone(phone));
        return InputResult.SUCCESS;
    }

    // Participant type selection using enum-based menu system with numeric input validation
    public static ParticipantTypeOption selectType(Scanner scan){
        while (true) {
            TextBoxUtils.printTitle("Select participant type");
            MenuUtils.listEnumOptions(ParticipantTypeOption.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select participant type or 'cancel': ");
            if ("cancel".equalsIgnoreCase(inputStr)) return ParticipantTypeOption.CANCELLED;
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (ParticipantTypeOption type : ParticipantTypeOption.values()) {
                    if (type.getValue() == input) {
                        return type;
                    }
                }
            }
            TextBoxUtils.printError("Invalid type. Please select a valid number.");
        }
    }
}
