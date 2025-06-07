package acad_events.acadevents.ui.functionalities.forms.event_forms;

import java.util.Scanner;

import acad_events.acadevents.common.dtos.event.LectureDTO;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

/**
 * Specialized form class for handling Lecture event type registration in the AcadEvents system.
 * Extends EventForm to inherit common event validation and input handling methods.
 * Manages Lecture-specific attribute: speaker (required for event identification and promotion).
 * 
 * Key features:
 * - Speaker validation with non-blank requirement for lecture identification
 * - Simplest event form among all event types with only one additional required field
 * - Consistent error handling and cancellation support throughout input operations
 * - Integration with BaseForm validation patterns and TextBoxUtils for UI consistency
 * 
 * Lecture event attributes:
 * - Speaker: Required string field for lecture presentation (cannot be blank)
 * 
 * Business context:
 * - Lectures are academic presentations typically featuring a single speaker or presenter
 * - Speaker information is essential for event promotion and participant attraction
 * - One of the simplest event types alongside workshops in terms of required attributes
 * 
 * Used by: EventFunctionalities during lecture creation workflow
 * Integration: Works with LectureDTO for data transfer and follows BaseForm validation patterns
 */
public class LectureForm extends EventForm {

    // Speaker input with non-blank validation - essential for lecture identification and promotion
    public static InputResult registerSpeaker(Scanner scan, LectureDTO dto){
        String speaker = readField(
            scan,
            "Enter the speaker of the lecture or 'cancel': ",
            "Speaker must be informed.",
            true, // Required field - cannot be empty
            FieldValidatorType.NOT_BLANK
        );
        if (speaker == null) return InputResult.CANCELLED;
        dto.setSpeaker(speaker);
        return InputResult.SUCCESS;
    }
}