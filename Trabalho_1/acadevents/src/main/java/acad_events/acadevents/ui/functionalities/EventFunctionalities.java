package acad_events.acadevents.ui.functionalities;

import java.text.Normalizer;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import acad_events.acadevents.common.DTOs.eventDTOs.*;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.EventAttribute;
import acad_events.acadevents.common.utils.enums.EventType;
import acad_events.acadevents.controllers.EventController;
import acad_events.acadevents.controllers.ParticipantController;
import acad_events.acadevents.common.utils.TestDataGenerator;
import acad_events.acadevents.ui.functionalities.enums.*;
import acad_events.acadevents.ui.functionalities.forms.BaseForm;
import acad_events.acadevents.ui.functionalities.forms.EventForms.*;

public class EventFunctionalities extends BaseFunctionalities {

    public EventFunctionalities(EventController eventController, ParticipantController participantController) {
        super(eventController, participantController);
    }

    public  boolean create(Scanner scan){

        EventDTO event = new EventDTO();
        if(EventForm.registerTitle(scan, event) == InputResult.CANCELLED) return false;
        if(EventForm.registerDate(scan, event) == InputResult.CANCELLED) return false;

        if(eventController.existsEventByTitleAndDate(event.getTitle(), event.getDate())) {
            TextBoxUtils.printError("An event with this title and date already exists!");
            return false;
        }

        if(EventForm.registerLocation(scan, event) == InputResult.CANCELLED) return false;
        if(EventForm.registerCapacity(scan, event) == InputResult.CANCELLED) return false;
        if(EventForm.registerDescription(scan, event) == InputResult.CANCELLED) return false;
        if(EventForm.registerModality(scan, event) == InputResult.CANCELLED) return false;

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
        TextBoxUtils.printSuccess("Event created successfully!");
        return true;
    }

    public  boolean remove(Scanner scan) {
        EventDTO dtoToRemove = selectEventByWay(scan, "remove");
        if (dtoToRemove == null) return false;
        boolean removed = eventController.delete(dtoToRemove.getId());
        if (removed) {
            TextBoxUtils.printSuccess("Event removed successfully.");
            return true;
        } else {
            TextBoxUtils.printError("Failed to remove event.");
            return false;
        }
    }

    public void listAll(){
        Collection<EventDTO> events = eventController.listAll();
        if(events.isEmpty()){
            TextBoxUtils.printError("No event found.");
            return;
        }
        TextBoxUtils.printTitle("Registered events:");
        for(EventDTO e : events){
            String type = e.getClass().getSimpleName().replace("DTO", ""); 
            TextBoxUtils.printLeftText(type + ": " + e.getTitle() + "   Modality: " + e.getModality().toString().toLowerCase() + "   Date: " + e.getDate() + "   Location: " + e.getLocation());
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
                    TextBoxUtils.printError("No events found for this date.");
                    return;
                }
                break;
            case TYPE:
                EventType typeOption = EventForm.selectType(scan);
                reportedValue = typeOption.getDescription();
                if (typeOption == EventType.CANCELLED) return;
                events = eventController.listByType(typeOption);
                if (events.isEmpty()) {
                    TextBoxUtils.printError("No events found for this type.");
                    return;
                }
                break;
            default:
                events = null;
                reportedValue = null;
                TextBoxUtils.printError("Report option not implemented.");
                return;
        }
        TextBoxUtils.printTitle(reportOption.getDescription() + " " + reportedValue);
        for (EventDTO e : events) {
            String type = e.getClass().getSimpleName().replace("DTO", "");
            TextBoxUtils.printLeftText(type + ": " + e.getTitle() + "   Modality: " + e.getModality() + "   Date: " + e.getDate() + "   Location: " + e.getLocation());
            TextBoxUtils.printUnderLineDisplayDivisor();
        }

        YesOrNoOption exportOption = BaseForm.selectYesOrNo(scan, "Do you want to export this report?");
        switch (exportOption) {
            case YES:
                try {
                    String sanitizedFileName = sanitizeFileName(reportedValue) + ".json";
                    String filePath = eventController.exportReportToJson(events, reportOption.getDescription(), sanitizedFileName);
                    TextBoxUtils.printSuccess("Report exported to... 'reports/" + filePath + "'");
                } catch (Exception e) {
                    TextBoxUtils.printError("Error exporting report: " + e.getMessage());
                }
                break;
            case NO:
                return;
        }
    }

    private static String sanitizeFileName(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String withoutAccents = normalized.replaceAll("\\p{M}", "");

        return withoutAccents.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }

    public void generateRandomEvent(Scanner scan) {
        TextBoxUtils.printTitle("Generating test data...");
        int quantity = BaseForm.readQuantity(scan, "How many events do you want to generate?");
        if (quantity == -1) {
            TextBoxUtils.printWarn("Event generation cancelled.");
            return;
        }
        if (quantity > 0) {
            for (int i = 0; i < quantity; i++) {
                TestDataGenerator.generateRandomEvent(eventController);
            }
            TextBoxUtils.printSuccess(quantity + " events generated successfully!");
        } else {
            TextBoxUtils.printWarn("No events were generated.");
        }
    }
}
