package acad_events.acadevents.ui.functionalities.forms.participant_forms;

import java.util.Scanner;

import acad_events.acadevents.common.dtos.participant.ProfessorDTO;
import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.models.participant.enums.AcademyDepartment;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

/**
 * Specialized form class for handling Professor participant type registration in the AcadEvents system.
 * Extends ParticipantForm to inherit common participant validation and input handling methods.
 * Manages Professor-specific attributes: employee ID (required) and department (required).
 * 
 * Key features:
 * - Employee ID validation with non-blank requirement for institutional identification
 * - Department selection from predefined AcademyDepartment enum with numbered menu display
 * - Consistent error handling and cancellation support throughout all input operations
 * - Integration with BaseForm validation patterns and TextBoxUtils for UI consistency
 * 
 * Professor participant attributes:
 * - Employee ID: Required string field for institutional identification (cannot be blank)
 * - Department: Required selection from AcademyDepartment enum (e.g., Computer Science, Mathematics, etc.)
 * 
 * Used by: ParticipantFunctionalities during professor registration workflow
 * Integration: Works with ProfessorDTO for data transfer and MenuUtils for department selection display
 */
public class ProfessorForm extends ParticipantForm {

    // Employee ID input with non-blank validation - essential for institutional identification
    public static InputResult registerEmployeeId(Scanner scan, ProfessorDTO dto){
        String employeeId = readField(
            scan,
            "Enter employee ID or 'cancel': ",
            "Employee ID must be filled.",
            true, // Required field - cannot be empty
            FieldValidatorType.NOT_BLANK
        );
        if (employeeId == null) return InputResult.CANCELLED;
        dto.setEmployeeId(employeeId);
        return InputResult.SUCCESS;
    }

    // Department selection using enum-based menu system with numeric input validation
    public static InputResult selectDepartment(Scanner scan, ProfessorDTO dto){
        while (true) {
            TextBoxUtils.printTitle("Select department");
            MenuUtils.listEnumOptions(AcademyDepartment.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select department or 'cancel': ");
            if ("cancel".equalsIgnoreCase(inputStr)) return InputResult.CANCELLED;
            
            // Numeric input validation and enum value matching
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