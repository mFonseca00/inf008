package acad_events.acadevents.ui.functionalities.DTOs;

public class ProfessorDTO extends ParticipantDTO{

    ProfessorDTO(ParticipantDTO basic){
        this.setCpf(basic.getCpf());
        this.setName(basic.getName());
        this.setEmail(basic.getEmail());
        this.setPhone(basic.getPhone());
    }
}
