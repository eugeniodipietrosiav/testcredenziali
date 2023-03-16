//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.model;

public class AttemptInput {
    private int id;
    private String username;
    private String time;
    private String result;
    private String type;

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

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String isResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return "" + this.result;
    }

    public AttemptInput(int id, String username, String time, String result, String type) {
        this.id = id;
        this.username = username;
        this.time = time;
        this.result = result;
        this.type = type;
    }

    public AttemptInput() {
    }
}
