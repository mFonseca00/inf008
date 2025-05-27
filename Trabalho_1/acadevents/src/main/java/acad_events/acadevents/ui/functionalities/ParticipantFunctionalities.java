package acad_events.acadevents.ui.functionalities;

import java.util.Collection;
import java.util.Scanner;

import acad_events.acadevents.common.DTOs.participantDTOs.ExternalDTO;
import acad_events.acadevents.common.DTOs.participantDTOs.ParticipantDTO;
import acad_events.acadevents.common.DTOs.participantDTOs.ProfessorDTO;
import acad_events.acadevents.common.DTOs.participantDTOs.StudentDTO;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.models.participant.ParticipantController;
import acad_events.acadevents.ui.functionalities.enums.InputResult;
import acad_events.acadevents.ui.functionalities.enums.ParticipantTypeOption;
import acad_events.acadevents.ui.functionalities.forms.ParticipantForms.ExternalForm;
import acad_events.acadevents.ui.functionalities.forms.ParticipantForms.ParticipantForm;
import acad_events.acadevents.ui.functionalities.forms.ParticipantForms.ProfessorForm;
import acad_events.acadevents.ui.functionalities.forms.ParticipantForms.StudentForm;

public class ParticipantFunctionalities {

    private final ParticipantController controller;

    public ParticipantFunctionalities(ParticipantController controller) {
        this.controller = controller;
    }

    public boolean registerNew(Scanner scan){

        ParticipantDTO participant = new ParticipantDTO();
        if(ParticipantForm.registerCpf(scan, participant) == InputResult.CANCELLED) return false;

        // Verifica se já existe participante com o mesmo CPF
        if (controller.existsByCPF(participant.getCpf())) {
            TextBoxUtils.printTitle("A participant with this CPF already exists!");
            return false;
        }

        if(ParticipantForm.registerName(scan, participant) == InputResult.CANCELLED) return false;
        if(ParticipantForm.registerEmail(scan, participant) == InputResult.CANCELLED) return false;
        if(ParticipantForm.registerPhone(scan, participant) == InputResult.CANCELLED) return false;

        // seleciona o tipo de participante (student, professor, external)
        ParticipantTypeOption type = ParticipantForm.selectType(scan);

        switch(type){
            case STUDENT:
                StudentDTO student = new StudentDTO(participant);
                if(StudentForm.registerEnrollment(scan, student) == InputResult.CANCELLED) return false;
                controller.register(student);
                break;
            case PROFESSOR:
                ProfessorDTO professor = new ProfessorDTO(participant);
                if(ProfessorForm.registerEmployeeId(scan, professor) == InputResult.CANCELLED) return false;
                if(ProfessorForm.selectDepartment(scan, professor) == InputResult.CANCELLED) return false;
                controller.register(professor);
                break;
            case EXTERNAL:
                ExternalDTO external = new ExternalDTO(participant);
                if(ExternalForm.registerOrg(scan, external) == InputResult.CANCELLED) return false;
                if(ExternalForm.selectRole(scan, external) == InputResult.CANCELLED) return false;
                controller.register(external);
                break;
            case CANCELLED:
                return false;
        }
        TextBoxUtils.printTitle("Participant registred sucessfully !!!");
        return true;
    }

    public boolean remove(Scanner scan){
        // solicita o cpf a ser "cancelado"
        String cpf = ParticipantForm.readCpf(scan);
        if(cpf == null) return false;
        // chama o método de remoção do participantController
        boolean removed = controller.delete(cpf);
        if (removed) {
            TextBoxUtils.printTitle("Participant removed from the system!");
        } else {
            TextBoxUtils.printTitle("Participant not found.");
        }
        return removed;
    }

    public void listAll(){
        // chama o método de listagem do participantController para puxar a collection
        Collection<ParticipantDTO> participants = controller.list();
        if (participants.isEmpty()) {
            TextBoxUtils.printTitle("No participants registered.");
            return;
        }
        TextBoxUtils.printTitle("Registered participants:");
        for (ParticipantDTO p : participants) {
            TextBoxUtils.printTableRow("CPF: " + p.getCpf(), "Name: " + p.getName(), "Email: " + p.getEmail());
        }
        TextBoxUtils.printUnderLineDisplayDivisor();
    }

}


