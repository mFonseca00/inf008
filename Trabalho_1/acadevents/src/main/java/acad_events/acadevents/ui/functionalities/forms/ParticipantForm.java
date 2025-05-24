package acad_events.acadevents.ui.functionalities.forms;

import java.util.Scanner;

import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.ValidatorInputs;
import acad_events.acadevents.ui.functionalities.DTOs.ParticipantDTO;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

public class ParticipantForm {

    public static InputResult readCpf(Scanner scan, ParticipantDTO dto){
        while (true) {
            String cpf = TextBoxUtils.inputLine(scan, "Enter participant's CPF (format: 000.000.000-00) or 'cancel': ");
            if ("cancel".equalsIgnoreCase(cpf)) return InputResult.CANCELLED;
            if (ValidatorInputs.isValidCPF(cpf)) {
                dto.setCpf(cpf);
                return InputResult.SUCCESS;
            }
            TextBoxUtils.printTitle("Invalid CPF.");
        }
    }

    public static InputResult readEmail(Scanner scan, ParticipantDTO dto){
        while (true) {
            String email = TextBoxUtils.inputLine(scan, "Enter participant's email or 'cancel': ");
            if ("cancel".equalsIgnoreCase(email)) return InputResult.CANCELLED;
            if (ValidatorInputs.isValidEmail(email)) {
                dto.setEmail(email);
                return InputResult.SUCCESS;
            }
            TextBoxUtils.printTitle("Invalid email");
        }
    }

    public static InputResult readName(Scanner scan, ParticipantDTO dto){
        while (true) {
            String name = TextBoxUtils.inputLine(scan, "Enter participant's name or 'cancel': ");
            if ("cancel".equalsIgnoreCase(name)) return InputResult.CANCELLED;
            if (name != null && !name.isBlank()) {
                dto.setName(name);
                return InputResult.SUCCESS;
            }
            TextBoxUtils.printTitle("Name must be filled");
        }
    }

    public static InputResult readPhone(Scanner scan, ParticipantDTO dto){
        while (true) {
            String phone = TextBoxUtils.inputLine(scan, "Enter participant's phone (format: 00 0000-0000 or 000 00000-0000) or 'cancel': ");
            if ("cancel".equalsIgnoreCase(phone)) return InputResult.CANCELLED;
            if (ValidatorInputs.isValidPhone(phone)) {
                dto.setPhone(phone);
                return InputResult.SUCCESS;
            }
            TextBoxUtils.printTitle("Enter the phone in the correct format");
        }
    }
}
