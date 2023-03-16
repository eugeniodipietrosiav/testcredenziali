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
public class Action {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    public int id;
    @Column
    public String actionType;
    @Column
    public String userName;
    @Column
    public String result;
    @Column
    public LocalDateTime actionTime;
    @Column
    public String description;

    public String getActionType() {
        return this.actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getActionTime() {
        return this.actionTime;
    }

    public void setActionTime(LocalDateTime actionTime) {
        this.actionTime = actionTime;
    }

    public Action(String actionType, String userName, String result, LocalDateTime actionTime, String description) {
        this.actionType = actionType;
        this.userName = userName;
        this.result = result;
        this.actionTime = actionTime;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Action(int id, String actionType, String userName, String result, LocalDateTime actionTime, String description) {
        this.id = id;
        this.actionType = actionType;
        this.userName = userName;
        this.result = result;
        this.actionTime = actionTime;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Action() {
    }
}
