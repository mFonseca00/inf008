package acad_events.acadevents.ui.functionalities;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import acad_events.acadevents.common.DTOs.eventDTOs.CourseDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.EventDTO;
import acad_events.acadevents.common.DTOs.participantDTOs.ParticipantDTO;
import acad_events.acadevents.common.DTOs.participantDTOs.StudentDTO;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.EventType;
import acad_events.acadevents.controllers.EventController;
import acad_events.acadevents.controllers.IntegrationController;
import acad_events.acadevents.controllers.ParticipantController;
import acad_events.acadevents.models.event.enums.Modality;
import acad_events.acadevents.ui.functionalities.enums.YesOrNoOption;
import acad_events.acadevents.ui.functionalities.forms.BaseForm;
import acad_events.acadevents.ui.functionalities.forms.EventForms.EventForm;
import acad_events.acadevents.ui.functionalities.forms.ParticipantForms.ParticipantForm;

public class IntegrationFunctionalities extends BaseFunctionalities {

    private final IntegrationController integrationController;

    public IntegrationFunctionalities(ParticipantController participantController, EventController eventController, IntegrationController integrationController) {
        super(eventController, participantController);
        this.integrationController = integrationController;
    }

    public boolean enrollParticipantInEvent(Scanner scan) {
        String cpf = ParticipantForm.readCpf(scan);
        ParticipantDTO participant = participantController.findParticipantByCPF(cpf);
        if (participant == null) {
            TextBoxUtils.printError("Participant not found.");
            return false;
        }
        EventDTO selectedEvent = selectEventByWay(scan, "enroll");
        if (selectedEvent == null) return false;

        // Verifica capacidade na lista correta
        int presentialCount = selectedEvent.getPresentialParticipants().size();
        int capacity = selectedEvent.getCapacity();

        // Regra para cursos
        if (selectedEvent instanceof CourseDTO && !(participant instanceof StudentDTO)) {
            TextBoxUtils.printError("Only students can enroll in courses.");
            return false;
        }

        // Decide a modalidade e chama o método correto do IntegrationController
        boolean enrolledPresential = selectedEvent.getPresentialParticipants().stream()
                    .anyMatch(p -> p.getCpf().equals(participant.getCpf()));
        boolean enrolledOnline = selectedEvent.getOnlineParticipants().stream()
                    .anyMatch(p -> p.getCpf().equals(participant.getCpf()));
        boolean alreadyEnrolled = enrolledOnline || enrolledPresential? true : false;
        boolean added = false;
        switch (selectedEvent.getModality()) {
            case PRESENTIAL:
                if (presentialCount >= capacity) {
                    TextBoxUtils.printError("Capacity limit reached. Cannot enroll participant.");
                    return false;
                }
                if (alreadyEnrolled) {
                    TextBoxUtils.printError("Participant is already enrolled in this event.");
                    return false;
                }
                added = integrationController.enrollPresentialParticipantInEvent(participant, selectedEvent.getId());
                break;
            case ONLINE:
                if (alreadyEnrolled) {
                    TextBoxUtils.printError("Participant is already enrolled in this event.");
                    return false;
                }
                added = integrationController.enrollOnlineParticipantInEvent(participant, selectedEvent.getId());
                break;
            case HYBRID:
                if (alreadyEnrolled) {
                    TextBoxUtils.printError("Participant is already enrolled in this event.");
                    return false;
                }
                Modality modality = EventForm.selectPresentialOrOnlineModality(scan);
                if (modality == null) return false;
                if (modality == Modality.PRESENTIAL) {
                    added = integrationController.enrollPresentialParticipantInEvent(participant, selectedEvent.getId());
                } else if (modality == Modality.ONLINE) {
                    added = integrationController.enrollOnlineParticipantInEvent(participant, selectedEvent.getId());
                }
                break;
        }

        if (added) {
            // Atualiza o DTO para refletir o estado real após a inscrição
            EventDTO updatedEvent = eventController.getEventById(selectedEvent.getId());
            selectedEvent.setPresentialParticipants(updatedEvent.getPresentialParticipants());
            selectedEvent.setOnlineParticipants(updatedEvent.getOnlineParticipants());

            TextBoxUtils.printSuccess("Participant enrolled successfully!");
            return true;
        } else {
            TextBoxUtils.printError("Failed to enroll participant.");
            return false;
        }
    }

