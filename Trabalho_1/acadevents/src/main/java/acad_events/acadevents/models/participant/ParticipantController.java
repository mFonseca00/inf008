package acad_events.acadevents.models.participant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import acad_events.acadevents.common.DTOs.participantDTOs.ExternalDTO;
import acad_events.acadevents.common.DTOs.participantDTOs.ParticipantDTO;
import acad_events.acadevents.common.DTOs.participantDTOs.ProfessorDTO;
import acad_events.acadevents.common.DTOs.participantDTOs.StudentDTO;
import acad_events.acadevents.models.participant.entities.External;
import acad_events.acadevents.models.participant.entities.Participant;
import acad_events.acadevents.models.participant.entities.Professor;
import acad_events.acadevents.models.participant.entities.Student;

public class ParticipantController {

    ParticipantRepository repository = new ParticipantRepository();

    public ParticipantRepository getRepository() {
        return repository;
    }

    public boolean existsByCPF(String cpf) {
        return repository.getParticipantByCPF(cpf) != null;
    }

    public boolean register(StudentDTO dto) {
        if (repository.getParticipantByCPF(dto.getCpf()) == null) {
            Student student = new Student(
                dto.getCpf(),
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getEnrollment()
            );
            repository.addParticipant(student);
            return true;
        }
        return false;
    }

    public boolean register(ProfessorDTO dto) {
        if (repository.getParticipantByCPF(dto.getCpf()) == null) {
            Professor professor = new Professor(
                dto.getCpf(),
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getEmployeeId(),
                dto.getDepartment()
            );
            repository.addParticipant(professor);
            return true;
        }
        return false;
    }

    public boolean register(ExternalDTO dto) {
        if (repository.getParticipantByCPF(dto.getCpf()) == null) {
            External external = new External(
                dto.getCpf(),
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getOrganization(),
                dto.getRole()
            );
            repository.addParticipant(external);
            return true;
        }
        return false;
    }

    public boolean delete(String CPF) {
        boolean response = repository.removeParticipantByCPF(CPF);
        return response;
    }

    public Collection<ParticipantDTO> list() {
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

}
