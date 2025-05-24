package acad_events.acadevents;



import acad_events.acadevents.models.participant.ParticipantController;
import acad_events.acadevents.models.participant.ParticipantRepository;
import acad_events.acadevents.ui.functionalities.ParticipantFunctionalities;
import acad_events.acadevents.ui.menu.MenuController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AcadEvents 
{
    public static void main(String[] args)
    {
        ParticipantController participantController = new ParticipantController();
        ParticipantRepository repository = participantController.getRepository();

        String filename = "participants.json";
        File file = new File(filename);

        // Garante que o arquivo existe e, se não, cria um arquivo JSON vazio
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]");
            } catch (IOException e) {
                System.out.println("Error creating empty participants.json file.");
            }
        }

        // Carregando arquivo de dados
        try {
            repository.loadFromJson(filename);
        } catch (IOException e) {
            System.out.println("No previous data found or error loading data.");
        }

        // Instancia funcionalidades com o controller compartilhado
        ParticipantFunctionalities partFunctions = new ParticipantFunctionalities(participantController);

        // MenuController recebe a instância de funcionalidades
        MenuController menu = new MenuController(partFunctions);
        menu.run();

        // Salvando dados ao sair
        try {
            repository.saveToJson(filename);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }
}
