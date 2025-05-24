package acad_events.acadevents;

import acad_events.acadevents.common.enums.AcademyDepartment;
import acad_events.acadevents.common.enums.ExternalRole;
import acad_events.acadevents.common.DTOs.StudentDTO;
import acad_events.acadevents.common.DTOs.ProfessorDTO;
import acad_events.acadevents.common.DTOs.ExternalDTO;
import acad_events.acadevents.common.DTOs.ParticipantDTO;
import acad_events.acadevents.models.participant.ParticipantController;
import acad_events.acadevents.models.participant.entities.Participant;
import acad_events.acadevents.ui.menu.MenuController;

public class AcadEvents 
{
    public static void main( String[] args )
    {
        // TESTE
        ParticipantController participantController = new ParticipantController();

        // Cadastrando participantes usando DTOs

        // Student
        StudentDTO student1 = new StudentDTO(new ParticipantDTO());
        student1.setName("Jorge");
        student1.setCpf("123.145.852-55");
        student1.setEmail("jorge@gmail.com");
        student1.setPhone("(71) 99253-9993");
        student1.setEnrollment("20241160005F");
        participantController.register(student1);

        StudentDTO student2 = new StudentDTO(new ParticipantDTO());
        student2.setName("Joana");
        student2.setCpf("123.145.852-56");
        student2.setEmail("joana@gmail.com");
        student2.setPhone("(71) 99253-9994");
        student2.setEnrollment("20241160006F");
        participantController.register(student2);

        // Professor
        ProfessorDTO professor = new ProfessorDTO(new ParticipantDTO());
        professor.setName("Carlos");
        professor.setCpf("321.654.987-00");
        professor.setEmail("carlos@ifba.edu.br");
        professor.setPhone("(71) 98888-1111");
        professor.setEmployeeId("EMP001");
        professor.setDepartment(AcademyDepartment.COMPUTER_SCIENCE);
        participantController.register(professor);

        // External
        ExternalDTO external = new ExternalDTO(new ParticipantDTO());
        external.setName("Marcos");
        external.setCpf("555.666.777-88");
        external.setEmail("marcos@empresa.com");
        external.setPhone("(71) 97777-3333");
        external.setOrganization("Empresa X");
        external.setRole(ExternalRole.SPEAKER);
        participantController.register(external);

        // Exibindo todos os participantes cadastrados
        System.out.println("\nLista de participantes cadastrados:");
        for(Participant p : participantController.list()){
            System.err.println(p.getId() + " " + p.getName() + " " + p.getEmail());
        }

        // Deletando
        participantController.delete("123.145.852-56");

        System.out.println("\nLista de participantes cadastrados:");
        for(Participant p : participantController.list()){
            System.err.println(p.getId() + " " + p.getName() + " " + p.getEmail());
        }

        //Menu
        MenuController menu = new MenuController();
        menu.run();
    }

}
