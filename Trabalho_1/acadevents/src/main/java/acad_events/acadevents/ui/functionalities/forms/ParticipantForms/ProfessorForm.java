package acad_events.acadevents.ui.functionalities.forms.ParticipantForms;

import java.util.Scanner;
import acad_events.acadevents.common.DTOs.participantDTOs.ProfessorDTO;
import acad_events.acadevents.models.participant.entities.enums.AcademyDepartment;
import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

public class ProfessorForm extends ParticipantForm {

    public static InputResult registerEmployeeId(Scanner scan, ProfessorDTO dto){
        String employeeId = readField(
            scan,
            "Enter employee ID or 'cancel': ",
            "Employee ID must be filled.",
            true,
            FieldValidatorType.NOT_BLANK
        );
        if (employeeId == null) return InputResult.CANCELLED;
        dto.setEmployeeId(employeeId);
        return InputResult.SUCCESS;
    }

    public static InputResult selectDepartment(Scanner scan, ProfessorDTO dto){
        while (true) {
            TextBoxUtils.printTitle("Select department");
            MenuUtils.listEnumOptions(AcademyDepartment.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select department or 'cancel': ");
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
            TextBoxUtils.printError("Invalid department. Please select a valid number.");
        }
    }
}