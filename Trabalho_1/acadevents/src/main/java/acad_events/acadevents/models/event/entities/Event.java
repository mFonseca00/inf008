package acad_events.acadevents.models.event.entities;

import java.util.ArrayList;
import java.util.List;

import acad_events.acadevents.models.event.entities.enums.Modality;
import acad_events.acadevents.models.participant.entities.Participant;

public class Event {
    private long Id;
    private static long nextID;
    private String title;
    private String date;
    private String location;
    protected int capacity;
    private String description;
    private Modality modality;
    protected List<Participant> participants = new ArrayList<>();

    public Event(String title, String date, String location, int capacity, String description, Modality modality) {
        this.Id = nextID++;
        this.title = title;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
        this.description = description;
        this.modality = modality;
    }

    public boolean addParticipant(Participant p) {
    if (participants.size() < capacity) {
        participants.add(p);
        return true;
    }
    return false;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public static void setNextID(long next) {
        nextID = next;
    }

    public long getId() {
        return Id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDescription() {
        return description;
    }

    public Modality getModality() {
        return modality;
    }

    
    
}
