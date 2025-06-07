package acad_events.acadevents.ui.functionalities.forms.event_forms;

import java.util.List;
import java.util.Scanner;

import acad_events.acadevents.common.dtos.event.EventDTO;
import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.EventAttribute;
import acad_events.acadevents.common.utils.enums.EventType;
import acad_events.acadevents.common.utils.enums.FieldValidatorType;
import acad_events.acadevents.models.event.enums.Modality;
import acad_events.acadevents.ui.functionalities.enums.EventWayToSelectEventsOption;
import acad_events.acadevents.ui.functionalities.enums.InputResult;
import acad_events.acadevents.ui.functionalities.forms.BaseForm;

/**
 * Core form class for event data entry and management operations in the AcadEvents system.
 * Extends BaseForm to inherit common validation patterns and user interaction methods.
 * Handles all common event attributes and provides sophisticated event selection mechanisms.
 * 
 * Key features:
 * - Brazilian date format validation (dd/MM/yyyy) for event scheduling
 * - Multi-modality support (Presential, Online, Hybrid) with specialized selection for enrollment
 * - Comprehensive event selection strategies: full list, attribute filtering, or direct ID lookup
 * - Event type selection supporting all academic event types (Course, Lecture, Workshop, Fair)
 * - Capacity validation for venue management and participant enrollment limits
 * 
 * Selection mechanisms:
 * - Template Method pattern implementation used by BaseFunctionalities for consistent event selection
 * - Supports three selection strategies: ALL_LIST, ATTRIBUTE_LIST, and ID-based lookup
 * - Attribute filtering by title, date, location, and modality for targeted event searches
 * - Specialized modality selection excluding HYBRID for participant enrollment operations
 * 
 * Used by: EventFunctionalities for event creation, BaseFunctionalities for event selection,
 * and IntegrationFunctionalities for participant enrollment workflows
 * 
 * Integration: Works with EventDTO for data transfer, MenuUtils for standardized option display,
 * and TextBoxUtils for consistent UI presentation across the system
 */
public class EventForm extends BaseForm {

    // Direct ID input for fast event lookup - used in event deletion and modification workflows
    public static String readId(Scanner scan) {
        return readField(
            scan,
            "Enter the ID of the event or 'cancel': ",
            "ID must be a positive integer number.",
            true, FieldValidatorType.POSITIVE_INT);
    }

    // Brazilian date format input for event scheduling and report generation
    public static String readDate(Scanner scan) {
        return readField(
            scan,
            "Enter date (dd/MM/yyyy) or 'cancel': ",
            "Date must be filled and in the format dd/MM/yyyy.",
            true, FieldValidatorType.DATE);
    }

    // Event title registration with non-blank validation for event identification
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

    // Date registration with Brazilian format validation for academic event scheduling
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

    // Location validation for venue management and event logistics
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

    // Capacity validation for participant enrollment limits and venue management
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

    // Event description for academic content and participant information
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

    // Modality selection for event delivery format - supports Presential, Online, and Hybrid
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

    // General modality selection used in report generation and event filtering
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

    // Specialized modality selection for participant enrollment - excludes HYBRID option
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

    // Event type selection supporting all academic event types with polymorphic handling
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

    // Selection strategy chooser - implements Template Method pattern for BaseFunctionalities
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

    // Attribute selection for filtered event searches - supports title, date, location, modality
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

    // Event selection from list with detailed display - used in deletion, enrollment, and certificate workflows
    public static EventDTO selectEvent(Scanner scan, List<EventDTO> events) {
        while (true) {
            TextBoxUtils.printTitle("Select an event");
            for (int i = 0; i < events.size(); i++) {
                EventDTO event = events.get(i);
                String className = event.getClass().getSimpleName().replace("DTO", "");
                TextBoxUtils.printLeftText((i + 1) + " - " + className + "  "
                                                   + event.getModality().toString().toLowerCase()
                                                   + "  " + event.getTitle()
                                                   + "  Date: " + event.getDate()
                                                   + "  Location: " + event.getLocation());
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
