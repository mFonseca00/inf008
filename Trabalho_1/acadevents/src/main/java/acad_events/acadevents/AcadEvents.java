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

/**
 * Main class and entry point for the AcadEvents system.
 * Responsible for application bootstrapping, dependency injection, and data persistence lifecycle.
 * Coordinates the initialization of all system components and manages JSON file operations.
 * 
 * Key responsibilities:
 * - Initializes the complete dependency injection chain (repositories → controllers → functionalities → menu)
 * - Manages JSON file persistence with automatic file creation for first-time runs
 * - Ensures data consistency by loading data on startup and saving on shutdown
 * - Provides graceful error handling for file operations
 * 
 * Application flow:
 * 1. Load existing data from JSON files (participants.json, events.json)
 * 2. Initialize all system components with proper dependency injection
 * 3. Run the main menu controller for user interaction
 * 4. Save all data back to JSON files on application exit
 * 
 * File management:
 * - Creates empty JSON files if they don't exist for first-time users
 * - Maintains data persistence across application restarts
 * - Handles JSON loading/saving errors gracefully without crashing the system
 */
public class AcadEvents {
    // JSON file constants for data persistence
    private static final String PARTICIPANTS_FILENAME = "participants.json";
    private static final String EVENTS_FILENAME = "events.json";

    // Application entry point with complete lifecycle management
    public static void main(String[] args) {
        // Dependency injection chain: repositories → controllers → functionalities
        ParticipantController participantController = initializeParticipants();
        EventController eventController = initializeEvents();
        IntegrationController integrationController = initializeIntegration(participantController, eventController);

        // UI functionality classes with injected dependencies
        ParticipantFunctionalities partFunctions = new ParticipantFunctionalities(eventController, participantController);
        EventFunctionalities eventFunctions = new EventFunctionalities(eventController, participantController);
        IntegrationFunctionalities integrFunctions = new IntegrationFunctionalities(participantController, eventController, integrationController);

        // Main application controller with all functionality classes
        MenuController menu = new MenuController(partFunctions, eventFunctions, integrFunctions);
        menu.run();

        // Data persistence on application shutdown
        saveData(participantController.getRepository(), eventController.getRepository());
    }

    // Participant system initialization with JSON data loading
    private static ParticipantController initializeParticipants() {
        ParticipantController participantController = new ParticipantController();
        ParticipantRepository participantRepository = participantController.getRepository();
        loadDataFromJson(participantRepository, PARTICIPANTS_FILENAME, "participant");
        return participantController;
    }

    // Event system initialization with JSON data loading
    private static EventController initializeEvents() {
        EventController eventController = new EventController();
        EventRepository eventRepository = eventController.getRepository();
        loadDataFromJson(eventRepository, EVENTS_FILENAME, "event");
        return eventController;
    }

    // Integration controller setup with repository dependencies
    private static IntegrationController initializeIntegration(ParticipantController participantController, EventController eventController) {
        ParticipantRepository participantRepository = participantController.getRepository();
        EventRepository eventRepository = eventController.getRepository();
        return new IntegrationController(participantRepository, eventRepository);
    }

    // Generic JSON data loading with polymorphic repository handling and graceful error management
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

    // First-time setup: creates empty JSON array files for new installations
    private static void createEmptyJsonFile(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("[]");
        } catch (IOException e) {
            System.out.println("Error creating empty JSON file: " + e.getMessage());
        }
    }

    // Data persistence with error handling for both participants and events
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
