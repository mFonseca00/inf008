package acad_events.acadevents.ui.functionalities.forms.EventForms;

import java.util.Scanner;

import acad_events.acadevents.common.dtos.event.FairDTO;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

public class FairForm extends EventForm {

    public static InputResult registerOrganizer(Scanner scan, FairDTO dto){
        String organizer = readField(
            scan,
            "Enter the organizer of the fair or 'cancel': ",
            "Organizer must be informed.",
            true, FieldValidatorType.NOT_BLANK
        );
        if (organizer == null) return InputResult.CANCELLED;
        dto.setOrganizer(organizer);
        return InputResult.SUCCESS;
    }

    public static InputResult registerNumberOfStands(Scanner scan, FairDTO dto){
        String numberOfStands = readField(
            scan,
            "Enter the number of stands of the fair or 'cancel': ",
            "Number of stands must be a positive integer.",
            true, FieldValidatorType.POSITIVE_INT
        );
        if (numberOfStands == null) return InputResult.CANCELLED;
        dto.setNumberOfStands(Integer.parseInt(numberOfStands));
        return InputResult.SUCCESS;
    }
}