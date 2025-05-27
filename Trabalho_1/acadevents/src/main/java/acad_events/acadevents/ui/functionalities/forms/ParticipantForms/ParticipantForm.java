package acad_events.acadevents.ui.functionalities.forms.ParticipantForms;

import java.util.Scanner;

import acad_events.acadevents.common.DTOs.participantDTOs.ParticipantDTO;
import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;
import acad_events.acadevents.ui.functionalities.enums.ParticipantTypeOption;
import acad_events.acadevents.ui.functionalities.forms.BaseForm;

public class ParticipantForm extends BaseForm{

    public static String readCpf(Scanner scan) {
        return readField(
            scan,
            "Enter CPF (format: xxx.xxx.xxx-xx) or 'cancel': ",
            "Invalid CPF.",
            true, FieldValidatorType.CPF);
    }

    public static InputResult registerCpf(Scanner scan, ParticipantDTO dto){
        String cpf = readField(
            scan,
            "Enter CPF (format: xxx.xxx.xxx-xx) or 'cancel': ",
            "Invalid CPF.",
            true,
            FieldValidatorType.CPF
        );
        if (cpf == null) return InputResult.CANCELLED;
        dto.setCpf(cpf);
        return InputResult.SUCCESS;
    }

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

    public static InputResult registerPhone(Scanner scan, ParticipantDTO dto){
        String phone = readField(
            scan,
            "Enter phone (xx xxxx-xxxx, xx xxxxx-xxxx, xxx xxxx-xxxx or xxx xxxxx-xxxx) or 'cancel': ",
            "Invalid phone format.",
            true,
            FieldValidatorType.PHONE
        );
        if (phone == null) return InputResult.CANCELLED;
        dto.setPhone(phone);
        return InputResult.SUCCESS;
    }

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
            TextBoxUtils.printTitle("Invalid type. Please select a valid number.");
        }
    }
}
