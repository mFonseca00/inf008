package acad_events.acadevents.common.utils;

import acad_events.acadevents.common.dtos.event.*;
import acad_events.acadevents.common.dtos.participant.*;
import acad_events.acadevents.controllers.EventController;
import acad_events.acadevents.controllers.ParticipantController;
import acad_events.acadevents.models.event.enums.Modality;
import acad_events.acadevents.models.participant.enums.AcademyDepartment;
import acad_events.acadevents.models.participant.enums.ExternalRole;

import java.util.Random;

public class TestDataGenerator {

    private static final String[] EVENT_TITLES = {
        "Java a fundo", "Sobre IA", "React na pratica", "Tecnologias incriveis", "QT na pratica",
        "Desenvolvimento Web Moderno", "Seguranca Cibernetica Essencial", "Computacao em Nuvem",
        "Machine Learning Aplicado", "Big Data Analytics", "DevOps e Automacao", "Blockchain Descomplicado",
        "UX UI Design Thinking", "Gestao de Projetos Ageis", "Marketing Digital",
        "Python para Ciencia de Dados", "Arquitetura de Microservicos", "Internet das Coisas IoT",
        "Realidade Virtual e Aumentada", "Desenvolvimento de Jogos Digitais", "Redes Neurais Artificiais",
        "Computacao Quantica", "Etica em Inteligencia Artificial", "Lideranca em Tecnologia",
        "Inovacao Aberta e Corporativa"
    };
    private static final String[] PARTICIPANT_NAMES = {
        "Joao Silva", "Maria Souza", "Carlos Pereira", "Ana Oliveira", "Lucas Santos", "Juliana Costa",
        "Pedro Almeida", "Fernanda Lima", "Marcos Rocha", "Beatriz Azevedo", "Ricardo Nunes", "Camila Dias",
        "Gustavo Borges", "Leticia Farias", "Bruno Gomes", "Alice Ferreira", "Thiago Martins", "Laura Barbosa",
        "Rafael Ribeiro", "Sofia Castro", "Daniel Araujo", "Manuela Cardoso", "Enzo Pinto", "Valentina Monteiro",
        "Gabriel Correia"
    };
    private static final String[] KNOWLEDGE_AREAS = {
        "Tecnologia", "Inovacao", "Gestao", "Marketing", "Engenharia de Software", "Ciencia de Dados",
        "Redes de Computadores", "Inteligencia Artificial", "Desenvolvimento Mobile", "Financas",
        "Recursos Humanos", "Logistica", "Saude Digital", "Educacao Tecnologica", "Sustentabilidade",
        "Direito Digital", "Agronegocio Tecnologico", "Energias Renovaveis", "Biotecnologia", "Industria Criativa",
        "Turismo Tecnologico", "Cidades Inteligentes", "Robotica Aplicada", "Comunicacao Social", "Psicologia Organizacional"
    };
    private static final String[] ORGANIZATIONS = {
        "IFBA", "UFBA", "SENAI", "UNEB", "Google", "Microsoft", "Amazon", "Oracle", "IBM", "Apple",
        "Facebook", "Twitter", "Nubank", "Magazine Luiza", "Globo", "Petrobras", "Vale", "Ambev",
        "Natura", "Itau Unibanco", "Bradesco", "Santander", "Embraer", "TOTVS", "Stefanini"
    };
    private static final String[] INSTRUCTORS = {
        "Alberto", "Roberta", "Joao", "Maria", "Fabio",
        "Laura", "Andre", "Sofia", "Felipe", "Gabriela",
        "Rafael", "Isabela", "Tiago", "Helena", "Vinicius",
        "Davi", "Clara", "Miguel", "Eloisa", "Heitor",
        "Lorena", "Igor", "Yasmin", "Arthur", "Cecilia"
    };
    private static final String[] SPEAKERS = {
        "Doutor Carlos", "Mestre Ana", "Especialista Pedro", "Especialista Carla", "Pesquisador Sergio",
        "Pesquisadora Monica", "Doutor Ricardo", "Mestre Patricia", "Especialista Daniel", "Especialista Vanessa",
        "Consultor Eduardo", "Consultora Tatiana", "Doutor Marcelo", "Mestre Carolina", "Especialista Leonardo",
        "Doutora Bianca", "Mestre Guilherme", "Especialista Luiza", "Pesquisador Rodrigo", "Pesquisadora Natalia",
        "Doutor Samuel", "Mestre Vitoria", "Especialista Henrique", "Consultora Beatriz", "Doutora Julia"
    };
    private static final String[] COORDINATORS = {
        "Marcos", "Juliana", "Paulo", "Patricia", "Antonio",
        "Sandra", "Fernando", "Beatriz", "Rogerio", "Debora",
        "Cristiano", "Viviane", "Samuel", "Amanda", "Nelson",
        "Diego", "Renata", "Alex", "Priscila", "Flavio",
        "Elaine", "Jose", "Fatima", "Roberto", "Simone"
    };

