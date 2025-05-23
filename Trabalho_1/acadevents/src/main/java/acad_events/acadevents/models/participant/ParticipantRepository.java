package acad_events.acadevents.models.participant;

import java.util.HashMap;
import java.util.Map;

import acad_events.acadevents.models.participant.entities.Participant;

import java.util.Collection;

public class ParticipantRepository {
    private Map<Long, Participant> participantsByID = new HashMap<>(); // Para buscas e manuseio no sistema
    private Map<String, Participant> participantsByCPF = new HashMap<>(); // Para buscas solicitadas pelo usuário (mais facil compreenssão)

    public void addParticipant(Participant participant){
        participantsByID.put(participant.getId(), participant);
        participantsByCPF.put(participant.getCPF(), participant);
    }

    public Participant getParticipantById(Long Id){
        return participantsByID.get(Id);
    }

    public Participant getParticipantByCPF(String cpf){
        return participantsByCPF.get(cpf);
    }

    public Collection<Participant> getAllParticipants(){
        return participantsByID.values();
    }

    public boolean removeParticipantById(Long Id){
    Participant participant = participantsByID.get(Id);
    if(participant != null){
        participantsByID.remove(Id);
        participantsByCPF.remove(participant.getCPF());
        return true;
    }
    return false;
    }

    public boolean removeParticipantByCPF(String cpf){
        Participant participant = participantsByCPF.get(cpf);
        if(participant != null){
            participantsByCPF.remove(cpf);
            participantsByID.remove(participant.getId());
            return true;
        }
        return false;
    }
}
