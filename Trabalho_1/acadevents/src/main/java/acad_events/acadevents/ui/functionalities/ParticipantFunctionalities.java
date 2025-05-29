package acad_events.acadevents.ui.functionalities;

import java.util.Collection;
import java.util.Scanner;

import acad_events.acadevents.common.DTOs.participantDTOs.*;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.TestDataGenerator;
import acad_events.acadevents.models.participant.ParticipantController;
import acad_events.acadevents.models.event.EventController;
import acad_events.acadevents.ui.functionalities.enums.*;
import acad_events.acadevents.ui.functionalities.forms.BaseForm;
import acad_events.acadevents.ui.functionalities.forms.ParticipantForms.*;

public class ParticipantFunctionalities extends BaseFunctionalities {

    public ParticipantFunctionalities(EventController eventController, ParticipantController participantController) {
        super(eventController, participantController);
    }

    public boolean registerNew(Scanner scan){

        ParticipantDTO participant = new ParticipantDTO();
        if(ParticipantForm.registerCpf(scan, participant) == InputResult.CANCELLED) return false;

        // Verifica se já existe participante com o mesmo CPF
        if (participantController.existsByCPF(participant.getCpf())) {
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
                participantController.register(student);
                break;
            case PROFESSOR:
                ProfessorDTO professor = new ProfessorDTO(participant);
                if(ProfessorForm.registerEmployeeId(scan, professor) == InputResult.CANCELLED) return false;
                if(ProfessorForm.selectDepartment(scan, professor) == InputResult.CANCELLED) return false;
                participantController.register(professor);
                break;
            case EXTERNAL:
                ExternalDTO external = new ExternalDTO(participant);
                if(ExternalForm.registerOrg(scan, external) == InputResult.CANCELLED) return false;
                if(ExternalForm.selectRole(scan, external) == InputResult.CANCELLED) return false;
                participantController.register(external);
                break;
            case CANCELLED:
                return false;
        }
        TextBoxUtils.printTitle("Participant registered successfully!");
        return true;
    }

    public boolean remove(Scanner scan) {
        String cpf = ParticipantForm.readCpf(scan);
        if (cpf == null) return false;
        boolean removed = participantController.delete(cpf);
        if (removed) {
            TextBoxUtils.printTitle("Participant removed from the system!");
        } else {
            TextBoxUtils.printTitle("Participant not found.");
        }
        return removed;
    }

    public void listAll() {
        Collection<ParticipantDTO> participants = participantController.list();
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

    public void generateRandomParticipant(Scanner scan) {
        TextBoxUtils.printTitle("Generating test data...");
        int quantity = BaseForm.readQuantity(scan, "How many participants do you want to generate?");
        for (int i = 0; i < quantity; i++) {
            TestDataGenerator.generateRandomParticipant(participantController);
        }
        TextBoxUtils.printTitle(quantity + " participants generated successfully!");
    }
}


