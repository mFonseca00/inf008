package acad_events.acadevents.ui.functionalities.forms.EventForms;

import java.util.List;
import java.util.Scanner;

import acad_events.acadevents.common.DTOs.eventDTOs.EventDTO;
import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.EventAttribute;
import acad_events.acadevents.common.utils.enums.EventType;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.models.event.enums.Modality;
import acad_events.acadevents.ui.functionalities.enums.EventWayToSelectEventsOption;
import acad_events.acadevents.ui.functionalities.enums.InputResult;
import acad_events.acadevents.ui.functionalities.forms.BaseForm;

public class EventForm extends BaseForm {

    public static String readId(Scanner scan) {
        return readField(
            scan,
            "Enter the ID of the event or 'cancel': ",
            "ID must be a positive integer number.",
            true, FieldValidatorType.POSITIVE_INT);
    }

    public static String readDate(Scanner scan) {
        return readField(
            scan,
            "Enter date (dd/MM/yyyy) or 'cancel': ",
            "Date must be filled and in the format dd/MM/yyyy.",
            true, FieldValidatorType.DATE);
    }

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
            "Enter date (dd/MM/yyyy) or 'cancel': ",
            "Date must be filled and in the format dd/MM/yyyy.",
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

    public static InputResult registerModality(Scanner scan, EventDTO dto) {
        while (true) {
            TextBoxUtils.printTitle("Select an event modality");
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
            TextBoxUtils.printError("Invalid modality. Please select a valid number.");
        }
    }

    public static Modality selectModality(Scanner scan) {
        while (true) {
            TextBoxUtils.printTitle("Select event modality");
            MenuUtils.listEnumOptions(Modality.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select modality or 'cancel': ");
            if ("cancel".equalsIgnoreCase(inputStr)) return null;
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (Modality modality : Modality.values()) {
                    if (modality.getValue() == input) {
                        return modality;
                    }
                }
            }
            TextBoxUtils.printError("Invalid modality. Please select a valid number.");
        }
    }

    public static Modality selectPresentialOrOnlineModality(Scanner scan) {
        while (true) {
            TextBoxUtils.printTitle("Select modality for enrollment: ");
            for (Modality modality : Modality.values()) {
                if (modality != Modality.HYBRID) {
                    TextBoxUtils.printLeftText(modality.getValue() + " - " + modality.getDescription());
                }
            }
            TextBoxUtils.printUnderLineDisplayDivisor();
            String inputStr = TextBoxUtils.inputLine(scan, "Select modality or 'cancel': ");
            if ("cancel".equalsIgnoreCase(inputStr)) return null;
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (Modality modality : Modality.values()) {
                    if (modality.getValue() == input && modality != Modality.HYBRID) {
                        return modality;
                    }
                }
            }
            TextBoxUtils.printError("Invalid modality. Please select a valid number.");
        }
    }

    public static EventType selectType(Scanner scan) {
        while (true) {
            TextBoxUtils.printTitle("Select event type");
            MenuUtils.listEnumOptions(EventType.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select event type or 'cancel': ");
            if ("cancel".equalsIgnoreCase(inputStr)) return EventType.CANCELLED;
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (EventType type : EventType.values()) {
                    if (type.getValue() == input) {
                        return type;
                    }
                }
            }
            TextBoxUtils.printError("Invalid event type. Please select a valid number.");
        }
    }

    public static EventWayToSelectEventsOption selectWayToSelectEvent(Scanner scan, String operation) {
        while (true) {
            TextBoxUtils.printTitle("Select an option to " + operation);
            MenuUtils.listEnumOptions(EventWayToSelectEventsOption.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select an option: ");
            if ("cancel".equalsIgnoreCase(inputStr)) return EventWayToSelectEventsOption.CANCELLED;
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (EventWayToSelectEventsOption option : EventWayToSelectEventsOption.values()) {
                    if (option.getValue() == input) {
                        return option;
                    }
                }
            }
            TextBoxUtils.printError("Invalid option. Please select a valid number.");
        }
    }

    public static EventAttribute selectAttribute(Scanner scan) {
        while (true) {
            TextBoxUtils.printTitle("Select an attribute to remove an event");
            MenuUtils.listEnumOptions(EventAttribute.class);
            String inputStr = TextBoxUtils.inputLine(scan, "Select an option: ");
            if ("cancel".equalsIgnoreCase(inputStr)) return EventAttribute.CANCELLED;
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                for (EventAttribute attribute : EventAttribute.values()) {
                    if (attribute.getValue() == input) {
                        return attribute;
                    }
                }
            }
            TextBoxUtils.printError("Invalid attribute. Please select a valid number.");
        }
    }

    public static EventDTO selectEvent(Scanner scan, List<EventDTO> events) {
        while (true) {
            TextBoxUtils.printTitle("Select an event");
            for (int i = 0; i < events.size(); i++) {
                EventDTO event = events.get(i);
                TextBoxUtils.printLeftText((i + 1) + " - " + event.getClass().getSimpleName() + " " + event.getModality().toString().toLowerCase() + " " + event.getTitle() + " Date: " + event.getDate() + " Location: " + event.getLocation());
            }
            TextBoxUtils.printUnderLineDisplayDivisor();
            String inputStr = TextBoxUtils.inputLine(scan, "Select event number or 'cancel': ");
            if ("cancel".equalsIgnoreCase(inputStr)) return null;
            if (inputStr.matches("\\d+")) {
                int input = Integer.parseInt(inputStr);
                if (input >= 1 && input <= events.size()) {
                    return events.get(input - 1);
                }
            }
            TextBoxUtils.printError("Invalid option. Please select a valid number.");
        }
    }
}
