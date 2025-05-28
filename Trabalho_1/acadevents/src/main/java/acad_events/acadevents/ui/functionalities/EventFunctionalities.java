package acad_events.acadevents.ui.functionalities;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import acad_events.acadevents.common.DTOs.eventDTOs.CourseDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.EventDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.FairDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.LectureDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.WorkshopDTO;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.EventAttribute;
import acad_events.acadevents.common.utils.enums.EventType;
import acad_events.acadevents.models.event.EventController;
import acad_events.acadevents.ui.functionalities.enums.EventReportOption;
import acad_events.acadevents.ui.functionalities.enums.EventWayToRemoveOption;
import acad_events.acadevents.ui.functionalities.enums.InputResult;
import acad_events.acadevents.ui.functionalities.enums.YesOrNoOption;
import acad_events.acadevents.ui.functionalities.forms.BaseForm;
import acad_events.acadevents.ui.functionalities.forms.EventForms.CourseForm;
import acad_events.acadevents.ui.functionalities.forms.EventForms.EventForm;
import acad_events.acadevents.ui.functionalities.forms.EventForms.FairForm;
import acad_events.acadevents.ui.functionalities.forms.EventForms.LectureForm;
import acad_events.acadevents.ui.functionalities.forms.EventForms.WorkshopForm;

public class EventFunctionalities {

    private final EventController eventController;

    public EventFunctionalities(EventController eventController) {
        this.eventController = eventController;
    }

    // cadastrar/criar novo evento (todos os tipos)
    public  boolean create(Scanner scan){

        EventDTO event = new EventDTO();
        // Dados comuns
        if(EventForm.registerTitle(scan, event) == InputResult.CANCELLED) return false;
        if(EventForm.registerDate(scan, event) == InputResult.CANCELLED) return false;

        // Verifica duplicidade
        if(eventController.existsEventByTitleAndDate(event.getTitle(), event.getDate())) {
            TextBoxUtils.printTitle("An event with this title and date already exists!");
            return false;
        }

        if(EventForm.registerLocation(scan, event) == InputResult.CANCELLED) return false;
        if(EventForm.registerCapacity(scan, event) == InputResult.CANCELLED) return false;
        if(EventForm.registerDescription(scan, event) == InputResult.CANCELLED) return false;
        if(EventForm.selectModality(scan, event) == InputResult.CANCELLED) return false;

        EventType type = EventForm.selectType(scan);

        switch(type){
            case COURSE:
                CourseDTO course = new CourseDTO(event);
                if(CourseForm.registerCoordinator(scan, course) == InputResult.CANCELLED) return false;
                if(CourseForm.registerKnowledgeArea(scan, course) == InputResult.CANCELLED) return false;
                if(CourseForm.registerTotalHours(scan, course) == InputResult.CANCELLED) return false;
                eventController.create(course);
                break;
            case LECTURE:
                LectureDTO lecture = new LectureDTO(event);
                if(LectureForm.registerSpeaker(scan, lecture) == InputResult.CANCELLED) return false;
                eventController.create(lecture);
                break;
            case FAIR:
                FairDTO fair = new FairDTO(event);
                if(FairForm.registerOrganizer(scan, fair) == InputResult.CANCELLED) return false;
                if(FairForm.registerNumberOfStands(scan, fair) == InputResult.CANCELLED) return false;
                eventController.create(fair);
                break;
            case WORKSHOP:
                WorkshopDTO workshop = new WorkshopDTO(event);
                if(WorkshopForm.registerInstructor(scan, workshop) == InputResult.CANCELLED) return false;
                if(WorkshopForm.registerDurationHours(scan, workshop) == InputResult.CANCELLED) return false;
                eventController.create(workshop);
                break;
            case CANCELLED:
                return false;
        }
        
        return true;
    }

