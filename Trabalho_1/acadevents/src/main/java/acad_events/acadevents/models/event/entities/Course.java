package acad_events.acadevents.models.event.entities;

import acad_events.acadevents.models.event.entities.enums.Modality;
import acad_events.acadevents.models.participant.entities.Participant;
import acad_events.acadevents.models.participant.entities.Student;

public class Course extends Event {
    private String coordinator;
    private int totalHours;
    private String knowledgeArea;

    public Course(String title, String date, String location, int capacity, String description, Modality modality, String coordinator, int totalHours, String knowledgeArea) {
        super(title, date, location, capacity, description, modality);
        this.coordinator = coordinator;
        this.totalHours = totalHours;
        this.knowledgeArea = knowledgeArea;
    }

    @Override
    public boolean addParticipant(Participant p) {
        if (p instanceof Student && participants.size() < capacity) {
            participants.add(p);
            return true;
        }
        return false;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public String getKnowledgeArea() {
        return knowledgeArea;
    }
}