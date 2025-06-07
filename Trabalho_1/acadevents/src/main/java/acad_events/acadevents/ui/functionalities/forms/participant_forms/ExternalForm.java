package acad_events.acadevents.ui.functionalities.forms.participant_forms;

import java.util.Scanner;

import acad_events.acadevents.common.dtos.participant.ExternalDTO;
import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.models.participant.enums.ExternalRole;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

/**
 * Specialized form class for handling External participant type registration in the AcadEvents system.
 * Extends ParticipantForm to inherit common participant validation and input handling methods.
 * Manages External-specific attributes: organization (optional) and role (required).
 * 
 * Key features:
 * - Optional organization field with flexible input handling (accepts blank, "None", or actual organization)
 * - Role selection from predefined ExternalRole enum with numbered menu display
 * - Consistent error handling and cancellation support throughout all input operations
 * - Integration with BaseForm validation patterns and TextBoxUtils for UI consistency
 * 
 * External participant attributes:
 * - Organization: Optional field, can be null if participant is not affiliated with any organization
 * - Role: Required selection from ExternalRole enum (e.g., Researcher, Industry Professional, etc.)
 * 
 * Used by: ParticipantFunctionalities during external participant registration workflow
 * Integration: Works with ExternalDTO for data transfer and MenuUtils for role selection display
 */
public class ExternalForm extends ParticipantForm {

    // Organization input with flexible handling - supports null values for unaffiliated participants
    public static InputResult registerOrg(Scanner scan, ExternalDTO dto) {
        String org = readField(
            scan,
            "Enter organization (leave blank or type 'None' if not part of any) or 'cancel': ",
            "",
            false, // Not required - allows empty input
            FieldValidatorType.NOT_BLANK
        );
        if (org == null) return InputResult.CANCELLED;
        
        // Flexible organization handling - converts blank or "None" inputs to null for database consistency
        if (org.isBlank() || org.equalsIgnoreCase("None")) {
            dto.setOrganization(null);
        } else {
            dto.setOrganization(org);
        }
        return InputResult.SUCCESS;
    }

    // Role selection using enum-based menu system with numeric input validation
    public static InputResult selectRole(Scanner scan, ExternalDTO dto){
        while (true) {
            TextBoxUtils.printTitle("Select role");
            MenuUtils.listEnumOptions(ExternalRole.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select role or 'cancel': ");
            if ("cancel".equalsIgnoreCase(inputStr)) return InputResult.CANCELLED;
            
            // Numeric input validation and enum value matching
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (ExternalRole role : ExternalRole.values()) {
                    if (role.getValue() == input) {
                        dto.setRole(role);
                        return InputResult.SUCCESS;
                    }
                }
            }
            TextBoxUtils.printError("Invalid role. Please select a valid number.");
        }
    }
}