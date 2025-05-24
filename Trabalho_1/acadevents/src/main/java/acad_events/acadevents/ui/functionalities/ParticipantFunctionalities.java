package acad_events.acadevents.ui.functionalities;

import java.util.Scanner;

import acad_events.acadevents.ui.functionalities.DTOs.ExternalDTO;
import acad_events.acadevents.ui.functionalities.DTOs.ParticipantDTO;
import acad_events.acadevents.ui.functionalities.DTOs.ProfessorDTO;
import acad_events.acadevents.ui.functionalities.DTOs.StudentDTO;
import acad_events.acadevents.ui.functionalities.enums.InputResult;
import acad_events.acadevents.ui.functionalities.enums.ParticipantTypeOption;
import acad_events.acadevents.ui.functionalities.forms.ParticipantForm;

public class ParticipantFunctionalities {

    public boolean registerNew(Scanner scan){

        ParticipantDTO participant = new ParticipantDTO();
        // solicita os dados comuns (CPF, nome, email, telefone)
        if(ParticipantForm.readCpf(scan, participant) == InputResult.CANCELLED) return false;
        if(ParticipantForm.readName(scan, participant) == InputResult.CANCELLED) return false;
        if(ParticipantForm.readEmail(scan, participant) == InputResult.CANCELLED) return false;
        if(ParticipantForm.readPhone(scan, participant) == InputResult.CANCELLED) return false;

        // seleciona o tipo de participante (student, professor, external) opção para retornar para menu anterior também
        ParticipantTypeOption type = ParticipantForm.selectType(scan);

        // switch case
            // se student, solicita enrollment (matrícula)
            // se professor, solicita emplyeeID e pede para selecionar o departamento (utilizar switch case + enum)
            // se external, solicita organização e pede para selecionar role no evento (utilizar switch case + enum)
        switch(type){
            case STUDENT:
                StudentDTO student = new StudentDTO(participant);
                
                break;
            case PROFESSOR:
                ProfessorDTO professor = new ProfessorDTO(participant);

                break;
            case EXTERNAL:
                ExternalDTO external = new ExternalDTO(participant);

                break;
            case CANCELLED:
                return false;
        }
        
        // chama o método de adição do participantController, passando os parâmetros
        return true;
    }

    public void remove(Scanner scan){
        // solicita o cpf a ser "cancelado"

        // chama o método de remoção do participantController
    }

    public void listAll(){
        // chama o método de listagem do participantController para puxar a collection

        // apresenta o nome, e email de cada participante
    }

    public void insertOnEvent(Scanner scan){
        // solicita o cpf do participante

        //chama o método do eventController para inserir o participante em um evento específico
    }

    public void generateCertificate(Scanner scan){
        // solicita o cpf do participante
        // solicita o código do curso

        // chama o método do eventController para verificar se o participante está inscrito naquele curso
        // chama o método do participantController gerar o certificado em arquivo de texto
    }
}


