package acad_events.acadevents.ui.functionalities;

import java.util.Scanner;

public class ParticipantFunctionalities {
    public void registerNew(Scanner scan){
        // solicita os dados comuns (CPF, nome, email, telefone)

        // seleciona o tipo de participante (student, professor, external) opção para retornar para menu anterior também
            // switch case
                // se student, solicita enrollment (matrícula)
                // se professor, solicita emplyeeID e pede para selecionar o departamento (utilizar switch case + enum)
                // se external, solicita organização e pede para selecionar role no evento (utilizar switch case + enum)
        
        // chama o método de adição do participantController, passando os parâmetros
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


