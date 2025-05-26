package acad_events.acadevents.models.event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import acad_events.acadevents.common.DTOs.eventDTOs.EventDTO;
import acad_events.acadevents.models.event.entities.Event;

public class EventReportController {
    private final EventRepository eventRepo;

    public EventReportController(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    public List<EventDTO> listByType(String type) {
        List<Event> events = eventRepo.getEventsByTipe(type);
        List<EventDTO> dtos = new ArrayList<>();
        for (Event e : events) {
            EventDTO dto = new EventDTO();
            dto.setTitle(e.getTitle());
            dto.setDate(e.getDate());
            dto.setLocation(e.getLocation());
            dto.setCapacity(e.getCapacity());
            dto.setDescription(e.getDescription());
            dto.setModality(e.getModality());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<EventDTO> listByDate(String date) {
        List<Event> events = eventRepo.getEventByDate(date);
        List<EventDTO> dtos = new ArrayList<>();
        for (Event e : events) {
            EventDTO dto = new EventDTO();
            dto.setTitle(e.getTitle());
            dto.setDate(e.getDate());
            dto.setLocation(e.getLocation());
            dto.setCapacity(e.getCapacity());
            dto.setDescription(e.getDescription());
            dto.setModality(e.getModality());
            dtos.add(dto);
        }
        return dtos;
    }

    public void exportReportByTypeToJson(List<EventDTO> dtos, String type, String filename) throws IOException {
        File reportsDir = new File("reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdir();
        }
        String filePath = "reports" + File.separator + filename;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(dtos, writer);
        }
    }
}
