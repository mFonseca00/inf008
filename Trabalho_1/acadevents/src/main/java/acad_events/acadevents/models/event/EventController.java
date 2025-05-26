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

    public boolean register(CourseDTO dto){
        if(repository.getEventByTitle(dto.getTitle()) == null && repository.getEventByDate(dto.getDate()) == null){
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
        return false;
    }
    
    public boolean register(LectureDTO dto){
        if(repository.getEventByTitle(dto.getTitle()) == null && repository.getEventByDate(dto.getDate()) == null){
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
        return false;
    }

    public boolean register(WorkshopDTO dto){
        if(repository.getEventByTitle(dto.getTitle()) == null && repository.getEventByDate(dto.getDate()) == null){
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
        return false;
    }

    public boolean register(FairDTO dto){
        if(repository.getEventByTitle(dto.getTitle()) == null && repository.getEventByDate(dto.getDate()) == null){
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
        return false;
    }

    public boolean delete(Long eventId){
        boolean response = repository.removeEventById(eventId);
        return response;
    }

    public Collection<EventDTO> list() {
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

    public boolean create(CourseDTO dto){
        if(repository.getEventByTitle(dto.getTitle()) == null && repository.getEventByDate(dto.getDate()) == null){
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
        return false;
    }

    public boolean create(LectureDTO dto){
        if(repository.getEventByTitle(dto.getTitle()) == null && repository.getEventByDate(dto.getDate()) == null){
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
        return false;
    }

    public boolean create(WorkshopDTO dto){
        if(repository.getEventByTitle(dto.getTitle()) == null && repository.getEventByDate(dto.getDate()) == null){
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
        return false;
    }

    public boolean create(FairDTO dto){
        if(repository.getEventByTitle(dto.getTitle()) == null && repository.getEventByDate(dto.getDate()) == null){
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
        return false;
    }
}
