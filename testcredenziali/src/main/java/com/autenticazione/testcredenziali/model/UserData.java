//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.model;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserData {
    private String username;
    private LocalDateTime time;
    private boolean result;
    private String type;

    @XmlElement
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public UserData(String username, LocalDateTime time, boolean result, String type) {
        this.username = username;
        this.time = time;
        this.result = result;
        this.type = type;
    }

    public UserData() {
    }
}
