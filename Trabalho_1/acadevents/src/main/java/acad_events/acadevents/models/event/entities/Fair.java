package acad_events.acadevents.models.event.entities;

import acad_events.acadevents.models.event.entities.enums.Modality;

public class Fair extends Event {
    private String organizer;
    private int numberOfStands;

    public Fair(String title, String date, String location, int capacity, String description, Modality modality, String organizer, int numberOfStands) {
        super(title, date, location, capacity, description, modality);
        this.organizer = organizer;
        this.numberOfStands = numberOfStands;
    }

    public String getOrganizer() {
        return organizer;
    }

    public int getNumberOfStands() {
        return numberOfStands;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public void setNumberOfStands(int numberOfStands) {
        this.numberOfStands = numberOfStands;
    }

    
}