package acad_events.acadevents.ui.functionalities.forms.EventForms;

import java.util.Scanner;

import acad_events.acadevents.common.DTOs.eventDTOs.EventDTO;
import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.Enums.EventAttribute;
import acad_events.acadevents.common.utils.Enums.FieldValidatorType;
import acad_events.acadevents.ui.functionalities.enums.EventTypeOption;
import acad_events.acadevents.ui.functionalities.enums.InputResult;
import acad_events.acadevents.ui.functionalities.forms.BaseForm;
import acad_events.acadevents.models.event.entities.enums.Modality;

public class EventForm extends BaseForm {

    public static InputResult registerTitle(Scanner scan, EventDTO dto){
        String title = readField(
            scan,
            "Enter title or 'cancel': ",
            "Title must be filled.",
            true, FieldValidatorType.NOT_BLANK
        );
        if (title == null) return InputResult.CANCELLED;
        dto.setTitle(title);
        return InputResult.SUCCESS;        
    }

    public static InputResult registerDate(Scanner scan, EventDTO dto){
        String date = readField(
            scan,
            "Enter date or 'cancel': ",
            "Date must be filled.",
            true, FieldValidatorType.DATE
        );
        if (date == null) return InputResult.CANCELLED;
        dto.setDate(date);
        return InputResult.SUCCESS;        
    }

    public static InputResult registerLocation(Scanner scan, EventDTO dto){
        String location = readField(
            scan,
            "Enter location or 'cancel': ",
            "Location must be filled.",
            true, FieldValidatorType.NOT_BLANK
        );
        if (location == null) return InputResult.CANCELLED;
        dto.setLocation(location);
        return InputResult.SUCCESS;        
    }

    public static InputResult registerCapacity(Scanner scan, EventDTO dto){
        String capacity = readField(
            scan,
            "Enter capacity (positive integer) or 'cancel': ",
            "Capacity must be a positive integer.",
            true,
            FieldValidatorType.POSITIVE_INT
        );
        if (capacity == null) return InputResult.CANCELLED;
        dto.setCapacity(Integer.parseInt(capacity));
        return InputResult.SUCCESS;        
    }

    public static InputResult registerDescription(Scanner scan, EventDTO dto){
        String description = readField(
            scan,
            "Enter description or 'cancel': ",
            "Description must be filled.",
            true, FieldValidatorType.NOT_BLANK
        );
        if (description == null) return InputResult.CANCELLED;
        dto.setDescription(description);
        return InputResult.SUCCESS;        
    }

    public static InputResult selectModality(Scanner scan, EventDTO dto){
        while (true) {
            TextBoxUtils.printTitle("Select event modality");
            MenuUtils.listEnumOptions(Modality.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select modality or 'cancel': ");
            if ("cancel".equalsIgnoreCase(inputStr)) return InputResult.CANCELLED;
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (Modality modality : Modality.values()) {
                    if (modality.getValue() == input) {
                        dto.setModality(modality);
                        return InputResult.SUCCESS;
                    }
                }
            }
            TextBoxUtils.printTitle("Invalid modality. Please select a valid number.");
        }
    }

    public static EventTypeOption selectType(Scanner scan){
        while (true) {
            TextBoxUtils.printTitle("Select event type");
            MenuUtils.listEnumOptions(EventTypeOption.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select event type or 'cancel': ");
            if ("cancel".equalsIgnoreCase(inputStr)) return EventTypeOption.CANCELLED;
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (EventTypeOption type : EventTypeOption.values()) {
                    if (type.getValue() == input) {
                        return type;
                    }
                }
            }
            TextBoxUtils.printTitle("Invalid event type. Please select a valid number.");
        }
    }

    public static EventAttribute selectAttribute(Scanner scan){
        while (true) {
            TextBoxUtils.printTitle("Select an event attribute to list");
            MenuUtils.listEnumOptions(EventAttribute.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select attribute or 'cancel': ");
            if ("cancel".equalsIgnoreCase(inputStr)) return EventAttribute.CANCELLED;
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (EventAttribute attribute : EventAttribute.values()) {
                    if (attribute.getValue() == input) {
                        return attribute;
                    }
                }
            }
            TextBoxUtils.printTitle("Invalid attribute. Please select a valid number.");
        }
    }
}
