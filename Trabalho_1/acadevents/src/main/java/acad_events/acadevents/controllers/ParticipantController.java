package acad_events.acadevents.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import acad_events.acadevents.common.dtos.participant.ExternalDTO;
import acad_events.acadevents.common.dtos.participant.ParticipantDTO;
import acad_events.acadevents.common.dtos.participant.ProfessorDTO;
import acad_events.acadevents.common.dtos.participant.StudentDTO;
import acad_events.acadevents.models.participant.entities.External;
import acad_events.acadevents.models.participant.entities.Participant;
import acad_events.acadevents.models.participant.entities.Professor;
import acad_events.acadevents.models.participant.entities.Student;
import acad_events.acadevents.repositories.ParticipantRepository;

/**
 * Controller class for managing participant operations in the AcadEvents system.
 * Handles business logic for CRUD operations and participant type management.
 * Acts as intermediary between UI layer and ParticipantRepository for data persistence.
 * 
 * Key features:
 * - Manages three participant types: Student, Professor, and External with specific attributes
 * - Prevents duplicate CPF registration (business rule enforcement)
 * - Converts between Entity and DTO objects for clean layer separation
 * - Provides CPF-based participant lookup and validation
 * - Used extensively by ParticipantFunctionalities and IntegrationController
 * 
 * Integration: Works with TestDataGenerator for test data creation and supports
 * participant enrollment in events through IntegrationController
 */
public class ParticipantController {

    ParticipantRepository repository = new ParticipantRepository();

    public ParticipantRepository getRepository() {
        return repository;
    }

    // Business rule validation: ensures CPF uniqueness across the system
    public boolean existsByCPF(String cpf) {
        return repository.getParticipantByCPF(cpf) != null;
    }

    // Entity-to-DTO conversion with polymorphic handling for different participant types
    private ParticipantDTO toDTO(Participant p) {
        if (p == null) return null;

        if (p instanceof Student) {
            Student s = (Student) p;
            StudentDTO dto = new StudentDTO();
            dto.setCpf(s.getCPF());
            dto.setName(s.getName());
            dto.setEmail(s.getEmail());
            dto.setPhone(s.getPhone());
            dto.setEnrollment(s.getEnrollment());
            return dto;
        } else if (p instanceof Professor) {
            Professor prof = (Professor) p;
            ProfessorDTO dto = new ProfessorDTO();
            dto.setCpf(prof.getCPF());
            dto.setName(prof.getName());
            dto.setEmail(prof.getEmail());
            dto.setPhone(prof.getPhone());
            dto.setEmployeeId(prof.getEmployeeId());
            dto.setDepartment(prof.getDepartment());
            return dto;
        } else if (p instanceof External) {
            External ext = (External) p;
            ExternalDTO dto = new ExternalDTO();
            dto.setCpf(ext.getCPF());
            dto.setName(ext.getName());
            dto.setEmail(ext.getEmail());
            dto.setPhone(ext.getPhone());
            dto.setOrganization(ext.getOrganization());
            dto.setRole(ext.getRole());
            return dto;
        } else {
            ParticipantDTO dto = new ParticipantDTO();
            dto.setCpf(p.getCPF());
            dto.setName(p.getName());
            dto.setEmail(p.getEmail());
            dto.setPhone(p.getPhone());
            return dto;
        }
    }

    // CPF-based participant lookup (used for enrollment and certificate generation)
    public ParticipantDTO findParticipantByCPF(String cpf){
        Participant participant = repository.getParticipantByCPF(cpf);
        return toDTO(participant);
    }

    // Type-specific registration methods with duplicate CPF prevention
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

    // Participant removal (triggers cleanup in IntegrationController to unenroll from events)
    public boolean delete(String CPF) {
        boolean response = repository.removeParticipantByCPF(CPF);
        return response;
    }

    // Simplified listing for display purposes (returns basic participant info)
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
