package acad_events.acadevents.ui.functionalities.forms;

import java.util.Scanner;

import acad_events.acadevents.common.DTOs.ExternalDTO;
import acad_events.acadevents.common.DTOs.ParticipantDTO;
import acad_events.acadevents.common.DTOs.ProfessorDTO;
import acad_events.acadevents.common.DTOs.StudentDTO;
import acad_events.acadevents.common.enums.AcademyDepartment;
import acad_events.acadevents.common.enums.ExternalRole;
import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.ValidatorInputs;
import acad_events.acadevents.ui.functionalities.enums.InputResult;
import acad_events.acadevents.ui.functionalities.enums.ParticipantTypeOption;

public class ParticipantForm {

    public static String readCpfOnly(Scanner scan) {
        while (true) {
            String cpf = TextBoxUtils.inputLine(scan, "Enter participant's CPF (format: 000.000.000-00) or 'cancel': ");
            if ("cancel".equalsIgnoreCase(cpf)) return null;
            if (ValidatorInputs.isValidCPF(cpf)) {
                return cpf;
            }
            TextBoxUtils.printTitle("Invalid CPF.");
        }
    }

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
            dto.setPhone(phone);
            return InputResult.SUCCESS;
        }
    }

    public static InputResult readEnrollment(Scanner scan, StudentDTO dto){
        while (true) {
            String enrollment = TextBoxUtils.inputLine(scan, "Enter student enrollment or 'cancel': ");
            if ("cancel".equalsIgnoreCase(enrollment)) return InputResult.CANCELLED;
            if (enrollment != null && !enrollment.isBlank()) {
                dto.setEnrollment(enrollment);
                return InputResult.SUCCESS;
            }
            TextBoxUtils.printTitle("Enrollment must be filled");
        }
    }

    public static InputResult readEmployeeId(Scanner scan, ProfessorDTO dto){
        while (true) {
            String employeeId = TextBoxUtils.inputLine(scan, "Enter professor's employee ID or 'cancel': ");
            if ("cancel".equalsIgnoreCase(employeeId)) return InputResult.CANCELLED;
            if (employeeId != null && !employeeId.isBlank()) {
                dto.setEmployeeId(employeeId);
                return InputResult.SUCCESS;
            }
            TextBoxUtils.printTitle("Employee ID must be filled");
        }
    }

    public static InputResult readOrg(Scanner scan, ExternalDTO dto){
        while (true) {
            String org = TextBoxUtils.inputLine(scan, "Enter external's organization (leave blank or type 'None' if not part of any) or 'cancel': ");
            if ("cancel".equalsIgnoreCase(org)) return InputResult.CANCELLED;
            if (org == null || org.isBlank() || org.equalsIgnoreCase("None")) {
                dto.setOrganization(null);
                return InputResult.SUCCESS;
            }
            dto.setOrganization(org);
            return InputResult.SUCCESS;
        }
    }

    public static ParticipantTypeOption selectType(Scanner scan){
        ParticipantTypeOption option = null;
        while (option == null) {
            TextBoxUtils.printTitle("Select participant type");
            MenuUtils.listEnumOptions(ParticipantTypeOption.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Please select an option (1-4): ");
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (ParticipantTypeOption type : ParticipantTypeOption.values()) {
                    if (type.getValue() == input) {
                        option = type;
                        break;
                    }
                }
                if (option == null) {
                    TextBoxUtils.printTitle("Invalid option. Please insert a number (1-4).");
                }
            } else {
                TextBoxUtils.printTitle("Invalid input. Please insert a number (1-4).");
            }
        }
        return option;
    }

    public static InputResult selectDepartment(Scanner scan, ProfessorDTO dto){
        while (true) {
            TextBoxUtils.printTitle("Select professor's department");
            MenuUtils.listEnumOptions(AcademyDepartment.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select department (number) or 'cancel': ");
            if ("cancel".equalsIgnoreCase(inputStr)) return InputResult.CANCELLED;
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (AcademyDepartment dep : AcademyDepartment.values()) {
                    if (dep.getValue() == input) {
                        dto.setDepartment(dep);
                        return InputResult.SUCCESS;
                    }
                }
            }
            TextBoxUtils.printTitle("Invalid department. Please select a valid number.");
        }
    }

    public static InputResult selectRole(Scanner scan, ExternalDTO dto){
        while (true) {
            TextBoxUtils.printTitle("Select external's role");
            MenuUtils.listEnumOptions(ExternalRole.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select role (number) or 'cancel': ");
            if ("cancel".equalsIgnoreCase(inputStr)) return InputResult.CANCELLED;
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (ExternalRole role : ExternalRole.values()) {
                    if (role.getValue() == input) {
                        dto.setRole(role);
                        return InputResult.SUCCESS;
                    }
                }
            }
            TextBoxUtils.printTitle("Invalid role. Please select a valid number.");
        }
    }
}
