package acad_events.acadevents.ui.functionalities;

import java.util.List;
import java.util.Scanner;
import acad_events.acadevents.common.DTOs.eventDTOs.EventDTO;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.EventAttribute;
import acad_events.acadevents.models.event.EventController;
import acad_events.acadevents.models.event.entities.enums.Modality;
import acad_events.acadevents.models.participant.ParticipantController;
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
                    TextBoxUtils.printTitle("No events found.");
                    return null;
                }
                selectedEvent = EventForm.selectEvent(scan, allEvents);
                break;
            }
            case ATTRIBUTE_LIST: {
                String valueSearch = null;
                EventAttribute attribute = EventForm.selectAttribute(scan);
                if (attribute == EventAttribute.CANCELLED) return null;
                if (attribute == EventAttribute.MODALITY) {
                    Modality modality = EventForm.selectModality(scan);
                    if (modality == null) return null;
                    valueSearch = modality.toString();
                } else {
                    valueSearch = TextBoxUtils.inputLine(scan, "Enter the value for " + attribute.getDescription() + " or 'cancel': ");
                    if ("cancel".equalsIgnoreCase(valueSearch)) return null;
                }
                filteredEvents = eventController.listByAttribute(attribute, valueSearch);
                if (filteredEvents.isEmpty()) {
                    TextBoxUtils.printError("No events found for the given attribute.");
                    return null;
                }
                selectedEvent = EventForm.selectEvent(scan, filteredEvents);
                break;
            }
            case ID: {
                String idStr = EventForm.readId(scan);
                if (idStr == null || idStr.isBlank()) return null;
                Long id = Long.parseLong(idStr);
                filteredEvents = (List<EventDTO>) eventController.listAll();
                selectedEvent = filteredEvents.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
                if (selectedEvent == null) {
                    TextBoxUtils.printError("No event found with the given ID.");
                    return null;
                }
                break;
            }
            case CANCELLED:
            default:
                return null;
        }
        return selectedEvent;
    }
}