package acad_events.acadevents.common.dtos.eventdtos;

public class FairDTO extends EventDTO{
    private String organizer;
    private int numberOfStands;

    public FairDTO(EventDTO base) {
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

    public FairDTO() {}

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
