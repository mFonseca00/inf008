package acad_events.acadevents.participant;

import acad_events.acadevents.participant.entities.External;
import acad_events.acadevents.participant.entities.Professor;
import acad_events.acadevents.participant.entities.Student;
import acad_events.acadevents.participant.entities.enums.AcademyDepartment;
import acad_events.acadevents.participant.entities.enums.ExternalRole;

public class ParticipantController {

    ParticipantRepository repository = new ParticipantRepository();

    //Register

    // Student
    public boolean register(String name, String CPF, String Email, String phone, String enrollment){
        if(repository.getParticipantByCPF(CPF)!=null){
            Student student = new Student(CPF, name, Email, phone, enrollment);
            repository.addParticipant(student);
            return true;
        }
        return false;
    }
    // Professor
    public boolean register(String name, String CPF, String Email, String phone, String employeeId, AcademyDepartment department){
        if(repository.getParticipantByCPF(CPF)!=null){
            Professor professor = new Professor(CPF, name, Email, phone, employeeId, department);
            repository.addParticipant(professor);
            return true;
        }
        return false;
    }
    // External
    public boolean register(String name, String CPF, String Email, String phone, String enrollment, String organization, ExternalRole role){
        if(repository.getParticipantByCPF(CPF)!=null){
            External external = new External(CPF, name, Email, phone, organization, role);
            repository.addParticipant(external);
            return true;
        }
        return false;
    }

    //Delete


    //List
    public void list(){
        
    }
    //Generate Certificate (buscar checar se o id fornecido corresponde com algum id da lista e gerar certificado)
}
