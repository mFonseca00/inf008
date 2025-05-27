package acad_events.acadevents.ui.functionalities;

import java.util.Collection;
import java.util.Scanner;

import acad_events.acadevents.common.DTOs.eventDTOs.CourseDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.EventDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.FairDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.LectureDTO;
import acad_events.acadevents.common.DTOs.eventDTOs.WorkshopDTO;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.models.event.EventController;
import acad_events.acadevents.ui.functionalities.enums.EventTypeOption;
import acad_events.acadevents.ui.functionalities.enums.InputResult;
import acad_events.acadevents.ui.functionalities.forms.EventForms.CourseForm;
import acad_events.acadevents.ui.functionalities.forms.EventForms.EventForm;
import acad_events.acadevents.ui.functionalities.forms.EventForms.FairForm;
import acad_events.acadevents.ui.functionalities.forms.EventForms.LectureForm;
import acad_events.acadevents.ui.functionalities.forms.EventForms.WorkshopForm;

public class EventFunctionalities {

    private final EventController eventController;

    public EventFunctionalities(EventController eventController) {
        this.eventController = eventController;
    }

    // cadastrar/criar novo evento (todos os tipos)
    public  boolean create(Scanner scan){

        EventDTO event = new EventDTO();
        // Dados comuns
        if(EventForm.registerTitle(scan, event) == InputResult.CANCELLED) return false;
        if(EventForm.registerDate(scan, event) == InputResult.CANCELLED) return false;

        // Verifica duplicidade
        if(eventController.existsEventByTitleAndDate(event.getTitle(), event.getDate())) {
            TextBoxUtils.printTitle("An event with this title and date already exists!");
            return false;
        }

        if(EventForm.registerLocation(scan, event) == InputResult.CANCELLED) return false;
        if(EventForm.registerCapacity(scan, event) == InputResult.CANCELLED) return false;
        if(EventForm.registerDescription(scan, event) == InputResult.CANCELLED) return false;
        if(EventForm.selectModality(scan, event) == InputResult.CANCELLED) return false;

        EventTypeOption type = EventForm.selectType(scan);

        switch(type){
            case COURSE:
                CourseDTO course = new CourseDTO(event);
                if(CourseForm.registerCoordinator(scan, course) == InputResult.CANCELLED) return false;
                if(CourseForm.registerKnowledgeArea(scan, course) == InputResult.CANCELLED) return false;
                if(CourseForm.registerTotalHours(scan, course) == InputResult.CANCELLED) return false;
                eventController.create(course);
                break;
            case LECTURE:
                LectureDTO lecture = new LectureDTO(event);
                if(LectureForm.registerSpeaker(scan, lecture) == InputResult.CANCELLED) return false;
                eventController.create(lecture);
                break;
            case FAIR:
                FairDTO fair = new FairDTO(event);
                if(FairForm.registerOrganizer(scan, fair) == InputResult.CANCELLED) return false;
                if(FairForm.registerNumberOfStands(scan, fair) == InputResult.CANCELLED) return false;
                eventController.create(fair);
                break;
            case WORKSHOP:
                WorkshopDTO workshop = new WorkshopDTO(event);
                if(WorkshopForm.registerInstructor(scan, workshop) == InputResult.CANCELLED) return false;
                if(WorkshopForm.registerDurationHours(scan, workshop) == InputResult.CANCELLED) return false;
                eventController.create(workshop);
                break;
            case CANCELLED:
                return false;
        }
        
        return false;
    }

    // deletar evento (definir se será utilizada lista geral ou por atributo ou se será solicitado o id)
    public  boolean remove(){
        return false;
    }

    public void listAll(){ // Definir se apenas listará todos ou se dará opção para listar por atributo
        Collection<EventDTO> events = eventController.listAll();
        if(events.isEmpty()){
            TextBoxUtils.printTitle("No event found.");
            return;
        }
        TextBoxUtils.printTitle("Registered events:");
        for(EventDTO e : events){
            TextBoxUtils.printLeftText(e.getClass().getName() + ": " + e.getTitle() + "Modality: " + e.getModality() + "Date: " + e.getDate() + "Location: " + e.getLocation());
            TextBoxUtils.printUnderLineDisplayDivisor();
        }
    }

    // Gerar relatório (por tipo e por data) - deve receber um enum que indique a opção
    public void generateReport(){ // Após concluir, verificar se não deveria estar em uma nova classe
        // Solicita os dados necessários (data(definir se por seleção ou inserção) ou tipo(por seleção))
        // Apresenta o relatório(Não precisa trazer todos os dados) e pergunta se quer exportar ele completo
    }

}
