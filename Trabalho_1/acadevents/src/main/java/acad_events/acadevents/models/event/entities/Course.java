package acad_events.acadevents.models.event.entities;

import acad_events.acadevents.models.event.enums.Modality;

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

    public String getCoordinator() {
        return coordinator;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public String getKnowledgeArea() {
        return knowledgeArea;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public void setKnowledgeArea(String knowledgeArea) {
        this.knowledgeArea = knowledgeArea;
    }

    
}