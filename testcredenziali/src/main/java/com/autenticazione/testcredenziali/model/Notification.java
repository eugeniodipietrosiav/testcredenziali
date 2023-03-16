//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Table
@Entity
public class Notification {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    public int id;
    @Column
    public String username;
    @Column
    public String message;
    @Column
    public boolean successful;
    @Column
    public LocalDateTime timestamp;
    @Column
    public LocalDateTime expirationDate;
    @Column
    public int priority;
    @Column
    public boolean viewed;

    public boolean isViewed() {
        return this.viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessful() {
        return this.successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public LocalDateTime getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Notification(String username, String message, boolean successful, LocalDateTime timestamp, LocalDateTime expirationDate, int priority) {
        this.username = username;
        this.message = message;
        this.successful = successful;
        this.timestamp = timestamp;
        this.expirationDate = expirationDate;
        this.priority = priority;
    }

    public Notification() {
    }
}
