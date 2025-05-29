package acad_events.acadevents.common.DTOs.eventDTOs;

public class CourseDTO extends EventDTO{
    private String coordinator;
    private int totalHours;
    private String knowledgeArea;

    public CourseDTO(EventDTO base) {
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

    public CourseDTO() {}

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }
    
    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public String getKnowledgeArea() {
        return knowledgeArea;
    }

    public void setKnowledgeArea(String knowledgeArea) {
        this.knowledgeArea = knowledgeArea;
    }
}
