package acad_events.acadevents.ui.functionalities;

import java.util.List;
import java.util.Scanner;

import acad_events.acadevents.common.dtos.event.EventDTO;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.EventAttribute;
import acad_events.acadevents.controllers.EventController;
import acad_events.acadevents.controllers.ParticipantController;
import acad_events.acadevents.models.event.enums.Modality;
import acad_events.acadevents.ui.functionalities.enums.EventWayToSelectEventsOption;
import acad_events.acadevents.ui.functionalities.forms.event_forms.EventForm;

/**
 * Abstract base class for all functionality classes in the AcadEvents system.
 * Provides shared dependencies and common operations for event and participant management.
 * Implements the Template Method pattern for event selection workflows.
 * 
 * Key features:
 * - Dependency injection for EventController and ParticipantController
 * - Centralized event selection logic with multiple search strategies
 * - Consistent error handling and user feedback across all functionality classes
 * - Supports event selection by: full list, attribute filtering, or direct ID lookup
 * 
 * Extended by: EventFunctionalities, ParticipantFunctionalities, and IntegrationFunctionalities
 * Used in: Event deletion, participant enrollment, certificate generation workflows
 */
public abstract class BaseFunctionalities {
    // Core controllers injected to all functionality classes for data operations
    protected final EventController eventController;
    protected final ParticipantController participantController;

    protected BaseFunctionalities(EventController eventController, ParticipantController participantController) {
        this.eventController = eventController;
        this.participantController = participantController;
    }

    // Template method providing three strategies for event selection across the system
    protected EventDTO selectEventByWay(Scanner scan, String operation) {
        EventWayToSelectEventsOption option = EventForm.selectWayToSelectEvent(scan, operation);
        List<EventDTO> filteredEvents = null;
        EventDTO selectedEvent = null;

        switch (option) {
            case ALL_LIST: {
                // Strategy 1: Select from complete event list (used for general operations)
                List<EventDTO> allEvents = (List<EventDTO>) eventController.listAll();
                if (allEvents.isEmpty()) {
                    TextBoxUtils.printError("No events found.");
                    return null;
                }
                selectedEvent = EventForm.selectEvent(scan, allEvents);
                if (selectedEvent == null) {
                    TextBoxUtils.printWarn("Event selection from list cancelled by user.");
                }
                break;
            }
            case ATTRIBUTE_LIST: {
                // Strategy 2: Filtered search by event attributes (title, date, location, modality)
                String valueSearch = null;
                EventAttribute attribute = EventForm.selectAttribute(scan);
                if (attribute == EventAttribute.CANCELLED) {
                    TextBoxUtils.printWarn("Attribute selection for event search cancelled by user.");
                    return null;
                }
                if (attribute == EventAttribute.MODALITY) {
                    Modality modality = EventForm.selectModality(scan);
                    if (modality == null) {
                        TextBoxUtils.printWarn("Modality selection for event search cancelled by user.");
                        return null;
                    }
                    valueSearch = modality.toString();
                } else {
                    valueSearch = TextBoxUtils.inputLine(scan, "Enter the value for " + attribute.getDescription() + " or 'cancel': ");
                    if ("cancel".equalsIgnoreCase(valueSearch)) {
                        TextBoxUtils.printWarn("Value input for attribute search cancelled by user.");
                        return null;
                    }
                }
                filteredEvents = eventController.listByAttribute(attribute, valueSearch);
                if (filteredEvents.isEmpty()) {
                    TextBoxUtils.printError("No events found for the given attribute.");
                    return null;
                }
                selectedEvent = EventForm.selectEvent(scan, filteredEvents);
                if (selectedEvent == null) {
                    TextBoxUtils.printWarn("Event selection from attribute list cancelled by user.");
                }
                break;
            }
            case ID: {
                // Strategy 3: Direct ID lookup (fastest method for known event IDs)
                String idStr = EventForm.readId(scan);
                if (idStr == null) {
                    TextBoxUtils.printWarn("Event selection from attribute list cancelled by user.");
                    return null;
                }
                try {
                    Long id = Long.parseLong(idStr);
                    selectedEvent = eventController.getEventById(id);
                    if (selectedEvent == null) {
                        TextBoxUtils.printError("No events found for given ID.");
                    }
                } catch (NumberFormatException e) {
                    TextBoxUtils.printError("Invalid ID.");
                    selectedEvent = null;
                }
                break;
            }
            case CANCELLED:
                TextBoxUtils.printWarn("Event selection method cancelled by user.");
                return null;
            default:
                TextBoxUtils.printWarn("Invalid event selection method.");
                return null;
        }
        return selectedEvent;
    }
}