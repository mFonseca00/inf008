package acad_events.acadevents.models.event;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

import com.google.gson.*;

import acad_events.acadevents.models.event.entities.*;
import acad_events.acadevents.models.participant.entities.*;

public class EventRepository {
    private Map<Long, Event> eventsById = new HashMap<>();

    private static final Gson gson;
    static {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public void addEvent(Event event){
        eventsById.put(event.getId(), event);
    }

    public Event getEventById(Long id){
        return eventsById.get(id);
    }

    public List<Event> getEventsByType(String type){
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

    public boolean addPresentialParticipantToEvent(Long eventId, Participant participant) {
        Event event = getEventById(eventId);
        if (event != null) {
            return event.addPresentialParticipant(participant);
        }
        return false;
    }

    public boolean removePresentialParticipantFromEvent(Long eventId, Participant participant) {
        Event event = getEventById(eventId);
        if (event != null) {
            return event.removePresentialParticipant(participant);
        }
        return false;
    }

    public boolean addOnlineParticipantToEvent(Long eventId, Participant participant) {
        Event event = getEventById(eventId);
        if (event != null) {
            return event.addOnlineParticipant(participant);
        }
        return false;
    }

    public boolean removeOnlineParticipantFromEvent(Long eventId, Participant participant) {
        Event event = getEventById(eventId);
        if (event != null) {
            return event.removeOnlineParticipant(participant);
        }
        return false;
    }

    public void saveToJson(String filename) throws IOException {
        List<Event> events = new ArrayList<>(getAllEvents());
        JsonArray jsonArray = new JsonArray();

        for (Event event : events) {
            JsonObject eventJson = gson.toJsonTree(event).getAsJsonObject();
            eventJson.addProperty("eventType", event.getClass().getSimpleName());

            // Process presentialParticipants to add participantType
            eventJson.remove("presentialParticipants"); // Remove the array serialized by default
            JsonArray presentialParticipantsTypedArray = new JsonArray();
            if (event.getPresentialParticipants() != null) {
                for (Participant p : event.getPresentialParticipants()) {
                    JsonObject participantJson = gson.toJsonTree(p).getAsJsonObject();
                    participantJson.addProperty("participantType", p.getClass().getSimpleName());
                    presentialParticipantsTypedArray.add(participantJson);
                }
            }
            eventJson.add("presentialParticipants", presentialParticipantsTypedArray);

            // Process onlineParticipants to add participantType
            eventJson.remove("onlineParticipants"); // Remove the array serialized by default
            JsonArray onlineParticipantsTypedArray = new JsonArray();
            if (event.getOnlineParticipants() != null) {
                for (Participant p : event.getOnlineParticipants()) {
                    JsonObject participantJson = gson.toJsonTree(p).getAsJsonObject();
                    participantJson.addProperty("participantType", p.getClass().getSimpleName());
                    onlineParticipantsTypedArray.add(participantJson);
                }
            }
            eventJson.add("onlineParticipants", onlineParticipantsTypedArray);

            jsonArray.add(eventJson);
        }

        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(jsonArray, writer);
        }
    }

    private Class<? extends Event> getEventClassFromString(String eventType) {
        switch (eventType) {
            case "Course": return Course.class;
            case "Fair": return Fair.class;
            case "Lecture": return Lecture.class;
            case "Workshop": return Workshop.class;
            default:
                System.out.println("ERROR: Unknown event type: " + eventType);
                return null;
        }
    }

    private List<Participant> deserializeParticipantList(JsonArray participantsArrayJson) {
        List<Participant> participants = new ArrayList<>();
        if (participantsArrayJson == null) {
            return participants;
        }

        for (JsonElement participantElem : participantsArrayJson) {
            JsonObject participantJsonObject = participantElem.getAsJsonObject();
            JsonElement typeElement = participantJsonObject.get("participantType");
            if (typeElement == null || typeElement.isJsonNull()) {
                System.out.println("WARNING: participantType not found or null for participant: " + participantJsonObject.toString());
                continue;
            }
            String participantType = typeElement.getAsString();
            Participant p = null;
            switch (participantType) {
                case "Student":
                    p = gson.fromJson(participantJsonObject, Student.class);
                    break;
                case "Professor":
                    p = gson.fromJson(participantJsonObject, Professor.class);
                    break;
                case "External":
                    p = gson.fromJson(participantJsonObject, External.class);
                    break;
                default:
                    System.out.println("ERROR: Unknown participant type: " + participantType);
                    break;
            }
            if (p != null) {
                participants.add(p);
            }
        }
        return participants;
    }

    public void loadFromJson(String filename) throws IOException {
        try (Reader reader = new FileReader(filename)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            eventsById.clear();
            long maxId = 0;
            int index = 0;
            for (JsonElement elem : array) {
                try {
                    JsonObject jsonObject = elem.getAsJsonObject();
                    
                    JsonElement typeElement = jsonObject.get("eventType");
                    if (typeElement == null || typeElement.isJsonNull()) {
                         System.out.println("ERROR: eventType not found or null at index " + index + ": " + jsonObject.toString());
                         continue;
                    }
                    String eventType = typeElement.getAsString();
                    Class<? extends Event> eventClass = getEventClassFromString(eventType);

                    if (eventClass == null) {
                        continue; 
                    }

                    // Deserialize participants manually FIRST
                    List<Participant> presentialParticipants = deserializeParticipantList(jsonObject.getAsJsonArray("presentialParticipants"));
                    List<Participant> onlineParticipants = deserializeParticipantList(jsonObject.getAsJsonArray("onlineParticipants"));

                    // Remove participant list keys from the main JsonObject BEFORE deserializing the Event
                    // to prevent Gson from attempting to deserialize them as an abstract List<Participant>.
                    JsonObject eventOnlyJsonObject = jsonObject.deepCopy(); // Create a deep copy to avoid modifying the original JsonObject.
                    eventOnlyJsonObject.remove("presentialParticipants");
                    eventOnlyJsonObject.remove("onlineParticipants");

                    Event e = gson.fromJson(eventOnlyJsonObject, eventClass); // Deserialize the event object itself.
                    
                    // Set the manually deserialized participant lists onto the event object.
                    if (e != null) {
                        e.setPresentialParticipants(presentialParticipants);
                        e.setOnlineParticipants(onlineParticipants);
                        
                        addEvent(e);
                        if (e.getId() > maxId) {
                            maxId = e.getId();
                        }
                    } else {
                        System.out.println("ERROR: Null event after deserialization at index " + index + " for type " + eventType);
                    }
                } catch (Exception ex) {
                    System.out.println("ERROR deserializing event at index " + index + ": " + ex.getMessage());
                    ex.printStackTrace();
                }
                index++;
            }
            Event.setNextID(maxId + 1);
        }
    }
}