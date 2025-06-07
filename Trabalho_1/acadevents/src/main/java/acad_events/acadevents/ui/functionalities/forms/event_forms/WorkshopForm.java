package acad_events.acadevents.ui.functionalities.forms.event_forms;

import java.util.Scanner;

import acad_events.acadevents.common.dtos.event.WorkshopDTO;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

/**
 * Specialized form class for handling Workshop event type registration in the AcadEvents system.
 * Extends EventForm to inherit common event validation and input handling methods.
 * Manages Workshop-specific attributes: instructor and duration hours for skill-based learning events.
 * 
 * Key features:
 * - Instructor validation with non-blank requirement for workshop leadership identification
 * - Duration validation using positive integer constraints for time management and scheduling
 * - Consistent error handling and cancellation support throughout all input operations
 * - Integration with BaseForm validation patterns and TextBoxUtils for UI consistency
 * 
 * Workshop event attributes:
 * - Instructor: Required string field for workshop leadership (cannot be blank)
 * - Duration Hours: Required positive integer for workshop time planning and scheduling
 * 
 * Business context:
 * - Workshops are hands-on learning events requiring specific time allocation and instructor expertise
 * - Duration hours are essential for scheduling and participant planning
 * - Instructor information is critical for workshop credibility and participant decision-making
 * 
 * Used by: EventFunctionalities during workshop creation workflow
 * Integration: Works with WorkshopDTO for data transfer and follows BaseForm validation patterns
 */
public class WorkshopForm extends EventForm {

    // Instructor input with non-blank validation - essential for workshop leadership and credibility
    public static InputResult registerInstructor(Scanner scan, WorkshopDTO dto){
        String instructor = readField(
            scan,
            "Enter the instructor of the workshop or 'cancel': ",
            "Instructor must be informed.",
            true, // Required field - cannot be empty
            FieldValidatorType.NOT_BLANK
        );
        if (instructor == null) return InputResult.CANCELLED;
        dto.setInstructor(instructor);
        return InputResult.SUCCESS;
    }

    // Duration validation for workshop time management and participant scheduling
    public static InputResult registerDurationHours(Scanner scan, WorkshopDTO dto){
        String durationHours = readField(
            scan,
            "Enter the duration in hours of the workshop or 'cancel': ",
            "Duration must be a positive integer.",
            true, // Required field - essential for scheduling
            FieldValidatorType.POSITIVE_INT
        );
        if (durationHours == null) return InputResult.CANCELLED;
        dto.setDurationHours(Integer.parseInt(durationHours));
        return InputResult.SUCCESS;
    }
}