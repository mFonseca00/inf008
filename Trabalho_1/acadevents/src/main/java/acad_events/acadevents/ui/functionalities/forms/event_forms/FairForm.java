package acad_events.acadevents.ui.functionalities.forms.event_forms;

import java.util.Scanner;

import acad_events.acadevents.common.dtos.event.FairDTO;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

/**
 * Specialized form class for handling Fair event type registration in the AcadEvents system.
 * Extends EventForm to inherit common event validation and input handling methods.
 * Manages Fair-specific attributes: organizer and number of stands for venue management.
 * 
 * Key features:
 * - Organizer validation with non-blank requirement for fair management identification
 * - Number of stands validation using positive integer constraints for venue organization
 * - Consistent error handling and cancellation support throughout all input operations
 * - Integration with BaseForm validation patterns and TextBoxUtils for UI consistency
 * 
 * Fair event attributes:
 * - Organizer: Required string field for fair management and coordination (cannot be blank)
 * - Number of Stands: Required positive integer for venue organization and space allocation
 * 
 * Business context:
 * - Fairs are typically large events with multiple exhibitors requiring organized stand allocation
 * - Number of stands helps in venue planning and participant capacity management
 * - Organizer information is essential for event coordination and management
 * 
 * Used by: EventFunctionalities during fair creation workflow
 * Integration: Works with FairDTO for data transfer and follows BaseForm validation patterns
 */
public class FairForm extends EventForm {

    // Organizer input with non-blank validation - essential for fair management and coordination
    public static InputResult registerOrganizer(Scanner scan, FairDTO dto){
        String organizer = readField(
            scan,
            "Enter the organizer of the fair or 'cancel': ",
            "Organizer must be informed.",
            true, // Required field - cannot be empty
            FieldValidatorType.NOT_BLANK
        );
        if (organizer == null) return InputResult.CANCELLED;
        dto.setOrganizer(organizer);
        return InputResult.SUCCESS;
    }

    // Number of stands validation for venue organization and space allocation planning
    public static InputResult registerNumberOfStands(Scanner scan, FairDTO dto){
        String numberOfStands = readField(
            scan,
            "Enter the number of stands of the fair or 'cancel': ",
            "Number of stands must be a positive integer.",
            true, // Required field - essential for venue planning
            FieldValidatorType.POSITIVE_INT
        );
        if (numberOfStands == null) return InputResult.CANCELLED;
        dto.setNumberOfStands(Integer.parseInt(numberOfStands));
        return InputResult.SUCCESS;
    }
}