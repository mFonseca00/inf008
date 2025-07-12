package br.edu.ifba.inf008.interfaces.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String name;

    private String email;

    @Column(name = "registred_at")
    private LocalDateTime registredAt;
    
    // Construtores

    public User() {}

    public User(Long userId, String name, String email, LocalDateTime registredAt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.registredAt = registredAt;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.registredAt = LocalDateTime.now();
    }

    // Getters e Setters

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegistredAt() {
        return registredAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRegistredAt(LocalDateTime registredAt) {
        this.registredAt = registredAt;
    }

    // toString

    @Override
    public String toString() {
        return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", registredAt=" + registredAt + "]";
    }

}
