package acad_events.acadevents.ui.functionalities.DTOs;

public class StudentDTO extends ParticipantDTO{
    private String enrollment;
    
    public StudentDTO(ParticipantDTO basic){
        this.setCpf(basic.getCpf());
        this.setName(basic.getName());
        this.setEmail(basic.getEmail());
        this.setPhone(basic.getPhone());
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getEnrollment() {
        return enrollment;
    }
}
