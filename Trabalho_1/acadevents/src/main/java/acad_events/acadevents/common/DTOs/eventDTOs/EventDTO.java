package acad_events.acadevents.common.DTOs.eventDTOs;

import java.util.ArrayList;
import java.util.List;

import acad_events.acadevents.common.DTOs.participantDTOs.ParticipantDTO;
import acad_events.acadevents.models.event.entities.enums.Modality;

public class EventDTO {
    private String title;
    private String date;
    private String location;
    protected int capacity;
    private String description;
    private Modality modality;
    protected List<ParticipantDTO> participants = new ArrayList<>();

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public List<ParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDTO> participants) {
        this.participants = participants;
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
