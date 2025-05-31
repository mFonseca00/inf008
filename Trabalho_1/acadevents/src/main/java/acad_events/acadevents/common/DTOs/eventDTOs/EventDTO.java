package acad_events.acadevents.common.DTOs.eventDTOs;

import java.util.ArrayList;
import java.util.List;

import acad_events.acadevents.common.DTOs.participantDTOs.ParticipantDTO;
import acad_events.acadevents.models.event.enums.Modality;

public class EventDTO {
    private Long id;
    private String title;
    private String date;
    private String location;
    protected int capacity;
    private String description;
    private Modality modality;
    protected List<ParticipantDTO> presentialParticipants = new ArrayList<>();
    protected List<ParticipantDTO> onlineParticipants = new ArrayList<>();

    public EventDTO() {
        this.presentialParticipants = new ArrayList<>();
        this.onlineParticipants = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public List<ParticipantDTO> getPresentialParticipants() {
        if (presentialParticipants == null) {
            presentialParticipants = new ArrayList<>();
        }
        return presentialParticipants;
    }

    public void setPresentialParticipants(List<ParticipantDTO> participants) {
        this.presentialParticipants = participants;
    }

    public List<ParticipantDTO> getOnlineParticipants() {
        if (onlineParticipants == null) {
            onlineParticipants = new ArrayList<>();
        }
        return onlineParticipants;
    }

    public void setOnlineParticipants(List<ParticipantDTO> participants) {
        this.onlineParticipants = participants;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
