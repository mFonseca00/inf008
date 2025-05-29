package acad_events.acadevents.common.DTOs.eventDTOs;

public class WorkshopDTO extends EventDTO{
    private String instructor;
    private int durationHours;

    public WorkshopDTO(EventDTO base) {
        this.setId(base.getId());
        this.setTitle(base.getTitle());
        this.setDate(base.getDate());
        this.setLocation(base.getLocation());
        this.setCapacity(base.getCapacity());
        this.setDescription(base.getDescription());
        this.setModality(base.getModality());
        this.setPresentialParticipants(base.getPresentialParticipants());
        this.setOnlineParticipants(base.getOnlineParticipants());
    }

    public WorkshopDTO() {}

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }
}
