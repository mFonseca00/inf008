package acad_events.acadevents.models.event.entities;

import acad_events.acadevents.models.event.entities.enums.Modality;

public class Lecture extends Event {
    private String speaker;

    public Lecture(String title, String date, String location, int capacity, String description, Modality modality, String speaker) {
        super(title, date, location, capacity, description, modality);
        this.speaker = speaker;
    }

    public String getSpeaker() {
        return speaker;
    }
}
