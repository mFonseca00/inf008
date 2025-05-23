package acad_events.acadevents.models.participant.entities;

public abstract class Participant {
    private long Id;
    private static long nextID;
    private String CPF;
    private String name;
    private String email;
    private String phone;

    public Participant(String CPF, String name, String email, String phone) {
        this.Id = nextID++;
        this.CPF = CPF;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return " [Id=" + Id + ", CPF=" + CPF + ", name=" + name + ", email=" + email + ", phone=" + phone;
    }

    public long getId() {
        return Id;
    }

    public String getCPF() {
        return CPF;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

}
