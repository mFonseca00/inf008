package acad_events.acadevents.common.DTOs;

import acad_events.acadevents.common.enums.AcademyDepartment;

public class ProfessorDTO extends ParticipantDTO{

    private String employeeId;
    private AcademyDepartment department;

    public ProfessorDTO(ParticipantDTO basic){
        this.setCpf(basic.getCpf());
        this.setName(basic.getName());
        this.setEmail(basic.getEmail());
        this.setPhone(basic.getPhone());
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public AcademyDepartment getDepartment() {
        return department;
    }

    public void setDepartment(AcademyDepartment department) {
        this.department = department;
    }
}
