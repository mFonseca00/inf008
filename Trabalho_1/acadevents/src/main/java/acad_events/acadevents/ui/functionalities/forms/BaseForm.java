package acad_events.acadevents.ui.functionalities.forms;

import java.util.Scanner;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.ValidatorInputs;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;

public abstract class BaseForm {
    protected static String readField(Scanner scan, String prompt, String errorMsg, boolean required, FieldValidatorType validatorType) {
        while (true) {
            String input = TextBoxUtils.inputLine(scan, prompt);
            if ("cancel".equalsIgnoreCase(input)) return null;
            if (!required && (input == null || input.isBlank())) return "";
            boolean valid;
            switch (validatorType) {
                case CPF:
                    valid = ValidatorInputs.isValidCPF(input);
                    break;
                case EMAIL:
                    valid = ValidatorInputs.isValidEmail(input);
                    break;
                case PHONE:
                    valid = ValidatorInputs.isValidPhone(input);
                    break;
                case NOT_BLANK:
                    valid = input != null && !input.isBlank();
                    break;
                case POSITIVE_INT:
                    valid = ValidatorInputs.isPositiveInteger(input);
                    break;
                case DATE:
                    valid = ValidatorInputs.isValidDate(input);
                    break;
                default:
                    valid = true;
            }
            if (valid) return input;
            TextBoxUtils.printTitle(errorMsg);
        }
    }
}