package acad_events.acadevents.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import acad_events.acadevents.common.dtos.event.CourseDTO;
import acad_events.acadevents.common.dtos.event.EventDTO;
import acad_events.acadevents.common.dtos.event.FairDTO;
import acad_events.acadevents.common.dtos.event.LectureDTO;
import acad_events.acadevents.common.dtos.event.WorkshopDTO;
import acad_events.acadevents.common.dtos.participant.ParticipantDTO;
import acad_events.acadevents.common.utils.enums.EventAttribute;
import acad_events.acadevents.common.utils.enums.EventType;
import acad_events.acadevents.models.event.entities.Course;
import acad_events.acadevents.models.event.entities.Event;
import acad_events.acadevents.models.event.entities.Fair;
import acad_events.acadevents.models.event.entities.Lecture;
import acad_events.acadevents.models.event.entities.Workshop;
import acad_events.acadevents.models.participant.entities.Participant;
import acad_events.acadevents.repositories.EventRepository;

/**
 * Controller class for managing event operations in the AcadEvents system.
 * Handles business logic for CRUD operations, data validation, and report generation.
 * Acts as intermediary between UI layer and EventRepository for data persistence.
 * 
 * Key features:
 * - Creates different event types (Course, Lecture, Workshop, Fair) with validation
 * - Prevents duplicate events (same title and date)
 * - Provides filtering and search capabilities by various attributes
 * - Exports event reports to JSON with automatic file organization
 * - Converts between Entity and DTO objects for clean separation of concerns
 */
public class EventController {

    EventRepository repository = new EventRepository();

    public EventRepository getRepository(){
        return repository;
    }

    public EventDTO getEventById(Long id){
        Event event = repository.getEventById(id);
        if(event == null) return null;
        return toDTO(event);
    }

    // Event creation methods with business rule validation (no duplicate title+date)
    public boolean create(CourseDTO dto){
        if (existsEventByTitleAndDate(dto.getTitle(), dto.getDate())) {
            return false;
        }
        Course course = new Course(
            dto.getTitle(),
            dto.getDate(),
            dto.getLocation(),
            dto.getCapacity(),
            dto.getDescription(),
            dto.getModality(),
            dto.getCoordinator(),
            dto.getTotalHours(),
            dto.getKnowledgeArea()
        );
        repository.addEvent(course);
        return true;
    }

    public boolean create(LectureDTO dto){
        if (existsEventByTitleAndDate(dto.getTitle(), dto.getDate())) {
            return false;
        }
        Lecture lecture = new Lecture(
            dto.getTitle(),
            dto.getDate(),
            dto.getLocation(),
            dto.getCapacity(),
            dto.getDescription(),
            dto.getModality(),
            dto.getSpeaker()
        );
        repository.addEvent(lecture);
        return true;
    }

    public boolean create(WorkshopDTO dto){
        if (existsEventByTitleAndDate(dto.getTitle(), dto.getDate())) {
            return false;
        }
        Workshop workshop = new Workshop(
            dto.getTitle(),
            dto.getDate(),
            dto.getLocation(),
            dto.getCapacity(),
            dto.getDescription(),
            dto.getModality(),
            dto.getInstructor(),
            dto.getDurationHours()
        );
        repository.addEvent(workshop);
        return true;
    }

    public boolean create(FairDTO dto){
        if (existsEventByTitleAndDate(dto.getTitle(), dto.getDate())) {
            return false;
        }
        Fair fair = new Fair(
            dto.getTitle(),
            dto.getDate(),
            dto.getLocation(),
            dto.getCapacity(),
            dto.getDescription(),
            dto.getModality(),
            dto.getOrganizer(),
            dto.getNumberOfStands()
        );
        repository.addEvent(fair);
        return true;
    }

    public boolean delete(Long eventId){
        return repository.removeEventById(eventId);
    }

    // Business rule: prevents duplicate events with same title and date
    public boolean existsEventByTitleAndDate(String title, String date) {
        Collection<Event> events = repository.getAllEvents();
        if (events == null) return false;
        for (Event event : events) {
            if (event == null) continue;
            String eventTitle = event.getTitle();
            String eventDate = event.getDate();
            if (eventTitle != null && eventDate != null && 
                eventTitle.equalsIgnoreCase(title) && eventDate.equalsIgnoreCase(date)) {
                return true;
        }
        }
        return false;
    }

    public Collection<EventDTO> listAll() {
        Collection<Event> events = repository.getAllEvents();
        List<EventDTO> eventDTOs = new ArrayList<>();
        for (Event e : events) {
            eventDTOs.add(toDTO(e));
        }
        return eventDTOs;
    }