    private static final Random random = new Random();

    public static void generateRandomParticipant(ParticipantController participantController) {
        int type = random.nextInt(3); // 0: Student, 1: Professor, 2: External
        String cpf;
        do {
            cpf = generateRandomCPF();
        } while (participantController.existsByCPF(cpf));

        String name = PARTICIPANT_NAMES[random.nextInt(PARTICIPANT_NAMES.length)];
        String email = generateRandomEmail(name);
        String phone = generateRandomPhone();

        switch (type) {
            case 0: // Student
                StudentDTO student = new StudentDTO(new ParticipantDTO());
                student.setCpf(cpf);
                student.setName(name);
                student.setEmail(email);
                student.setPhone(phone);
                student.setEnrollment(generateRandomEnrollment());
                participantController.register(student);
                break;
            case 1: // Professor
                ProfessorDTO professor = new ProfessorDTO(new ParticipantDTO());
                professor.setCpf(cpf);
                professor.setName(name);
                professor.setEmail(email);
                professor.setPhone(phone);
                professor.setEmployeeId(generateRandomEmployeeId());
                professor.setDepartment(getRandomAcademyDepartment());
                participantController.register(professor);
                break;
            case 2: // External
                ExternalDTO external = new ExternalDTO(new ParticipantDTO());
                external.setCpf(cpf);
                external.setName(name);
                external.setEmail(email);
                external.setPhone(phone);
                external.setOrganization(ORGANIZATIONS[random.nextInt(ORGANIZATIONS.length)]);
                external.setRole(getRandomExternalRole());
                participantController.register(external);
                break;
        }
    }

    public static void generateRandomEvent(EventController eventController) {
        int type = random.nextInt(4); // 0: Course, 1: Lecture, 2: Fair, 3: Workshop
        String title = EVENT_TITLES[random.nextInt(EVENT_TITLES.length)];
        String date = generateRandomDate();
        String location = "Local de Teste";
        int capacity = 10 + random.nextInt(100);
        String description = "Uma simples descricao Teste";
        Modality modality = getRandomModality();

        switch (type) {
            case 0: // Course
                CourseDTO course = new CourseDTO(new EventDTO());
                course.setTitle(title);
                course.setDate(date);
                course.setLocation(location);
                course.setCapacity(capacity);
                course.setDescription(description);
                course.setModality(modality);
                course.setCoordinator(COORDINATORS[random.nextInt(COORDINATORS.length)]);
                course.setKnowledgeArea(KNOWLEDGE_AREAS[random.nextInt(KNOWLEDGE_AREAS.length)]);
                course.setTotalHours(40 + random.nextInt(80));
                eventController.create(course);
                break;
            case 1: // Lecture
                LectureDTO lecture = new LectureDTO(new EventDTO());
                lecture.setTitle(title);
                lecture.setDate(date);
                lecture.setLocation(location);
                lecture.setCapacity(capacity);
                lecture.setDescription(description);
                lecture.setModality(modality);
                lecture.setSpeaker(SPEAKERS[random.nextInt(SPEAKERS.length)]);
                eventController.create(lecture);
                break;
            case 2: // Fair
                FairDTO fair = new FairDTO(new EventDTO());
                fair.setTitle(title);
                fair.setDate(date);
                fair.setLocation(location);
                fair.setCapacity(capacity);
                fair.setDescription(description);
                fair.setModality(modality);
                fair.setOrganizer("Empresa de Teste");
                fair.setNumberOfStands(5 + random.nextInt(20));
                eventController.create(fair);
                break;
            case 3: // Workshop
                WorkshopDTO workshop = new WorkshopDTO(new EventDTO());
                workshop.setTitle(title);
                workshop.setDate(date);
                workshop.setLocation(location);
                workshop.setCapacity(capacity);
                workshop.setDescription(description);
                workshop.setModality(modality);
                workshop.setInstructor(INSTRUCTORS[random.nextInt(INSTRUCTORS.length)]);
                workshop.setDurationHours(8 + random.nextInt(24));
                eventController.create(workshop);
                break;
        }
    }

