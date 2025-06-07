package acad_events.acadevents.ui.functionalities;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import acad_events.acadevents.common.dtos.event.CourseDTO;
import acad_events.acadevents.common.dtos.event.EventDTO;
import acad_events.acadevents.common.dtos.participant.ParticipantDTO;
import acad_events.acadevents.common.dtos.participant.StudentDTO;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.EventType;
import acad_events.acadevents.controllers.EventController;
import acad_events.acadevents.controllers.IntegrationController;
import acad_events.acadevents.controllers.ParticipantController;
import acad_events.acadevents.models.event.enums.Modality;
import acad_events.acadevents.ui.functionalities.enums.YesOrNoOption;
import acad_events.acadevents.ui.functionalities.forms.BaseForm;
import acad_events.acadevents.ui.functionalities.forms.event_forms.EventForm;
import acad_events.acadevents.ui.functionalities.forms.participant_forms.ParticipantForm;

/**
 * UI functionality class for participant-event integration operations in the AcadEvents system.
 * Extends BaseFunctionalities to inherit shared event selection and controller access.
 * Handles complex workflows involving both participants and events with business rule enforcement.
 * 
 * Key features:
 * - Participant enrollment with capacity validation and participant type restrictions
 * - Multi-modality support (presential, online, hybrid) with appropriate enrollment logic
 * - Certificate generation with participation validation and optional file export
 * - Participant unenrollment from events (used during participant deletion)
 * - Duplicate enrollment prevention and comprehensive error handling
 * 
 * Business rules enforced:
 * - Only students can enroll in courses (CourseDTO restriction)
 * - Presential events have capacity limits, online events are unlimited
 * - Hybrid events allow choice between presential/online participation
 * - Certificate generation requires confirmed participation in selected event
 * 
 * Used by: ParticipantMenu for enrollment, certificate generation, and participant removal workflows
 * Integration: Works with IntegrationController for business logic and inherits event selection from BaseFunctionalities
 */
public class IntegrationFunctionalities extends BaseFunctionalities {

    private final IntegrationController integrationController;

    public IntegrationFunctionalities(ParticipantController participantController, EventController eventController, IntegrationController integrationController) {
        super(eventController, participantController);
        this.integrationController = integrationController;
    }

    // Complex enrollment workflow with business rule validation and multi-modality support
    public boolean enrollParticipantInEvent(Scanner scan) {
        String cpf = ParticipantForm.readCpf(scan);
        if (cpf == null) {
            TextBoxUtils.printWarn("CPF input cancelled by user.");
            return false;
        }
        ParticipantDTO participant = participantController.findParticipantByCPF(cpf);
        if (participant == null) {
            TextBoxUtils.printError("Participant not found with the provided CPF.");
            return false;
        }
        EventDTO selectedEvent = selectEventByWay(scan, "enroll");
        if (selectedEvent == null) return false;

        int presentialCount = selectedEvent.getPresentialParticipants().size();
        int capacity = selectedEvent.getCapacity();

        // Business rule: Only students can enroll in courses
        if (selectedEvent instanceof CourseDTO && !(participant instanceof StudentDTO)) {
            TextBoxUtils.printError("Only students can enroll in courses.");
            return false;
        }

        // Duplicate enrollment prevention
        boolean enrolledPresential = selectedEvent.getPresentialParticipants().stream()
                    .anyMatch(p -> p.getCpf().equals(participant.getCpf()));
        boolean enrolledOnline = selectedEvent.getOnlineParticipants().stream()
                    .anyMatch(p -> p.getCpf().equals(participant.getCpf()));
        boolean alreadyEnrolled = enrolledOnline || enrolledPresential;

        if (alreadyEnrolled) {
            TextBoxUtils.printError("Participant is already enrolled in this event.");
            return false;
        }

        // Multi-modality enrollment logic with capacity validation for presential events
        boolean added = false;
        switch (selectedEvent.getModality()) {
            case PRESENTIAL:
                if (presentialCount >= capacity) {
                    TextBoxUtils.printError("Capacity limit reached. Cannot enroll participant.");
                    return false;
                }
                added = integrationController.enrollPresentialParticipantInEvent(participant, selectedEvent.getId());
                break;
            case ONLINE:
                added = integrationController.enrollOnlineParticipantInEvent(participant, selectedEvent.getId());
                break;
            case HYBRID:
                Modality modality = EventForm.selectPresentialOrOnlineModality(scan);
                if (modality == null) return false;
                if (modality == Modality.PRESENTIAL) {
                    if (presentialCount >= capacity) {
                        TextBoxUtils.printError("Capacity limit reached. Cannot enroll participant.");
                        return false;
                    }
                    added = integrationController.enrollPresentialParticipantInEvent(participant, selectedEvent.getId());
                } else if (modality == Modality.ONLINE) {
                    added = integrationController.enrollOnlineParticipantInEvent(participant, selectedEvent.getId());
                }
                break;
        }

        if (added) {
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

    // Participant unenrollment with comprehensive modality handling
    public boolean removeParticipantFromEvent(Scanner scan) {
        String cpf = ParticipantForm.readCpf(scan);
        if (cpf == null) {
            TextBoxUtils.printWarn("CPF input cancelled by user.");
            return false;
        }
        ParticipantDTO participant = participantController.findParticipantByCPF(cpf);
        if (participant == null) {
            TextBoxUtils.printError("Participant not found with the provided CPF.");
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

    // Certificate generation with participation validation and optional file export
    public boolean generateCertificate(Scanner scan){
        String cpf = ParticipantForm.readCpf(scan);
        if (cpf == null) {
            TextBoxUtils.printWarn("CPF input cancelled by user.");
            return false; 
        }
        ParticipantDTO participant = participantController.findParticipantByCPF(cpf);
        if (participant == null) {
            TextBoxUtils.printError("Participant not found with the provided CPF.");
            return false;
        }

        Collection<EventDTO> allEvents = eventController.listAll();

        // Filter events where participant is actually enrolled (presential or online)
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
        if (selectedEvent == null) {
            TextBoxUtils.printWarn("Event selection cancelled by user.");
            return false;
        }
        
        String certificateText = integrationController.generateCertificade(
            participant,
            EventType.valueOf(selectedEvent.getClass().getSimpleName().replace("DTO", "").toUpperCase()),
            selectedEvent
        );

        TextBoxUtils.printTitle("Certificate generated");
        System.out.println(certificateText);
        YesOrNoOption option = BaseForm.selectYesOrNo(scan, "Do you want to export the certificate?");
        if (option == YesOrNoOption.YES) {
            try {
                integrationController.exportCertificateToTxt(
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

    // Cleanup method: removes participant from all events during participant deletion
    public void unenrollParticipantFromAllRegisteredEvents(ParticipantDTO participantDTO) {
        if (participantDTO == null) {
            TextBoxUtils.printError("Null Participante data. Can't unenroll.");
            return;
        }
        integrationController.unenrollParticipantFromAllEvents(participantDTO);
        TextBoxUtils.printSuccess("Participant '" + participantDTO.getName() + "' unenrolled from all events.");
    }
}
