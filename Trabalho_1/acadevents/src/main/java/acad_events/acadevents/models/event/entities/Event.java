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
    protected List<Participant> presentialParticipants = new ArrayList<>();
    protected List<Participant> onlineParticipants = new ArrayList<>();

    public Event(String title, String date, String location, int capacity, String description, Modality modality) {
        this.Id = nextID++;
        this.title = title;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
        this.description = description;
        this.modality = modality;
    }

    public boolean addPresentialParticipant(Participant p) {
        if (presentialParticipants.size() < capacity) {
            presentialParticipants.add(p);
            return true;
        }
    return false;
    }

    public boolean removePresentialParticipant(Participant p) {
        if (p != null){
            presentialParticipants.remove(p);
            return true;
        }
        return false;
    }

    public List<Participant> getPresentialParticipants() {
        if (presentialParticipants == null) presentialParticipants = new ArrayList<>();
        return presentialParticipants;
    }

    public boolean addOnlineParticipant(Participant p) {
        if (onlineParticipants.size() < capacity) {
            onlineParticipants.add(p);
            return true;
        }
    return false;
    }

    public boolean removeOnlineParticipant(Participant p) {
        if (p != null){
            onlineParticipants.remove(p);
            return true;
        }
        return false;
    }

    public List<Participant> getOnlineParticipants() {
        if (onlineParticipants == null) onlineParticipants = new ArrayList<>();
        return onlineParticipants;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public void setPresentialParticipants(List<Participant> participants) {
        this.presentialParticipants = participants;
    }

    public void setOnlineParticipants(List<Participant> participants) {
        this.onlineParticipants = participants;
    }
}
