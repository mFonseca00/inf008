package acad_events.acadevents.controllers;

import acad_events.acadevents.common.dtos.event.EventDTO;
import acad_events.acadevents.common.dtos.participant.ParticipantDTO;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.common.utils.enums.EventType;
import acad_events.acadevents.models.event.entities.Event;
import acad_events.acadevents.models.participant.entities.Participant;
import acad_events.acadevents.repositories.EventRepository;
import acad_events.acadevents.repositories.ParticipantRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * Controller for managing integration between participants and events in the AcadEvents system.
 * Handles participant enrollment, unenrollment, and certificate generation functionality.
 * Bridges the gap between EventRepository and ParticipantRepository for complex operations.
 * 
 * Key features:
 * - Manages both presential and online participant enrollment in events
 * - Handles participant removal from events (used when deleting participants)
 * - Generates formatted certificates for event participation
 * - Exports certificates to organized file structure with automatic naming
 * - Validates participation before certificate generation
 * 
 * Used by: IntegrationFunctionalities for participant enrollment and certificate management
 */
public class IntegrationController {
    private final ParticipantRepository participantRepo;
    private final EventRepository eventRepo;

    public IntegrationController(ParticipantRepository participantRepo, EventRepository eventRepo){
        this.participantRepo = participantRepo;
        this.eventRepo = eventRepo;
    }

    // Enrollment methods for different participation modalities (presential/online)
    public boolean enrollPresentialParticipantInEvent(ParticipantDTO participantDTO, Long eventId) {
        if (participantDTO == null) return false;
        Participant participant = participantRepo.getParticipantByCPF(participantDTO.getCpf());
        if (participant == null) return false;
        return eventRepo.addPresentialParticipantToEvent(eventId, participant);
    }

    public boolean enrollOnlineParticipantInEvent(ParticipantDTO participantDTO, Long eventId) {
        if (participantDTO == null) return false;
        Participant participant = participantRepo.getParticipantByCPF(participantDTO.getCpf());
        if (participant == null) return false;
        return eventRepo.addOnlineParticipantToEvent(eventId, participant);
    }

    // Unenrollment methods for removing participants from specific events
    public boolean removePresentialParticipantFromEvent(ParticipantDTO participantDTO, Long eventId) {
        if (participantDTO == null) return false;
        Participant participant = participantRepo.getParticipantByCPF(participantDTO.getCpf());
        if (participant == null) return false;
        return eventRepo.removePresentialParticipantFromEvent(eventId, participant);
    }

    public boolean removeOnlineParticipantFromEvent(ParticipantDTO participantDTO, Long eventId) {
        if (participantDTO == null) return false;
        Participant participant = participantRepo.getParticipantByCPF(participantDTO.getCpf());
        if (participant == null) return false;
        return eventRepo.removeOnlineParticipantFromEvent(eventId, participant);
    }

    // Cleanup method: removes participant from all events (used during participant deletion)
    public void unenrollParticipantFromAllEvents(ParticipantDTO participantDTO) {
        if (participantDTO == null) {
            return;
        }
        Collection<Event> allEvents = eventRepo.getAllEvents();
        if (allEvents != null) {
            for (Event event : allEvents) {
                this.removePresentialParticipantFromEvent(participantDTO, event.getId());
                this.removeOnlineParticipantFromEvent(participantDTO, event.getId());
            }
        }
    }

    // Certificate generation with participation validation and formatted output
    public String generateCertificade(ParticipantDTO participantDTO, EventType eventType, EventDTO eventDTO){
        if(participantDTO == null || eventDTO == null){
            return "Participant or event not found.";
        }
        boolean presentialEnrolled = false;
        boolean onlineEnrolled = false;
        for (ParticipantDTO p : eventDTO.getPresentialParticipants()) {
            if (p.getCpf().equals(participantDTO.getCpf())) {
                presentialEnrolled = true;
                break;
            }
        }

        if (!presentialEnrolled) {
            for (ParticipantDTO p : eventDTO.getOnlineParticipants()) {
                if (p.getCpf().equals(participantDTO.getCpf())) {
                    onlineEnrolled = true;
                    break;
                }
            }
        }

        if (!onlineEnrolled && !presentialEnrolled) {
            return "Participant is not enrolled in this event.";
        }

        String certificateContent = "We certify that " + participantDTO.getName() +
               " participated in the " + eventType + ": " + eventDTO.getTitle() +
               " held on " + eventDTO.getDate() + ".";
        return TextBoxUtils.formatedCertificateText(certificateContent);
    }

    // Certificate export with automatic directory creation and sanitized file naming
    public void exportCertificateToTxt(String certificateText, String participantName, String eventTitle, String eventDate) throws IOException {
        File certificatesDir = new File("certificates");
        if (!certificatesDir.exists()) {
            certificatesDir.mkdir();
        }
        String safeParticipant = participantName.replaceAll("[\\\\/:*?\"<>|\\s]", "_");
        String safeEvent = eventTitle.replaceAll("[\\\\/:*?\"<>|\\s]", "_");
        String safeDate = eventDate.replaceAll("[\\\\/:*?\"<>|\\s]", "_");
        String fileName = "certificate_" + safeParticipant + "_" + safeEvent + "_" + safeDate + ".txt";
        String filePath = "certificates" + File.separator + fileName;

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(certificateText);
        }
    }
}
