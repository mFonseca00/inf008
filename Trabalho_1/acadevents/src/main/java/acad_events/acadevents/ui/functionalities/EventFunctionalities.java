package acad_events.acadevents.ui.functionalities;

import java.text.Normalizer;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.EventAttribute;
import acad_events.acadevents.common.utils.enums.EventType;
import acad_events.acadevents.controllers.EventController;
import acad_events.acadevents.controllers.ParticipantController;
import acad_events.acadevents.common.dtos.event.*;
import acad_events.acadevents.common.utils.TestDataGenerator;
import acad_events.acadevents.ui.functionalities.enums.*;
import acad_events.acadevents.ui.functionalities.forms.BaseForm;
import acad_events.acadevents.ui.functionalities.forms.event_forms.*;

/**
 * UI functionality class for event management operations in the AcadEvents system.
 * Extends BaseFunctionalities to inherit shared event selection and controller access.
 * Handles all event-related user interactions including CRUD operations and report generation.
 * 
 * Key features:
 * - Multi-step event creation with type-specific attributes (Course, Lecture, Workshop, Fair)
 * - Polymorphic event handling with different forms for each event type
 * - Unified report generation method supporting both date and type filtering
 * - Automatic file export with organized directory structure and timestamped filenames
 * - Integration with TestDataGenerator for realistic test data creation
 * 
 * Used by: EventMenu for all event management operations
 * Integration: Works with EventController for business logic and EventForm classes for user input
 */
public class EventFunctionalities extends BaseFunctionalities {

    public EventFunctionalities(EventController eventController, ParticipantController participantController) {
        super(eventController, participantController);
    }

    // Multi-step event creation with business rule validation (duplicate title+date prevention)
    public  boolean create(Scanner scan){
        TextBoxUtils.printTitle("Create New Event");
        EventDTO event = new EventDTO();
        if(EventForm.registerTitle(scan, event) == InputResult.CANCELLED) {
            TextBoxUtils.printWarn("Event creation cancelled by user (Title input).");
            return false;
        }
        if(EventForm.registerDate(scan, event) == InputResult.CANCELLED) {
            TextBoxUtils.printWarn("Event creation cancelled by user (Date input).");
            return false;
        }

        if(eventController.existsEventByTitleAndDate(event.getTitle(), event.getDate())) {
            TextBoxUtils.printError("An event with this title and date already exists!");
            return false;
        }

        if(EventForm.registerLocation(scan, event) == InputResult.CANCELLED) {
            TextBoxUtils.printWarn("Event creation cancelled by user (Location input).");
            return false;
        }
        if(EventForm.registerCapacity(scan, event) == InputResult.CANCELLED) {
            TextBoxUtils.printWarn("Event creation cancelled by user (Capacity input).");
            return false;
        }
        if(EventForm.registerDescription(scan, event) == InputResult.CANCELLED) {
            TextBoxUtils.printWarn("Event creation cancelled by user (Description input).");
            return false;
        }
        if(EventForm.registerModality(scan, event) == InputResult.CANCELLED) {
            TextBoxUtils.printWarn("Event creation cancelled by user (Modality selection).");
            return false;
        }

        EventType type = EventForm.selectType(scan);

        // Polymorphic event creation with type-specific attributes and forms
        switch(type){
            case COURSE:
                CourseDTO course = new CourseDTO(event);
                if(CourseForm.registerCoordinator(scan, course) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("Course creation cancelled by user (Coordinator input).");
                    return false;
                }
                if(CourseForm.registerKnowledgeArea(scan, course) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("Course creation cancelled by user (Knowledge Area input).");
                    return false;
                }
                if(CourseForm.registerTotalHours(scan, course) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("Course creation cancelled by user (Total Hours input).");
                    return false;
                }
                eventController.create(course);
                break;
            case LECTURE:
                LectureDTO lecture = new LectureDTO(event);
                if(LectureForm.registerSpeaker(scan, lecture) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("Lecture creation cancelled by user (Speaker input).");
                    return false;
                }
                eventController.create(lecture);
                break;
            case FAIR:
                FairDTO fair = new FairDTO(event);
                if(FairForm.registerOrganizer(scan, fair) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("Fair creation cancelled by user (Organizer input).");
                    return false;
                }
                if(FairForm.registerNumberOfStands(scan, fair) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("Fair creation cancelled by user (Number of Stands input).");
                    return false;
                }
                eventController.create(fair);
                break;
            case WORKSHOP:
                WorkshopDTO workshop = new WorkshopDTO(event);
                if(WorkshopForm.registerInstructor(scan, workshop) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("Workshop creation cancelled by user (Instructor input).");
                    return false;
                }
                if(WorkshopForm.registerDurationHours(scan, workshop) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("Workshop creation cancelled by user (Duration Hours input).");
                    return false;
                }
                eventController.create(workshop);
                break;
            case CANCELLED:
                TextBoxUtils.printWarn("Event type selection cancelled by user.");
                return false;
        }
        TextBoxUtils.printSuccess("Event created successfully!");
        return true;
    }

    // Event deletion using inherited event selection strategies from BaseFunctionalities
    public  boolean remove(Scanner scan) {
        TextBoxUtils.printTitle("Remove Event");
        EventDTO dtoToRemove = selectEventByWay(scan, "remove");
        if (dtoToRemove == null) {
            return false;
        }
        boolean removed = eventController.delete(dtoToRemove.getId());
        if (removed) {
            TextBoxUtils.printSuccess("Event removed successfully.");
            return true;
        } else {
            TextBoxUtils.printError("Failed to remove event.");
            return false;
        }
    }

    // Simple event listing with formatted display showing key information
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

    // Unified report generation method supporting both date and type filtering with export capability
    public void generateReport(Scanner scan, EventReportOption reportOption) {
        List<EventDTO> events;
        String reportedValue;
        switch (reportOption) {
            case DATE:
                String date = EventForm.readDate(scan);
                if (date == null) {
                    TextBoxUtils.printWarn("Date input for report cancelled by user.");
                    return;
                }
                reportedValue = date;
                if (date.isBlank()) {
                    TextBoxUtils.printWarn("Date input cannot be blank for the report.");
                     return;
                }
                events = eventController.listByAttribute(EventAttribute.DATE, date);
                if (events.isEmpty()) {
                    TextBoxUtils.printError("No events found for this date.");
                    return;
                }
                break;
            case TYPE:
                EventType typeOption = EventForm.selectType(scan);
                if (typeOption == EventType.CANCELLED) {
                    TextBoxUtils.printWarn("Event type selection for report cancelled by user.");
                    return;
                }
                reportedValue = typeOption.getDescription();
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

    // File name sanitization for cross-platform compatibility (removes special characters and accents)
    private static String sanitizeFileName(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String withoutAccents = normalized.replaceAll("\\p{M}", "");

        return withoutAccents.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }

    // Test data generation using TestDataGenerator for realistic event creation
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
