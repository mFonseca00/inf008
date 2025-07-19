package br.edu.ifba.inf008.plugins.model;

import br.edu.ifba.inf008.interfaces.models.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;

public class UserViewModel {
    
    private final SimpleIntegerProperty userId;
    private final SimpleStringProperty name;
    private final SimpleStringProperty email;
    private final SimpleObjectProperty<LocalDateTime> registeredAt;
    
    public UserViewModel(User user) {
        this.userId = new SimpleIntegerProperty(user.getUserId());
        this.name = new SimpleStringProperty(user.getName());
        this.email = new SimpleStringProperty(user.getEmail());
        this.registeredAt = new SimpleObjectProperty<>(user.getRegisteredAt());
    }
    
    public Integer getUserId() {
        return userId.get();
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public String getEmail() {
        return email.get();
    }
    
    public void setEmail(String email) {
        this.email.set(email);
    }
    
    public LocalDateTime getRegisteredAt() {
        return registeredAt.get();
    }
    
    public User toUser() {
        User user = new User();
        user.setUserId(getUserId());
        user.setName(getName());
        user.setEmail(getEmail());
        user.setRegisteredAt(getRegisteredAt());
        return user;
    }
    
    public SimpleStringProperty nameProperty() {
        return name;
    }
    
    public SimpleStringProperty emailProperty() {
        return email;
    }
}
