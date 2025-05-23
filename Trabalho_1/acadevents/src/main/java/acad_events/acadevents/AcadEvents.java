package acad_events.acadevents;

import acad_events.acadevents.models.participant.ParticipantController;
import acad_events.acadevents.models.participant.entities.Participant;
import acad_events.acadevents.models.participant.entities.enums.AcademyDepartment;
import acad_events.acadevents.models.participant.entities.enums.ExternalRole;
import acad_events.acadevents.ui.menu.MenuController;

public class AcadEvents 
{
    public static void main( String[] args )
    {
        // TESTE
        ParticipantController participantController = new ParticipantController();

        // Cadastrando participantes
        participantController.register("Jorge", "123.145.852-55", "jorge@gmail.com", "(71) 99253-9993", "20241160005F");
        participantController.register("Joana", "123.145.852-56", "joana@gmail.com", "(71) 99253-9994", "20241160006F");
        participantController.register("Carlos", "321.654.987-00", "carlos@ifba.edu.br", "(71) 98888-1111", "EMP001", AcademyDepartment.COMPUTER_SCIENCE);
        participantController.register("Marcos", "555.666.777-88", "marcos@empresa.com", "(71) 97777-3333", null, "Empresa X", ExternalRole.SPEAKER);

        // Exibindo todos os participantes cadastrados
        System.out.println("Lista de participantes cadastrados:");
        for(Participant p : participantController.list()){
            System.err.println(p.getId() + " " + p.getName() + " " + p.getEmail());
        }

        // Deletando
        participantController.delete("123.145.852-56");

        System.out.println("Lista de participantes cadastrados:");
        for(Participant p : participantController.list()){
            System.err.println(p.getId() + " " + p.getName() + " " + p.getEmail());
        }

        
        //recuperação de infos

        //Menu
        MenuController menu = new MenuController();
        menu.run();
        
        //Armazenamento de infos          
    }
}
