package acad_events.acadevents.ui.functionalities.forms.ParticipantForms;

import java.util.Scanner;
import acad_events.acadevents.common.DTOs.participantDTOs.ExternalDTO;
import acad_events.acadevents.models.participant.entities.enums.ExternalRole;
import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.Enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

public class ExternalForm extends ParticipantForm {

    public static InputResult registerOrg(Scanner scan, ExternalDTO dto) {
        String org = readField(
            scan,
            "Enter organization (leave blank or type 'None' if not part of any) or 'cancel': ",
            "",
            false,
            FieldValidatorType.NOT_BLANK
        );
        if (org == null) return InputResult.CANCELLED;
        if (org.isBlank() || org.equalsIgnoreCase("None")) {
            dto.setOrganization(null);
        } else {
            dto.setOrganization(org);
        }
        return InputResult.SUCCESS;
    }

    public static InputResult selectRole(Scanner scan, ExternalDTO dto){
        while (true) {
            TextBoxUtils.printTitle("Select role");
            MenuUtils.listEnumOptions(ExternalRole.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select role or 'cancel': ");
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