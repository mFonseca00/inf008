package acad_events.acadevents.models.event.entities;

import acad_events.acadevents.models.event.enums.Modality;

public class Workshop extends Event {
    private String instructor;
    private int durationHours;

    public Workshop(String title, String date, String location, int capacity, String description, Modality modality, String instructor, int durationHours) {
        super(title, date, location, capacity, description, modality);
        this.instructor = instructor;
        this.durationHours = durationHours;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    
}