package acad_events.acadevents.repositories;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import acad_events.acadevents.models.participant.entities.External;
import acad_events.acadevents.models.participant.entities.Participant;
import acad_events.acadevents.models.participant.entities.Professor;
import acad_events.acadevents.models.participant.entities.Student;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Repository class for participant data persistence and retrieval in the AcadEvents system.
 * Manages dual-indexing for fast lookups by both ID and CPF.
 * Handles JSON serialization/deserialization with polymorphic support for different participant types.
 * 
 * Key features:
 * - Dual HashMap storage for lookups by ID or CPF
 * - JSON persistence with type preservation for Student, Professor, and External participants
 * - Automatic ID sequence management to prevent conflicts after system restarts
 * - CPF-based operations for participant enrollment and certificate generation
 * 
 * Used by: ParticipantController for all participant persistence operations and
 * IntegrationController for CPF-based participant lookup during event enrollment
 */
public class ParticipantRepository {
    // Dual indexing for efficient access patterns - ID for internal operations, CPF for user interactions
    private Map<Long, Participant> participantsByID = new HashMap<>();
    private Map<String, Participant> participantsByCPF = new HashMap<>();

    public void addParticipant(Participant participant){
        participantsByID.put(participant.getId(), participant);
        participantsByCPF.put(participant.getCPF(), participant);
    }

    public Participant getParticipantById(Long id){
        return participantsByID.get(id);
    }

    // CPF-based lookup - crucial for enrollment and certificate generation workflows
    public Participant getParticipantByCPF(String cpf){
        return participantsByCPF.get(cpf);
    }

    public Collection<Participant> getAllParticipants(){
        return participantsByID.values();
    }

    public boolean removeParticipantById(Long id){
    Participant participant = participantsByID.get(id);
    if(participant != null){
        participantsByID.remove(id);
        participantsByCPF.remove(participant.getCPF());
        return true;
    }
    return false;
    }

    // CPF-based removal - maintains data consistency across both indexes
    public boolean removeParticipantByCPF(String cpf){
        Participant participant = participantsByCPF.get(cpf);
        if(participant != null){
            participantsByCPF.remove(cpf);
            participantsByID.remove(participant.getId());
            return true;
        }
        return false;
    }

    // JSON serialization with polymorphic type preservation for different participant types
    public void saveToJson(String filename) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<JsonObject> jsonList = new ArrayList<>();
        for (Participant p : getAllParticipants()) {
            JsonObject obj = (JsonObject) gson.toJsonTree(p);
            if (p instanceof Student) {
                obj.addProperty("type", "Student");
            } else if (p instanceof Professor) {
                obj.addProperty("type", "Professor");
            } else if (p instanceof External) {
                obj.addProperty("type", "External");
            }
            jsonList.add(obj);
        }
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(jsonList, writer);
        }
    }

    // JSON deserialization with type resolution and ID sequence restoration
    public void loadFromJson(String filename) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filename)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            participantsByID.clear();
            participantsByCPF.clear();
            long maxId = 0;
            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                String type = obj.get("type").getAsString();
                Participant p = null;
                switch (type) {
                    case "Student":
                        p = gson.fromJson(obj, Student.class);
                        break;
                    case "Professor":
                        p = gson.fromJson(obj, Professor.class);
                        break;
                    case "External":
                        p = gson.fromJson(obj, External.class);
                        break;
                }
                if (p != null) {
                    addParticipant(p);
                    if (p.getId() > maxId) {
                        maxId = p.getId();
                    }
                }
            }
            Participant.setNextID(maxId + 1);
        }
    }
}
