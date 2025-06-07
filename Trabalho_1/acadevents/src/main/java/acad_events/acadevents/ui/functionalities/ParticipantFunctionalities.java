package acad_events.acadevents.ui.functionalities;

import java.util.Collection;
import java.util.Scanner;

import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.controllers.EventController;
import acad_events.acadevents.controllers.ParticipantController;
import acad_events.acadevents.common.dtos.participant.*;
import acad_events.acadevents.common.utils.TestDataGenerator;
import acad_events.acadevents.ui.functionalities.enums.*;
import acad_events.acadevents.ui.functionalities.forms.BaseForm;
import acad_events.acadevents.ui.functionalities.forms.ParticipantForms.*;

public class ParticipantFunctionalities extends BaseFunctionalities {

    public ParticipantFunctionalities(EventController eventController, ParticipantController participantController) {
        super(eventController, participantController);
    }

    public boolean registerNew(Scanner scan){
        TextBoxUtils.printTitle("Register New Participant");
        ParticipantDTO participant = new ParticipantDTO();
        if(ParticipantForm.registerCpf(scan, participant) == InputResult.CANCELLED) {
            TextBoxUtils.printWarn("Participant registration cancelled by user (CPF input).");
            return false;
        }

        if (participantController.existsByCPF(participant.getCpf())) {
            TextBoxUtils.printError("A participant with this CPF already exists!");
            return false;
        }

        if(ParticipantForm.registerName(scan, participant) == InputResult.CANCELLED) {
            TextBoxUtils.printWarn("Participant registration cancelled by user (Name input).");
            return false;
        }
        if(ParticipantForm.registerEmail(scan, participant) == InputResult.CANCELLED) {
            TextBoxUtils.printWarn("Participant registration cancelled by user (Email input).");
            return false;
        }
        if(ParticipantForm.registerPhone(scan, participant) == InputResult.CANCELLED) {
            TextBoxUtils.printWarn("Participant registration cancelled by user (Phone input).");
            return false;
        }

        ParticipantTypeOption type = ParticipantForm.selectType(scan);

        switch(type){
            case STUDENT:
                StudentDTO student = new StudentDTO(participant);
                if(StudentForm.registerEnrollment(scan, student) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("Student registration cancelled by user (Enrollment input).");
                    return false;
                }
                participantController.register(student);
                break;
            case PROFESSOR:
                ProfessorDTO professor = new ProfessorDTO(participant);
                if(ProfessorForm.registerEmployeeId(scan, professor) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("Professor registration cancelled by user (Employee ID input).");
                    return false;
                }
                if(ProfessorForm.selectDepartment(scan, professor) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("Professor registration cancelled by user (Department selection).");
                    return false;
                }
                participantController.register(professor);
                break;
            case EXTERNAL:
                ExternalDTO external = new ExternalDTO(participant);
                if(ExternalForm.registerOrg(scan, external) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("External participant registration cancelled by user (Organization input).");
                    return false;
                }
                if(ExternalForm.selectRole(scan, external) == InputResult.CANCELLED) {
                    TextBoxUtils.printWarn("External participant registration cancelled by user (Role selection).");
                    return false;
                }
                participantController.register(external);
                break;
            case CANCELLED:
                TextBoxUtils.printWarn("Participant type selection cancelled by user.");
                return false;
        }
        TextBoxUtils.printSuccess("Participant registered successfully!");
        return true;
    }

    public boolean remove(Scanner scan, IntegrationFunctionalities integrationFuncs) {
        TextBoxUtils.printTitle("Remove Participant from System");
        String cpf = ParticipantForm.readCpf(scan);
        if (cpf == null) {
            TextBoxUtils.printWarn("CPF input cancelled. Participant removal aborted.");
            return false;
        }

        ParticipantDTO participantToDelete = participantController.findParticipantByCPF(cpf);

        if (participantToDelete == null) {
            TextBoxUtils.printError("Participant with CPF " + cpf + " not found.");
            return false;
        }

        TextBoxUtils.printWarn("Participant found: " + participantToDelete.getName());
        
        YesOrNoOption confirmation = BaseForm.selectYesOrNo(scan, "Are you sure you want to remove this participant from the system and all events?");
        if (confirmation == YesOrNoOption.NO) {
            TextBoxUtils.printWarn("Participant removal cancelled by user.");
            return false;
        }

        integrationFuncs.unenrollParticipantFromAllRegisteredEvents(participantToDelete);

        boolean removedFromSystem = participantController.delete(cpf);

        if (removedFromSystem) {
            TextBoxUtils.printSuccess("Participant '" + participantToDelete.getName() + "' removed successfully from the system.");
        } else {
            TextBoxUtils.printError("Failed to remove participant '" + participantToDelete.getName() + "' from the system.");
        }
        return removedFromSystem;
    }

    public void listAll() {
        Collection<ParticipantDTO> participants = participantController.list();
        if (participants.isEmpty()) {
            TextBoxUtils.printError("No participants registered.");
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
        if (quantity == -1) {
            TextBoxUtils.printWarn("Participant generation cancelled.");
            return;
        }
        if (quantity > 0) {
            for (int i = 0; i < quantity; i++) {
                TestDataGenerator.generateRandomParticipant(participantController);
            }
            TextBoxUtils.printSuccess(quantity + " participants generated successfully!");
        } else {
            TextBoxUtils.printWarn("No participants were generated.");
        }
    }
}


