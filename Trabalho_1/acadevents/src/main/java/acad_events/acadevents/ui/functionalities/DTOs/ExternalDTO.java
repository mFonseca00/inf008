package acad_events.acadevents.ui.functionalities.DTOs;

public class ExternalDTO extends ParticipantDTO{

    ExternalDTO(ParticipantDTO basic){
        this.setCpf(basic.getCpf());
        this.setName(basic.getName());
        this.setEmail(basic.getEmail());
        this.setPhone(basic.getPhone());
    }
}
