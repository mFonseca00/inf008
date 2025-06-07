package acad_events.acadevents.common.dtos.event;

public class LectureDTO extends EventDTO{
    private String speaker;

    public LectureDTO(EventDTO base) {
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

    public LectureDTO() {}

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
}
