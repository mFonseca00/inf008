package acad_events.acadevents.ui.functionalities.forms.participant_forms;

import java.util.Scanner;

import acad_events.acadevents.common.dtos.participant.StudentDTO;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

/**
 * Specialized form class for handling Student participant type registration in the AcadEvents system.
 * Extends ParticipantForm to inherit common participant validation and input handling methods.
 * Manages Student-specific attribute: enrollment number (required for institutional identification).
 * 
 * Key features:
 * - Enrollment validation with non-blank requirement for student identification
 * - Consistent error handling and cancellation support throughout input operations
 * - Integration with BaseForm validation patterns and TextBoxUtils for UI consistency
 * - Simplest form among participant types with only one additional required field
 * 
 * Student participant attributes:
 * - Enrollment: Required string field for student institutional identification (cannot be blank)
 * 
 * Business context:
 * - Students have special privileges in the system (only students can enroll in courses)
 * - Enrollment number serves as unique institutional identifier within the academic context
 * 
 * Used by: ParticipantFunctionalities during student registration workflow
 * Integration: Works with StudentDTO for data transfer and follows the same patterns as other form classes
 */
public class StudentForm extends ParticipantForm {

    // Enrollment input with non-blank validation - essential for student institutional identification
    public static InputResult registerEnrollment(Scanner scan, StudentDTO dto){
        String enrollment = readField(
            scan,
            "Enter enrollment or 'cancel': ",
            "Enrollment must be filled.",
            true, // Required field - cannot be empty
            FieldValidatorType.NOT_BLANK
        );
        if (enrollment == null) return InputResult.CANCELLED;
        dto.setEnrollment(enrollment);
        return InputResult.SUCCESS;
    }
}