package acad_events.acadevents.ui.functionalities.forms.ParticipantForms;

import java.util.Scanner;

import acad_events.acadevents.common.dtos.participant.StudentDTO;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.InputResult;

public class StudentForm extends ParticipantForm {

    public static InputResult registerEnrollment(Scanner scan, StudentDTO dto){
        String enrollment = readField(
            scan,
            "Enter enrollment or 'cancel': ",
            "Enrollment must be filled.",
            true,
            FieldValidatorType.NOT_BLANK
        );
        if (enrollment == null) return InputResult.CANCELLED;
        dto.setEnrollment(enrollment);
        return InputResult.SUCCESS;
    }
}