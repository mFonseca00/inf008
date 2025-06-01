package acad_events.acadevents.models.participant.entities;

public class Student extends Participant {
    private String enrollment;

    public Student(String CPF, String name, String email, String phone, String enrollment) {
        super(CPF, name, email, phone);
        this.enrollment = enrollment;
    }

    @Override
    public String toString() {
        return "Student: " + super. toString() + " enrollment=" + enrollment + "]";
    }

    public String getEnrollment() {
        return enrollment;
    }
    
}
