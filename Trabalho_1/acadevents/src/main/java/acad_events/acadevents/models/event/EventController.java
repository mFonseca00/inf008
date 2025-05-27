package acad_events.acadevents.models.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import acad_events.acadevents.common.DTOs.eventDTOs.CourseDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.EventDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.FairDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.LectureDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.WorkshopDTO;
import acad_events.acadevents.common.DTOs.participantDTOs.ParticipantDTO;
import acad_events.acadevents.common.utils.Enums.EventAttribute;
import acad_events.acadevents.models.event.entities.Course;
import acad_events.acadevents.models.event.entities.Event;
import acad_events.acadevents.models.event.entities.Fair;
import acad_events.acadevents.models.event.entities.Lecture;
import acad_events.acadevents.models.event.entities.Workshop;
import acad_events.acadevents.models.participant.entities.Participant;

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

    public Collection<EventDTO> listAll() {
        Collection<Event> events = repository.getAllEvents();
        List<EventDTO> eventDTOs = new ArrayList<>();
        for (Event e : events) {
            eventDTOs.add(toDTO(e));
        }
        return eventDTOs;
    }

    public List<EventDTO> listByAtribute(EventAttribute attribute, String value) {
        Collection<Event> events = repository.getAllEvents();
        List<EventDTO> eventDTOs = new ArrayList<>();

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

    public boolean existsEventByTitleAndDate(String title, String date) {
        Collection<Event> events = repository.getAllEvents();
        for (Event event : events) {
            if (event.getTitle().equalsIgnoreCase(title) && event.getDate().equalsIgnoreCase(date)) {
                return true;
            }
        }
        return false;
    }

    // Utilitário para converter Event para o DTO correto
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
        List<ParticipantDTO> participantDTOs = new ArrayList<>();
        for (Participant p : e.getParticipants()) {
            ParticipantDTO pdto = new ParticipantDTO();
            pdto.setCpf(p.getCPF());
            pdto.setName(p.getName());
            pdto.setEmail(p.getEmail());
            pdto.setPhone(p.getPhone());
            participantDTOs.add(pdto);
        }
        dto.setParticipants(participantDTOs);
        return dto;
    }
}
