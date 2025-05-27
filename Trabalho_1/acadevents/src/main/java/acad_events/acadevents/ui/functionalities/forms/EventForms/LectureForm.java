package acad_events.acadevents.ui.functionalities.forms.EventForms;

import java.util.Scanner;
import acad_events.acadevents.common.DTOs.eventDTOs.LectureDTO;
import acad_events.acadevents.common.utils.Enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

public class LectureForm extends EventForm {

    public static InputResult registerSpeaker(Scanner scan, LectureDTO dto){
        String speaker = readField(
            scan,
            "Enter the speaker of the lecture or 'cancel': ",
            "Speaker must be informed.",
            true, FieldValidatorType.NOT_BLANK
        );
        if (speaker == null) return InputResult.CANCELLED;
        dto.setSpeaker(speaker);
        return InputResult.SUCCESS;
    }
}