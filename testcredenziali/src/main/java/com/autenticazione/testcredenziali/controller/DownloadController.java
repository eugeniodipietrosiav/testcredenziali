//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.model.DownloadStatus;
import com.autenticazione.testcredenziali.repository.DownloadStatusRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadController {
    @Autowired
    DownloadStatusRepository downloadStatusRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    public DownloadController() {
    }

    @PostMapping({"/download"})
    public void downloadReport(@RequestBody DownloadStatus input) {
        DownloadStatus status = new DownloadStatus(input.getStatus(), input.getUsername(), (LocalDateTime)null);
        System.out.println(input.getUsername());
        System.out.println(input.getStatus());
        LocalDateTime now = LocalDateTime.now();
        status.setStartTime(now);
        this.downloadStatusRepository.save(status);
    }
}
