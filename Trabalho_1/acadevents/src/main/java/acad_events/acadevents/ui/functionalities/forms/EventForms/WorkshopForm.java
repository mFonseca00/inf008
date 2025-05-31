package acad_events.acadevents.ui.functionalities.forms.EventForms;

import java.util.Scanner;

import acad_events.acadevents.common.dtos.eventdtos.WorkshopDTO;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

public class WorkshopForm extends EventForm {

    public static InputResult registerInstructor(Scanner scan, WorkshopDTO dto){
        String instructor = readField(
            scan,
            "Enter the instructor of the workshop or 'cancel': ",
            "Instructor must be informed.",
            true, FieldValidatorType.NOT_BLANK
        );
        if (instructor == null) return InputResult.CANCELLED;
        dto.setInstructor(instructor);
        return InputResult.SUCCESS;
    }

    public static InputResult registerDurationHours(Scanner scan, WorkshopDTO dto){
        String durationHours = readField(
            scan,
            "Enter the duration in hours of the workshop or 'cancel': ",
            "Duration must be a positive integer.",
            true, FieldValidatorType.POSITIVE_INT
        );
        if (durationHours == null) return InputResult.CANCELLED;
        dto.setDurationHours(Integer.parseInt(durationHours));
        return InputResult.SUCCESS;
    }
}