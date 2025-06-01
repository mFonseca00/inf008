package acad_events.acadevents.models.participant.entities;

import acad_events.acadevents.models.participant.enums.ExternalRole;

public class External extends Participant{
    private String organization;
    private ExternalRole role;

    public External(String CPF, String name, String email, String phone, String organization, ExternalRole role) {
        super(CPF, name, email, phone);
        this.organization = organization;
        this.role = role;
    }

    @Override
    public String toString() {
        return "External participant" + super.toString() + " organization=" + organization + ", role=" + role + "]";
    }

    public String getOrganization() {
        return organization;
    }

    public ExternalRole getRole() {
        return role;
    }

}