    public boolean removeParticipantFromEvent(Scanner scan) {
        String cpf = ParticipantForm.readCpf(scan);
        ParticipantDTO participant = participantController.findParticipantByCPF(cpf);
        if (participant == null) {
            TextBoxUtils.printError("Participant not found.");
            return false;
        }
        EventDTO selectedEvent = selectEventByWay(scan, "remove participant");
        if (selectedEvent == null) return false;

        boolean removed = false;
        switch (selectedEvent.getModality()) {
            case PRESENTIAL:
                boolean isEnrolledPresential = selectedEvent.getPresentialParticipants().stream()
                    .anyMatch(p -> p.getCpf().equals(participant.getCpf()));
                if (!isEnrolledPresential) {
                    TextBoxUtils.printError("Participant is not enrolled in this event.");
                    return false;
                }
                removed = integrationController.removePresentialParticipantFromEvent(participant, selectedEvent.getId());
                break;
            case ONLINE:
                boolean isEnrolledOnline = selectedEvent.getOnlineParticipants().stream()
                    .anyMatch(p -> p.getCpf().equals(participant.getCpf()));
                if (!isEnrolledOnline) {
                    TextBoxUtils.printError("Participant is not enrolled in this event.");
                    return false;
                }
                removed = integrationController.removeOnlineParticipantFromEvent(participant, selectedEvent.getId());
                break;
            case HYBRID:
                boolean wasEnrolledPresential = selectedEvent.getPresentialParticipants().stream()
                    .anyMatch(p -> p.getCpf().equals(participant.getCpf()));
                boolean wasEnrolledOnline = selectedEvent.getOnlineParticipants().stream()
                    .anyMatch(p -> p.getCpf().equals(participant.getCpf()));
                if (!wasEnrolledPresential && !wasEnrolledOnline) {
                    TextBoxUtils.printError("Participant is not enrolled in this event.");
                    return false;
                }
                boolean removedPresential = false, removedOnline = false;
                if (wasEnrolledPresential) {
                    removedPresential = integrationController.removePresentialParticipantFromEvent(participant, selectedEvent.getId());
                }
                if (wasEnrolledOnline) {
                    removedOnline = integrationController.removeOnlineParticipantFromEvent(participant, selectedEvent.getId());
                }
                removed = removedPresential || removedOnline;
                break;
        }

        if (removed) {
            // Atualiza o DTO para refletir o estado real após a remoção
            EventDTO updatedEvent = eventController.getEventById(selectedEvent.getId());
            selectedEvent.setPresentialParticipants(updatedEvent.getPresentialParticipants());
            selectedEvent.setOnlineParticipants(updatedEvent.getOnlineParticipants());

            TextBoxUtils.printSuccess("Participant removed from event successfully!");
            return true;
        } else {
            TextBoxUtils.printError("Failed to remove participant from event.");
            return false;
        }
    }

    public boolean generateCertificate(Scanner scan){
        String cpf = ParticipantForm.readCpf(scan);
        ParticipantDTO participant = participantController.findParticipantByCPF(cpf);
        if (participant == null) {
            TextBoxUtils.printError("Participant not found.");
            return false;
        }

        // Obtém todos os eventos
        Collection<EventDTO> allEvents = eventController.listAll();

        // Filtra os eventos em que o participante está inscrito
        List<EventDTO> enrolledEvents = allEvents.stream()
            .filter(event -> event.getPresentialParticipants().stream()
                    .anyMatch(p -> p.getCpf().equals(participant.getCpf())) ||
                    event.getOnlineParticipants().stream()
                    .anyMatch(p -> p.getCpf().equals(participant.getCpf())))
            .toList();

        if (enrolledEvents.isEmpty()) {
            TextBoxUtils.printError("Participant is not enrolled in any events.");
            return false;
        }

        EventDTO selectedEvent = EventForm.selectEvent(scan, enrolledEvents);
        
        String certificateText = integrationController.generateCertificade(
            participant,
            EventType.valueOf(selectedEvent.getClass().getSimpleName().replace("DTO", "").toUpperCase()),
            selectedEvent
        );

        TextBoxUtils.printTitle("Certificate generated");
        TextBoxUtils.printLeftText(certificateText);
        YesOrNoOption option = BaseForm.selectYesOrNo(scan, "Do you want to export the certificate?");
        if (option == YesOrNoOption.YES) {
            try {
                integrationController.exportCertificateToJson(
                    certificateText,
                    participant.getName(),
                    selectedEvent.getTitle(),
                    selectedEvent.getDate().toString()
                );
                TextBoxUtils.printSuccess("Certificate exported successfully!");
            } catch (IOException e) {
                TextBoxUtils.printError("Error exporting certificate: " + e.getMessage());
                return false;
            }
        }
        return true;
    }

}
