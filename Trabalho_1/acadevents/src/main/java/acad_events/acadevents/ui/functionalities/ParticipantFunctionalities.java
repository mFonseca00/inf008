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
import acad_events.acadevents.ui.functionalities.forms.ParticipantForm;

public class ParticipantFunctionalities {

    private final ParticipantController controller;

    public ParticipantFunctionalities(ParticipantController controller) {
        this.controller = controller;
    }

    public boolean registerNew(Scanner scan){

        ParticipantDTO participant = new ParticipantDTO();
        // solicita os dados comuns (CPF, nome, email, telefone)
        if(ParticipantForm.readCpf(scan, participant) == InputResult.CANCELLED) return false;
        if(ParticipantForm.readName(scan, participant) == InputResult.CANCELLED) return false;
        if(ParticipantForm.readEmail(scan, participant) == InputResult.CANCELLED) return false;
        if(ParticipantForm.readPhone(scan, participant) == InputResult.CANCELLED) return false;

        // seleciona o tipo de participante (student, professor, external) opção para retornar para menu anterior também
        ParticipantTypeOption type = ParticipantForm.selectType(scan);

        switch(type){
            case STUDENT:
                StudentDTO student = new StudentDTO(participant);
                if(ParticipantForm.readEnrollment(scan, student) == InputResult.CANCELLED) return false;
                // add no repositório
                controller.register(student);
                break;
            case PROFESSOR:
                ProfessorDTO professor = new ProfessorDTO(participant);
                if(ParticipantForm.readEmployeeId(scan, professor) == InputResult.CANCELLED) return false;
                if(ParticipantForm.selectDepartment(scan, professor) == InputResult.CANCELLED) return false;
                // add no repositório
                controller.register(professor);
                break;
            case EXTERNAL:
                ExternalDTO external = new ExternalDTO(participant);
                if(ParticipantForm.readOrg(scan, external) == InputResult.CANCELLED) return false;
                if(ParticipantForm.selectRole(scan, external) == InputResult.CANCELLED) return false;
                // add no repositório
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
        String cpf = ParticipantForm.readCpfOnly(scan);
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

    public void insertOnEvent(Scanner scan){
        // solicita o cpf do participante

        //chama o método do eventController para inserir o participante em um evento específico
    }

    public void generateCertificate(Scanner scan){
        // solicita o cpf do participante
        // solicita o código do curso

        // chama o método do eventController para verificar se o participante está inscrito naquele curso
        // chama o método do participantController gerar o certificado em arquivo de texto
    }
}


