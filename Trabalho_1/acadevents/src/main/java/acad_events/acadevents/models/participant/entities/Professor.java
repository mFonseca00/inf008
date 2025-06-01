package acad_events.acadevents.models.participant.entities;

import acad_events.acadevents.models.participant.enums.AcademyDepartment;

public class Professor extends Participant{
    private String employeeId;
    private AcademyDepartment department;

    
    public Professor(String CPF, String name, String email, String phone, String employeeId, AcademyDepartment department) {
        super(CPF, name, email, phone);
        this.employeeId = employeeId;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Professor" + super.toString() + " employeeId=" + employeeId + ", department=" + department + "]";
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public AcademyDepartment getDepartment() {
        return department;
    }

}
