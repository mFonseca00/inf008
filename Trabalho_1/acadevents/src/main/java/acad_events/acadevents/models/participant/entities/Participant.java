package acad_events.acadevents.models.participant.entities;

import java.util.Objects;

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

    public static void setNextID(long next) {
        nextID = next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false; // Pode ser ajustado se a comparação entre subclasses for desejada de forma diferente
        Participant that = (Participant) o;
        return Objects.equals(CPF, that.CPF);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CPF);
    }
}