    private static String generateRandomCPF() {
        Random random = new Random();
        StringBuilder baseCpf = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            baseCpf.append(random.nextInt(10));
        }
        String base = baseCpf.toString();

        int digit1 = calculateVerificationDigit(base, 10);
        int digit2 = calculateVerificationDigit(base + digit1, 11);

        String cpf =  base + digit1 + digit2;
        return ValidatorInputs.formatCPF(cpf);
    }

    private static int calculateVerificationDigit(String base, int weight) {
        int sum = 0;
        for (int i = 0; i < base.length(); i++) {
            sum += Character.getNumericValue(base.charAt(i)) * weight--;
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : (11 - remainder);
    }

    private static String generateRandomEmail(String name) {
        String email;
        email = name.toLowerCase().replace(" ", "_") + "@email.com";
        return email;
           
    }

    private static String generateRandomPhone() {
        Random random = new Random();
        int formatType = random.nextInt(4);
        String phone = "";

        switch (formatType) {
            case 0: // xx xxxx-xxxx
                phone = String.format("%02d %04d-%04d", random.nextInt(100), random.nextInt(10000), random.nextInt(10000));
                break;
            case 1: // xx xxxxx-xxxx
                phone = String.format("%02d %05d-%04d", random.nextInt(100), random.nextInt(100000), random.nextInt(10000));
                break;
            case 2: // xxx xxxx-xxxx
                phone = String.format("%03d %04d-%04d", random.nextInt(1000), random.nextInt(10000), random.nextInt(10000));
                break;
            case 3: // xxx xxxxx-xxxx
                phone = String.format("%03d %05d-%04d", random.nextInt(1000), random.nextInt(100000), random.nextInt(10000));
                break;
        }

        return phone;
    }

    private static String generateRandomEnrollment() {
        return String.valueOf(20250000 + random.nextInt(1000));
    }

    private static String generateRandomEmployeeId() {
        return String.valueOf(1000 + random.nextInt(9000));
    }

    private static AcademyDepartment getRandomAcademyDepartment() {
        AcademyDepartment[] departments = AcademyDepartment.values();
        return departments[random.nextInt(departments.length)];
    }

    private static ExternalRole getRandomExternalRole() {
        ExternalRole[] roles = ExternalRole.values();
        return roles[random.nextInt(roles.length)];
    }

    private static String generateRandomDate() {
        String date;
        while (true) {
            int day = 1 + random.nextInt(28);
            int month = 1 + random.nextInt(12);
            int year = 2025 + random.nextInt(6);
            date = String.format("%02d/%02d/%d", day, month, year);
            if (ValidatorInputs.isValidDate(date)) {
                return date;
            }
        }
    }

    private static Modality getRandomModality() {
        Modality[] modalities = Modality.values();
        return modalities[random.nextInt(modalities.length)];
    }
}