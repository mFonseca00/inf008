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
import acad_events.acadevents.models.event.entities.enums.Modality;
import acad_events.acadevents.models.participant.entities.Participant;

public class EventController {

    EventRepository repository = new EventRepository();

    public EventRepository getRepository(){
        return repository;
    }

    public EventDTO getEventById(Long id){
        Event event = repository.getEventById(id);
        EventDTO dto = new EventDTO();
        if(event != null){
            dto.setId(id);
            dto.setTitle(event.getTitle());
            dto.setCapacity(event.getCapacity());
            dto.setDate(event.getDate());
            dto.setDescription(event.getDescription());
            dto.setLocation(event.getLocation());
            dto.setModality(event.getModality());
            List<ParticipantDTO> participantDTOs = new ArrayList<>();
            for (Participant p : event.getParticipants()) {
                ParticipantDTO pdto = new ParticipantDTO();
                pdto.setCpf(p.getCPF());
                pdto.setName(p.getName());
                pdto.setEmail(p.getEmail());
                pdto.setPhone(p.getPhone());
                participantDTOs.add(pdto);
            }
            dto.setParticipants(participantDTOs);
        }
        return dto;
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
        boolean response = repository.removeEventById(eventId);
        return response;
    }

    public Collection<EventDTO> listAll() {
        Collection<Event> events = repository.getAllEvents();
        List<EventDTO> eventDTOs = new ArrayList<>();
        for (Event e : events) {
            EventDTO eDto = new EventDTO();
            eDto.setTitle(e.getTitle());
            eDto.setDate(e.getDate());
            eDto.setLocation(e.getLocation());
            eDto.setCapacity(e.getCapacity());
            eDto.setDescription(e.getDescription());
            eDto.setModality(e.getModality());
            List<ParticipantDTO> participantDTOs = new ArrayList<>();
            for (Participant p : e.getParticipants()) {
                ParticipantDTO pdto = new ParticipantDTO();
                pdto.setCpf(p.getCPF());
                pdto.setName(p.getName());
                pdto.setEmail(p.getEmail());
                pdto.setPhone(p.getPhone());
                participantDTOs.add(pdto);
            }
            eDto.setParticipants(participantDTOs);

            eventDTOs.add(eDto);
        }
        return eventDTOs;
    }

    public List<EventDTO> listByAtribute(EventAttribute attribute, String value) {
        Collection<Event> events = repository.getAllEvents();
        List<EventDTO> eventDTOs = new ArrayList<>();

        for (Event e : events) {
            boolean matches = false;
            switch (attribute) {
                case TITLE:
                    matches = e.getTitle().equalsIgnoreCase(value);
                    break;
                case DATE:
                    matches = e.getDate().equalsIgnoreCase(value);
                    break;
                case LOCATION:
                    matches = e.getLocation().equalsIgnoreCase(value);
                    break;
                case MODALITY:
                    try {
                        matches = e.getModality().toString().equalsIgnoreCase(value) ||
                                  e.getModality() == Modality.valueOf(value.toUpperCase());
                    } catch (IllegalArgumentException ex) {
                        matches = false;
                    }
                    break;
                case CANCELLED:
                    // This situation should never occur: the terminal/interface logic must filter out the CANCELL option.
                    // Returns an empty list only as a safety measure.
                    return new ArrayList<>();
                // Add attributes here
            }
            if (matches) {
                EventDTO eDto = new EventDTO();
                eDto.setId(e.getId());
                eDto.setTitle(e.getTitle());
                eDto.setDate(e.getDate());
                eDto.setLocation(e.getLocation());
                eDto.setCapacity(e.getCapacity());
                eDto.setDescription(e.getDescription());
                eDto.setModality(e.getModality());
                List<ParticipantDTO> participantDTOs = new ArrayList<>();
                for (Participant p : e.getParticipants()) {
                    ParticipantDTO pdto = new ParticipantDTO();
                    pdto.setCpf(p.getCPF());
                    pdto.setName(p.getName());
                    pdto.setEmail(p.getEmail());
                    pdto.setPhone(p.getPhone());
                    participantDTOs.add(pdto);
                }
                eDto.setParticipants(participantDTOs);
                eventDTOs.add(eDto);
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
}
