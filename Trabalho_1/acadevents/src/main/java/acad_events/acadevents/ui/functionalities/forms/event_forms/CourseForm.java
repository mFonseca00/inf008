package acad_events.acadevents.ui.functionalities.forms.event_forms;

import java.util.Scanner;

import acad_events.acadevents.common.dtos.event.CourseDTO;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

/**
 * Specialized form class for handling Course event type registration in the AcadEvents system.
 * Extends EventForm to inherit common event validation and input handling methods.
 * Manages Course-specific attributes: coordinator, knowledge area, and total hours.
 * 
 * Key features:
 * - Coordinator validation with non-blank requirement for course management identification
 * - Knowledge area input with validation for academic categorization
 * - Total hours validation using positive integer constraints for academic hour requirements
 * - Consistent error handling and cancellation support throughout all input operations
 * 
 * Course event attributes:
 * - Coordinator: Required string field for course management (cannot be blank)
 * - Knowledge Area: Required field for academic categorization (e.g., Computer Science, Mathematics)
 * - Total Hours: Required positive integer for academic hour requirements and certification
 * 
 * Business context:
 * - Courses have special enrollment restrictions (only students can enroll in courses)
 * - Total hours are used for academic certification and credit calculation
 * - Knowledge area helps in course categorization and reporting
 * 
 * Used by: EventFunctionalities during course creation workflow
 * Integration: Works with CourseDTO for data transfer and follows BaseForm validation patterns
 */
public class CourseForm extends EventForm {

    // Coordinator input with non-blank validation - essential for course management identification
    public static InputResult registerCoordinator(Scanner scan, CourseDTO dto){
        String coordinator = readField(
            scan,
            "Enter the coordinator of the course or 'cancel': ",
            "Coordinator must be informed.",
            true, // Required field - cannot be empty
            FieldValidatorType.NOT_BLANK
        );
        if (coordinator == null) return InputResult.CANCELLED;
        dto.setCoordinator(coordinator);
        return InputResult.SUCCESS;        
    }

    // Knowledge area input for academic categorization and course classification
    public static InputResult registerKnowledgeArea(Scanner scan, CourseDTO dto){
        String knowledgeArea = readField(
            scan,
            "Enter the knowledge area of the course or 'cancel': ",
            "Knowledge area must be informed.",
            true, // Required field - needed for course categorization
            FieldValidatorType.NOT_BLANK
        );
        if (knowledgeArea == null) return InputResult.CANCELLED;
        dto.setKnowledgeArea(knowledgeArea);
        return InputResult.SUCCESS;        
    }

    // Total hours validation for academic hour requirements and certification purposes
    public static InputResult registerTotalHours(Scanner scan, CourseDTO dto){
        String totalHours = readField(
            scan,
            "Enter the total hours of the course or 'cancel': ",
            "Total hours must be a positive integer number.",
            true, // Required field - essential for academic credit calculation
            FieldValidatorType.POSITIVE_INT
        );
        if (totalHours == null) return InputResult.CANCELLED;
        dto.setTotalHours(Integer.parseInt(totalHours));
        return InputResult.SUCCESS;        
    }
}