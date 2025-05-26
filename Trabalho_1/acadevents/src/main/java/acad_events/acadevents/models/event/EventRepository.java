package acad_events.acadevents.models.event;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import acad_events.acadevents.models.event.entities.Course;
import acad_events.acadevents.models.event.entities.Event;
import acad_events.acadevents.models.event.entities.Fair;
import acad_events.acadevents.models.event.entities.Lecture;
import acad_events.acadevents.models.event.entities.Workshop;
import acad_events.acadevents.models.event.entities.enums.Modality;
import acad_events.acadevents.models.participant.entities.Participant;

public class EventRepository {
    private Map<Long, Event> eventsById = new HashMap<>();

    public void addEvent(Event event){
        eventsById.put(event.getId(),event);
    }

    public Event getEventById(Long id){
        return eventsById.get(id);
    }

    public List<Event> getEventByTitle(String title){
        List<Event> events = new ArrayList<>();
        for (Event event : eventsById.values()){
            if (event.getTitle().equalsIgnoreCase(title)){
                events.add(event);
            }
        }
        return events;
    }

    public List<Event> getEventByDate(String date){
        List<Event> events = new ArrayList<>();
        for (Event event : eventsById.values()){
            if (event.getDate().equalsIgnoreCase(date)){
                events.add(event);
            }
        }
        return events;
    }

    public List<Event> getEventByModality(Modality modality){
        List<Event> events = new ArrayList<>();
        for (Event event : eventsById.values()){
            if (event.getModality() == modality){
                events.add(event);
            }
        }
        return events;
    }

    public List<Event> getEventsByTipe(String type){
        List<Event> events = new ArrayList<>();
        for (Event event : eventsById.values()){
            if(event.getClass().getSimpleName().equalsIgnoreCase(type)){
                events.add(event);
            }
        }
        return events;
    }

    public Collection<Event> getAllEvents(){
        return eventsById.values();
    }

    public boolean removeEventById(Long id){
        Event event = eventsById.get(id);
        if(event != null){
            eventsById.remove(id);
            return true;
        }
        return false;
    }

    public boolean addParticipantToEvent(Long eventId, Participant participant) {
        Event event = getEventById(eventId);
        if (event != null) {
            return event.addParticipant(participant);
        }
        return false;
    }

    public void saveToJson(String filename) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<JsonObject> jsonList = new ArrayList<>();
        for (Event e : getAllEvents()) {
            JsonObject obj = (JsonObject) gson.toJsonTree(e);
            obj.addProperty("type", e.getClass().getSimpleName());
            jsonList.add(obj);
        }
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(jsonList, writer);
        }
    }

    public void loadFromJson(String filename) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filename)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            eventsById.clear();
            long maxId = 0;
            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                String type = obj.get("type").getAsString();
                Event e = null;
                switch (type) {
                    case "Course":
                        e = gson.fromJson(obj, Course.class);
                        break;
                    case "Fair":
                        e = gson.fromJson(obj, Fair.class);
                        break;
                    case "Lecture":
                        e = gson.fromJson(obj, Lecture.class);
                        break;
                    case "Workshop":
                        e = gson.fromJson(obj, Workshop.class);
                        break;
                    // Apply new types here
                }
                if (e != null) {
                    addEvent(e);
                    if (e.getId() > maxId) {
                        maxId = e.getId();
                    }
                }
            }
            Event.setNextID(maxId + 1);
        }
    }
    
}
