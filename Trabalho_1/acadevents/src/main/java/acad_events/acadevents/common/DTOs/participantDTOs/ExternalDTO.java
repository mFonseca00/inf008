package acad_events.acadevents.common.dtos.participantdtos;

import acad_events.acadevents.models.participant.enums.ExternalRole;

public class ExternalDTO extends ParticipantDTO{
    private String organization;
    private ExternalRole role;
    
    public ExternalDTO(ParticipantDTO basic){
        this.setCpf(basic.getCpf());
        this.setName(basic.getName());
        this.setEmail(basic.getEmail());
        this.setPhone(basic.getPhone());
    }

    public ExternalDTO() {
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public ExternalRole getRole() {
        return role;
    }

    public void setRole(ExternalRole role) {
        this.role = role;
    }
}
