//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.model;

import com.autenticazione.testcredenziali.repository.DownloadStatusRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MessageObserver implements ApplicationListener<ApplicationEvent> {
    @Autowired
    private DownloadStatusRepository downloadStatusRepository;

    public MessageObserver() {
    }

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof MessageEvent messaggioEvent) {
            String userName = messaggioEvent.getUserName();
            String messaggio = messaggioEvent.getMessage();
            Long idMessage = messaggioEvent.getId();
            LocalDateTime now = LocalDateTime.now();
            DownloadStatus messaggioEntity = new DownloadStatus(messaggio, userName, now);
            this.downloadStatusRepository.save(messaggioEntity);
        }

    }
}
