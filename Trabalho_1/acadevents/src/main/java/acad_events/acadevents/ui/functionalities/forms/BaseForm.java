package acad_events.acadevents.ui.functionalities.forms;

import java.util.Scanner;

import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.ValidatorInputs;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.YesOrNoOption;

public class BaseForm {
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
            TextBoxUtils.printTitle("Invalid response. Please select a valid number.");
        }
    }

    public static int readQuantity(Scanner scan, String prompt) {
        TextBoxUtils.printLeftText(prompt);
        TextBoxUtils.printUnderLineDisplayDivisor();
        return readInt(scan);
    }

    public static int readInt(Scanner scan) {
        while (true) {
            String input = TextBoxUtils.inputLine(scan, "Enter the quantity: ");
            if (input.matches("\\d+")) {
                return Integer.parseInt(input);
            }
            TextBoxUtils.printTitle("Invalid input. Please enter a valid integer.");
        }
    }
}