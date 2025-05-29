package acad_events.acadevents.models.integration;

import acad_events.acadevents.common.DTOs.eventDTOs.EventDTO;
import acad_events.acadevents.common.DTOs.participantDTOs.ParticipantDTO;
import acad_events.acadevents.common.utils.enums.EventType;
import acad_events.acadevents.models.event.EventRepository;
import acad_events.acadevents.models.participant.ParticipantRepository;
import acad_events.acadevents.models.participant.entities.Participant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IntegrationController {
    private final ParticipantRepository participantRepo;
    private final EventRepository eventRepo;

    public IntegrationController(ParticipantRepository participantRepo, EventRepository eventRepo){
        this.participantRepo = participantRepo;
        this.eventRepo = eventRepo;
    }

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

    public String generateCertificade(ParticipantDTO participantDTO, EventType eventType, EventDTO eventDTO){
        if(participantDTO == null || eventDTO == null){
            return "Participant or event not found.";
        }
        // Verifica se está em alguma das listas
        boolean isEnrolled = eventDTO.getPresentialParticipants().stream()
            .anyMatch(p -> p.getCpf().equals(participantDTO.getCpf()))
            || eventDTO.getOnlineParticipants().stream()
            .anyMatch(p -> p.getCpf().equals(participantDTO.getCpf()));
        if (!isEnrolled) {
            return "Participant is not enrolled in this event.";
        }
        return "We certify that " + participantDTO.getName() +
               " participated in the " + eventType + " " + eventDTO.getTitle() +
               "\" held on " + eventDTO.getDate() + ".";
    }

    public void exportCertificateToJson(String certificateText, String participantName, String eventTitle, String eventDate) throws IOException {
        File certificatesDir = new File("certificates");
        if (!certificatesDir.exists()) {
            certificatesDir.mkdir();
        }
        String safeParticipant = participantName.replaceAll("[\\\\/:*?\"<>|\\s]", "_");
        String safeEvent = eventTitle.replaceAll("[\\\\/:*?\"<>|\\s]", "_");
        String safeDate = eventDate.replaceAll("[\\\\/:*?\"<>|\\s]", "_");
        String fileName = "certificate_" + safeParticipant + "_" + safeEvent + "_" + safeDate + ".json";
        String filePath = "certificates" + File.separator + fileName;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(certificateText, writer);
        }
    }
}
