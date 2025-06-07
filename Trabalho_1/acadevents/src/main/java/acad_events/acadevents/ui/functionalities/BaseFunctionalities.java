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
import acad_events.acadevents.ui.functionalities.forms.EventForms.EventForm;

public abstract class BaseFunctionalities {
    protected final EventController eventController;
    protected final ParticipantController participantController;

    protected BaseFunctionalities(EventController eventController, ParticipantController participantController) {
        this.eventController = eventController;
        this.participantController = participantController;
    }

    protected EventDTO selectEventByWay(Scanner scan, String operation) {
        EventWayToSelectEventsOption option = EventForm.selectWayToSelectEvent(scan, operation);
        List<EventDTO> filteredEvents = null;
        EventDTO selectedEvent = null;

        switch (option) {
            case ALL_LIST: {
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
                String idStr = EventForm.readId(scan);
                if (idStr == null) {
                    TextBoxUtils.printWarn("Event selection from attribute list cancelled by user.");
                    return null;
                }
                try {
                    Long id = Long.parseLong(idStr);
                    selectedEvent = eventController.getEventById(id);
                    if (selectedEvent == null) {
                        TextBoxUtils.printError("Nenhum evento encontrado com o ID fornecido.");
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