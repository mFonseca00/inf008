package acad_events.acadevents;

import acad_events.acadevents.controllers.EventController;
import acad_events.acadevents.controllers.IntegrationController;
import acad_events.acadevents.controllers.ParticipantController;
import acad_events.acadevents.repositories.EventRepository;
import acad_events.acadevents.repositories.ParticipantRepository;
import acad_events.acadevents.ui.functionalities.EventFunctionalities;
import acad_events.acadevents.ui.functionalities.IntegrationFunctionalities;
import acad_events.acadevents.ui.functionalities.ParticipantFunctionalities;
import acad_events.acadevents.ui.menu.MenuController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AcadEvents {
    private static final String PARTICIPANTS_FILENAME = "participants.json";
    private static final String EVENTS_FILENAME = "events.json";

    public static void main(String[] args) {
        ParticipantController participantController = initializeParticipants();
        EventController eventController = initializeEvents();
        IntegrationController integrationController = initializeIntegration(participantController, eventController);

        ParticipantFunctionalities partFunctions = new ParticipantFunctionalities(eventController, participantController);
        EventFunctionalities eventFunctions = new EventFunctionalities(eventController, participantController);
        IntegrationFunctionalities integrFunctions = new IntegrationFunctionalities(participantController, eventController, integrationController);

        MenuController menu = new MenuController(partFunctions, eventFunctions, integrFunctions);
        menu.run();

        saveData(participantController.getRepository(), eventController.getRepository());
    }

    private static ParticipantController initializeParticipants() {
        ParticipantController participantController = new ParticipantController();
        ParticipantRepository participantRepository = participantController.getRepository();
        loadDataFromJson(participantRepository, PARTICIPANTS_FILENAME, "participant");
        return participantController;
    }

    private static EventController initializeEvents() {
        EventController eventController = new EventController();
        EventRepository eventRepository = eventController.getRepository();
        loadDataFromJson(eventRepository, EVENTS_FILENAME, "event");
        return eventController;
    }

    private static IntegrationController initializeIntegration(ParticipantController participantController, EventController eventController) {
        ParticipantRepository participantRepository = participantController.getRepository();
        EventRepository eventRepository = eventController.getRepository();
        return new IntegrationController(participantRepository, eventRepository);
    }

    private static void loadDataFromJson(Object repository, String filename, String dataType) {
        File file = new File(filename);
        if (!file.exists()) {
            createEmptyJsonFile(file);
        }
        try {
            if (repository instanceof ParticipantRepository) {
                ((ParticipantRepository) repository).loadFromJson(filename);
            } else if (repository instanceof EventRepository) {
                ((EventRepository) repository).loadFromJson(filename);
            }
        } catch (IOException e) {
            System.out.println("No previous " + dataType + " data found or error loading data: " + e.getMessage());
        }
    }

    private static void createEmptyJsonFile(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("[]");
        } catch (IOException e) {
            System.out.println("Error creating empty JSON file: " + e.getMessage());
        }
    }

    private static void saveData(ParticipantRepository participantRepository, EventRepository eventRepository) {
        try {
            participantRepository.saveToJson(PARTICIPANTS_FILENAME);
        } catch (IOException e) {
            System.out.println("Error saving participant data: " + e.getMessage());
        }
        try {
            eventRepository.saveToJson(EVENTS_FILENAME);
        } catch (IOException e) {
            System.out.println("Error saving event data: " + e.getMessage());
        }
    }
}
