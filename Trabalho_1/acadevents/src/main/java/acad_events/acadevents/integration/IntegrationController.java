package acad_events.acadevents.integration;

import acad_events.acadevents.common.DTOs.eventDTOs.EventDTO;
import acad_events.acadevents.common.DTOs.participantDTOs.ParticipantDTO;
import acad_events.acadevents.models.event.EventRepository;
import acad_events.acadevents.models.participant.ParticipantRepository;
import acad_events.acadevents.models.participant.entities.Participant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;

public class IntegrationController {
    private final ParticipantRepository participantRepo;
    private final EventRepository eventRepo;

    public IntegrationController(ParticipantRepository participantRepo, EventRepository eventRepo){
        this.participantRepo = participantRepo;
        this.eventRepo = eventRepo;
    }

    public boolean enrollParticipantInEvent(ParticipantDTO participantDTO, Long eventId) {
        if (participantDTO == null) return false;
        Participant participant = participantRepo.getParticipantByCPF(participantDTO.getCpf());
        if (participant == null) return false;
        return eventRepo.addParticipantToEvent(eventId, participant);
    }

    public String generateCertificade(ParticipantDTO participantDTO, EventDTO eventDTO){
        if(participantDTO == null || eventDTO == null){
            return "Participant or event not found.";
        }
        if (!eventDTO.getParticipants().contains(participantDTO)) {
            return "Participant is not enrolled in this event.";
        }
        return "We certify that " + participantDTO.getName() +
               " participated in the event \"" + eventDTO.getTitle() +
               "\" held on " + eventDTO.getDate() + ".";
    }

    public void exportCertificateToJson(String certificateText, String filename) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(certificateText, writer);
        }
    }
}
