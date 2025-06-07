package acad_events.acadevents.ui.functionalities.forms.EventForms;

import java.util.Scanner;

import acad_events.acadevents.common.dtos.event.CourseDTO;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

public class CourseForm extends EventForm {

    public static InputResult registerCoordinator(Scanner scan, CourseDTO dto){
        String coordinator = readField(
            scan,
            "Enter the coordinator of the course or 'cancel': ",
            "Coordinator must be informed.",
            true, FieldValidatorType.NOT_BLANK
        );
        if (coordinator == null) return InputResult.CANCELLED;
        dto.setCoordinator(coordinator);
        return InputResult.SUCCESS;        
    }

    public static InputResult registerKnowledgeArea(Scanner scan, CourseDTO dto){
        String knowledgeArea = readField(
            scan,
            "Enter the knowledge area of the course or 'cancel': ",
            "Knowledge area must be informed.",
            true, FieldValidatorType.NOT_BLANK
        );
        if (knowledgeArea == null) return InputResult.CANCELLED;
        dto.setKnowledgeArea(knowledgeArea);
        return InputResult.SUCCESS;        
    }

    public static InputResult registerTotalHours(Scanner scan, CourseDTO dto){
        String totalHours = readField(
            scan,
            "Enter the total hours of the course or 'cancel': ",
            "Total hours must be informed.",
            true, FieldValidatorType.POSITIVE_INT
        );
        if (totalHours == null) return InputResult.CANCELLED;
        dto.setTotalHours(Integer.parseInt(totalHours));
        return InputResult.SUCCESS;        
    }
}