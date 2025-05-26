package acad_events.acadevents.common.DTOs.eventDTOs;

public class FairDTO extends EventDTO{
    private String organizer;
    private int numberOfStands;

    public String getOrganizer() {
        return organizer;
    }
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
    public int getNumberOfStands() {
        return numberOfStands;
    }
    public void setNumberOfStands(int numberOfStands) {
        this.numberOfStands = numberOfStands;
    }
}
