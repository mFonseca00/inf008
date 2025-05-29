package acad_events.acadevents;



import acad_events.acadevents.models.participant.ParticipantController;
import acad_events.acadevents.models.participant.ParticipantRepository;
import acad_events.acadevents.models.event.EventController;
import acad_events.acadevents.models.event.EventRepository;
import acad_events.acadevents.models.integration.IntegrationController;
import acad_events.acadevents.ui.functionalities.ParticipantFunctionalities;
import acad_events.acadevents.ui.functionalities.EventFunctionalities;
import acad_events.acadevents.ui.functionalities.IntegrationFunctionalities;
import acad_events.acadevents.ui.menu.MenuController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AcadEvents 
{
    public static void main(String[] args)
    {
        // PARTICIPANTS
        ParticipantController participantController = new ParticipantController();
        ParticipantRepository participantRepository = participantController.getRepository();

        String participantsFilename = "participants.json";
        File participantsFile = new File(participantsFilename);

        // Garante que o arquivo existe e, se não, cria um arquivo JSON vazio
        if (!participantsFile.exists()) {
            try (FileWriter writer = new FileWriter(participantsFile)) {
                writer.write("[]");
            } catch (IOException e) {
                System.out.println("Error creating empty participants.json file.");
            }
        }

        // Carregando arquivo de dados dos participantes
        try {
            participantRepository.loadFromJson(participantsFilename);
        } catch (IOException e) {
            System.out.println("No previous participant data found or error loading data.");
        }

        // EVENTS
        EventController eventController = new EventController();
        EventRepository eventRepository = eventController.getRepository();

        String eventsFilename = "events.json";
        File eventsFile = new File(eventsFilename);

        // Garante que o arquivo existe e, se não, cria um arquivo JSON vazio
        if (!eventsFile.exists()) {
            try (FileWriter writer = new FileWriter(eventsFile)) {
                writer.write("[]");
            } catch (IOException e) {
                System.out.println("Error creating empty events.json file.");
            }
        }

        // Carregando arquivo de dados dos eventos
        try {
            eventRepository.loadFromJson(eventsFilename);
        } catch (IOException e) {
            System.out.println("No previous event data found or error loading data.");
        }

        // INTEGRATION
        IntegrationController integrationController = new IntegrationController(participantRepository, eventRepository);


        // Instancia funcionalidades com os controllers compartilhados
        ParticipantFunctionalities partFunctions = new ParticipantFunctionalities(eventController, participantController);
        EventFunctionalities eventFunctions = new EventFunctionalities(eventController, participantController);
        IntegrationFunctionalities integrFunctions = new IntegrationFunctionalities(participantController, eventController, integrationController);

        // MenuController recebe as instâncias de funcionalidades
        MenuController menu = new MenuController(partFunctions, eventFunctions, integrFunctions);
        menu.run();

        // Salvando dados ao sair
        try {
            participantRepository.saveToJson(participantsFilename);
        } catch (IOException e) {
            System.out.println("Error saving participant data.");
        }
        try {
            eventRepository.saveToJson(eventsFilename);
        } catch (IOException e) {
            System.out.println("Error saving event data.");
        }
    }
}
