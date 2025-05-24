package acad_events.acadevents.models.participant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import acad_events.acadevents.common.DTOs.ExternalDTO;
import acad_events.acadevents.common.DTOs.ProfessorDTO;
import acad_events.acadevents.common.DTOs.StudentDTO;
import acad_events.acadevents.common.DTOs.ParticipantDTO;

import acad_events.acadevents.models.participant.entities.External;
import acad_events.acadevents.models.participant.entities.Participant;
import acad_events.acadevents.models.participant.entities.Professor;
import acad_events.acadevents.models.participant.entities.Student;

public class ParticipantController {

    ParticipantRepository repository = new ParticipantRepository();

    public ParticipantRepository getRepository() {
        return repository;
    }

    // Register

    // Student
    public boolean register(StudentDTO dto) {
        if (repository.getParticipantByCPF(dto.getCpf()) == null) {
            Student student = new Student(dto.getCpf(), dto.getName(), dto.getEmail(), dto.getPhone(),
                    dto.getEnrollment());
            repository.addParticipant(student);
            return true;
        }
        return false;
    }

    // Professor
    public boolean register(ProfessorDTO dto) {
        if (repository.getParticipantByCPF(dto.getCpf()) == null) {
            Professor professor = new Professor(dto.getCpf(), dto.getName(), dto.getEmail(), dto.getPhone(),
                    dto.getEmployeeId(), dto.getDepartment());
            repository.addParticipant(professor);
            return true;
        }
        return false;
    }

    // External
    public boolean register(ExternalDTO dto) {
        if (repository.getParticipantByCPF(dto.getCpf()) == null) {
            External external = new External(dto.getCpf(), dto.getName(), dto.getEmail(), dto.getPhone(),
                    dto.getOrganization(), dto.getRole());
            repository.addParticipant(external);
            return true;
        }
        return false;
    }

    // Delete
    public boolean delete(String CPF) {
        boolean response = repository.removeParticipantByCPF(CPF);
        return response;
    }

    // List
    public Collection<Participant> list() {
        return repository.getAllParticipants();
    }

    public Collection<ParticipantDTO> listDTOs() {
        Collection<Participant> participants = repository.getAllParticipants();
        List<ParticipantDTO> dtos = new ArrayList<>();
        for (Participant p : participants) {
            ParticipantDTO dto = new ParticipantDTO();
            dto.setCpf(p.getCPF());
            dto.setName(p.getName());
            dto.setEmail(p.getEmail());
            dto.setPhone(p.getPhone());
            dtos.add(dto);
        }
        return dtos;
    }

    // Enroll in event (inscrever participante em evento)

    // Generate Certificate (buscar checar se o id fornecido corresponde com algum id da lista e gerar certificado)
}
