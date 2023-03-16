//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.model;

import org.springframework.context.ApplicationEvent;

public class MessageEvent extends ApplicationEvent {
    private String userName;
    private Long id;
    private String message;

    public MessageEvent(Object source) {
        super(source);
    }

    public MessageEvent(Object source, String userName, Long id, String message) {
        super(source);
        this.userName = userName;
        this.id = id;
        this.message = message;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
