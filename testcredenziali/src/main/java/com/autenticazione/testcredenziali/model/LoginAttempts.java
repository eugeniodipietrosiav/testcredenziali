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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Table
@Entity
@XmlRootElement
public class LoginAttempts {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private Users user;
    @Column
    private LocalDateTime time;
    @Column
    private boolean result;
    @Column
    private String type;

    public LoginAttempts(Users user, LocalDateTime time, boolean result, String type) {
        this.user = user;
        this.time = time;
        this.result = result;
        this.type = type;
    }

    public LoginAttempts() {
    }

    public LoginAttempts(LocalDateTime time2, boolean result2, String type2) {
    }

    public Integer getId() {
        return this.id;
    }

    @XmlElement
    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @XmlElement
    public LocalDateTime getTime() {
        return this.time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @XmlElement
    public boolean isResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @XmlElement
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        Integer var10000 = this.id;
        return "LoginAttempts [id=" + var10000 + ", user=" + String.valueOf(this.user) + ", time=" + String.valueOf(this.time) + ", result=" + this.result + ", type=" + this.type + "]";
    }
}
