//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.model.DownloadStatus;
import com.autenticazione.testcredenziali.model.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    ApplicationContext applicationContext;

    public MessageController() {
    }

    @PostMapping({"/sendmessage"})
    public void saveMessage(@RequestBody DownloadStatus message) {
        this.applicationContext.publishEvent(new MessageEvent(this, message.getUsername(), (long)message.getId(), message.getStatus()));
    }
}
