package acad_events.acadevents.common.DTOs.eventDTOs;

public class WorkshopDTO extends EventDTO{
    private String instructor;
    private int durationHours;

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