    // Remoção pode ser realizada por ID, a partir da lista geral ou a partir de um atributo
    public  boolean remove(Scanner scan) {
        EventWayToRemoveOption option = EventForm.selectWayToRemove(scan);

        switch (option) {
            case ALL_LIST: {
                List<EventDTO> allEvents = (List<EventDTO>) eventController.listAll();
                if (allEvents.isEmpty()) {
                    TextBoxUtils.printTitle("No events found.");
                    return false;
                }
                EventDTO dtoToRemove = EventForm.selectEvent(scan, allEvents);
                if (dtoToRemove == null) return false;
                boolean removed = eventController.delete(dtoToRemove.getId());
                if (removed) {
                    TextBoxUtils.printTitle("Event removed successfully.");
                    return true;
                } else {
                    TextBoxUtils.printTitle("Failed to remove event.");
                    return false;
                }
            }
            case ATTRIBUTE_LIST: {
                String valueSearch = null;
                EventAttribute attribute = EventForm.selectAttribute(scan);
                if (attribute == EventAttribute.CANCELLED) return false;
                if (attribute == EventAttribute.MODALITY) {
                    EventDTO dto = new EventDTO();
                    if (EventForm.selectModality(scan, dto) == InputResult.CANCELLED) return false;
                    valueSearch = dto.getModality().toString();
                } else {
                    valueSearch = TextBoxUtils.inputLine(scan, "Enter the value for " + attribute.getDescription() + " or 'cancel': ");
                    if ("cancel".equalsIgnoreCase(valueSearch)) return false;
                }
                List<EventDTO> filtered = eventController.listByAttribute(attribute, valueSearch);
                if (filtered.isEmpty()) {
                    TextBoxUtils.printTitle("No events found for the given attribute.");
                    return false;
                }
                EventDTO dtoToRemove = EventForm.selectEvent(scan, filtered);
                if (dtoToRemove == null) return false;
                boolean removed = eventController.delete(dtoToRemove.getId());
                if (removed) {
                    TextBoxUtils.printTitle("Event removed successfully.");
                    return true;
                } else {
                    TextBoxUtils.printTitle("Failed to remove event.");
                    return false;
                }
            }
            case ID: {
                String idStr = EventForm.readId(scan);
                if (idStr == null || idStr.isBlank()) return false;
                Long id = Long.parseLong(idStr);
                boolean removed = eventController.delete(id);
                if (removed) {
                    TextBoxUtils.printTitle("Event removed successfully.");
                    return true;
                } else {
                    TextBoxUtils.printTitle("No event found with the given ID.");
                    return false;
                }
            }
            case CANCELLED:
            default:
                return false;
        }
    }

    public void listAll(){
        Collection<EventDTO> events = eventController.listAll();
        if(events.isEmpty()){
            TextBoxUtils.printTitle("No event found.");
            return;
        }
        TextBoxUtils.printTitle("Registered events:");
        for(EventDTO e : events){
            String type = e.getClass().getSimpleName().replace("DTO", ""); // remove DTO from name
            TextBoxUtils.printLeftText(type + ": " + e.getTitle() + " Modality: " + e.getModality() + " Date: " + e.getDate() + " Location: " + e.getLocation());
            TextBoxUtils.printUnderLineDisplayDivisor();
        }
    }

    public void generateReport(Scanner scan, EventReportOption reportOption) {
        List<EventDTO> events;
        String reportedValue;
        switch (reportOption) {
            case DATE:
                String date = EventForm.readDate(scan);
                reportedValue = date;
                if (date == null || date.isBlank()) return;
                events = eventController.listByAttribute(EventAttribute.DATE, date);
                if (events.isEmpty()) {
                    TextBoxUtils.printTitle("No events found for this date.");
                    return;
                }
                break;
            case TYPE:
                EventType typeOption = EventForm.selectType(scan);
                reportedValue = typeOption.getDescription();
                if (typeOption == EventType.CANCELLED) return;
                events = eventController.listByType(typeOption);
                if (events.isEmpty()) {
                    TextBoxUtils.printTitle("No events found for this type.");
                    return;
                }
                break;
            default:
                events = null;
                reportedValue = null;
                TextBoxUtils.printTitle("Report option not implemented.");
                return;
        }
        TextBoxUtils.printTitle(reportOption.getDescription() + " " + reportedValue);
        for (EventDTO e : events) {
            String type = e.getClass().getSimpleName().replace("DTO", "");
            TextBoxUtils.printLeftText(type + ": " + e.getTitle() + " Modality: " + e.getModality() + " Date: " + e.getDate() + " Location: " + e.getLocation());
            TextBoxUtils.printUnderLineDisplayDivisor();
        }

        YesOrNoOption exportOption = BaseForm.selectYesOrNo(scan, "Do you want to export this report?");
        switch (exportOption) {
            case YES:
                try {
                    eventController.exportReportToJson(events, reportOption.getDescription(), reportedValue + ".json");
                    TextBoxUtils.printTitle("Report exported to... 'reports/" + reportOption.getDescription() + "/" + reportedValue + ".json'");
                } catch (Exception e) {
                    TextBoxUtils.printTitle("Error exporting report: " + e.getMessage());
                }
                break;
            case NO:
                return;
        }
    }
}