    // Filtering methods for report generation (used by EventFunctionalities)
    public List<EventDTO> listByType(EventType type){
        Collection<Event> events = repository.getEventsByType(type.toString());
        List<EventDTO> eventDTOs = new ArrayList<>();
        if (events == null) return eventDTOs;
        for (Event e : events) {
            eventDTOs.add(toDTO(e));
        }
        return eventDTOs;
    }

    // Flexible search method supporting multiple event attributes
    public List<EventDTO> listByAttribute(EventAttribute attribute, String value) {
        Collection<Event> events = repository.getAllEvents();
        List<EventDTO> eventDTOs = new ArrayList<>();
        if (events == null) return eventDTOs;
        boolean hasValue = value != null && !value.isBlank();
        String valueLower = hasValue ? value.toLowerCase() : "";

        for (Event e : events) {
            boolean matches = false;
            switch (attribute) {
                case TITLE:
                    matches = !hasValue || e.getTitle().toLowerCase().contains(valueLower);
                    break;
                case DATE:
                    matches = !hasValue || e.getDate().toLowerCase().contains(valueLower);
                    break;
                case LOCATION:
                    matches = !hasValue || e.getLocation().toLowerCase().contains(valueLower);
                    break;
                case MODALITY:
                    matches = !hasValue ||
                        e.getModality().toString().toLowerCase().contains(valueLower) ||
                        (e.getModality().name().toLowerCase().contains(valueLower));
                    break;
                case CANCELLED:
                    return new ArrayList<>();
            }
            if (matches) {
                eventDTOs.add(toDTO(e));
            }
        }
        return eventDTOs;
    }

    // Entity-to-DTO conversion with polymorphic handling for different event types
    private EventDTO toDTO(Event e) {
        EventDTO dto;
        if (e instanceof Course) {
            Course c = (Course) e;
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCoordinator(c.getCoordinator());
            courseDTO.setTotalHours(c.getTotalHours());
            courseDTO.setKnowledgeArea(c.getKnowledgeArea());
            dto = courseDTO;
        } else if (e instanceof Lecture) {
            Lecture l = (Lecture) e;
            LectureDTO lectureDTO = new LectureDTO();
            lectureDTO.setSpeaker(l.getSpeaker());
            dto = lectureDTO;
        } else if (e instanceof Fair) {
            Fair f = (Fair) e;
            FairDTO fairDTO = new FairDTO();
            fairDTO.setOrganizer(f.getOrganizer());
            fairDTO.setNumberOfStands(f.getNumberOfStands());
            dto = fairDTO;
        } else if (e instanceof Workshop) {
            Workshop w = (Workshop) e;
            WorkshopDTO workshopDTO = new WorkshopDTO();
            workshopDTO.setInstructor(w.getInstructor());
            workshopDTO.setDurationHours(w.getDurationHours());
            dto = workshopDTO;
        } else {
            dto = new EventDTO();
        }
        dto.setId(e.getId());
        dto.setTitle(e.getTitle());
        dto.setDate(e.getDate());
        dto.setLocation(e.getLocation());
        dto.setCapacity(e.getCapacity());
        dto.setDescription(e.getDescription());
        dto.setModality(e.getModality());

        List<ParticipantDTO> presentialDTOs = new ArrayList<>();
        for (Participant p : e.getPresentialParticipants()) {
            ParticipantDTO pdto = new ParticipantDTO();
            pdto.setCpf(p.getCPF());
            pdto.setName(p.getName());
            pdto.setEmail(p.getEmail());
            pdto.setPhone(p.getPhone());
            presentialDTOs.add(pdto);
        }
        dto.setPresentialParticipants(presentialDTOs);

        List<ParticipantDTO> onlineDTOs = new ArrayList<>();
        for (Participant p : e.getOnlineParticipants()) {
            ParticipantDTO pdto = new ParticipantDTO();
            pdto.setCpf(p.getCPF());
            pdto.setName(p.getName());
            pdto.setEmail(p.getEmail());
            pdto.setPhone(p.getPhone());
            onlineDTOs.add(pdto);
        }
        dto.setOnlineParticipants(onlineDTOs);

        return dto;
    }

    // Report export with automatic directory creation and timestamped filenames
    public String exportReportToJson(List<EventDTO> dtos, String reportOption, String filename) throws IOException {
        String safeReportOption = reportOption.replaceAll("[\\\\/:*?\"<>|\\s]", "_");
        File reportSubDir = new File("reports" + File.separator + safeReportOption);
        if (!reportSubDir.exists()) {
            reportSubDir.mkdirs();
        }

        String safeFilename = filename.replaceAll("[\\\\/:*?\"<>|\\s]", "_").replaceAll("(?i)\\.json$", "");

        String dateStr = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = reportSubDir.getPath() + File.separator + safeFilename + "_" + dateStr + ".json";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(dtos, writer);
        }

        return filePath;
    }
}
