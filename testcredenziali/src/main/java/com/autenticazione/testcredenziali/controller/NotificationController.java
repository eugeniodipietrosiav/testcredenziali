//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.model.Notification;
import com.autenticazione.testcredenziali.repository.NotificationRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationController() {
    }

    @GetMapping({"/notifications/{username}"})
    public ResponseEntity<?> getNotifications(@PathVariable String username) {
        System.out.println("NOTIFICATION CONTROLLER");

        try {
            if (this.notificationRepository.findAllByUsernameOrderByTimestampDesc(username) != null) {
                System.out.println("NOTIF USERNAME NOT NULL ");
                List<Notification> notificationList = this.notificationRepository.findAllByUsernameOrderByTimestampDesc(username);

                for(int i = 0; i < notificationList.size(); ++i) {
                    Notification element = (Notification)notificationList.get(i);
                    if (element.getExpirationDate() != null && LocalDateTime.now().isAfter(element.getExpirationDate())) {
                        notificationList.remove(i);
                        --i;
                    }
                    else if(element.getExpirationDate() != null && (ChronoUnit.SECONDS.between(LocalDateTime.now(), element.getExpirationDate())<=60)){
                        System.out.println(ChronoUnit.MINUTES.between(LocalDateTime.now(), element.getExpirationDate()));
                        System.out.println("DIFF");
                        element.setPriority(3);
                        notificationRepository.save(element);
                    }
                }

                return new ResponseEntity(notificationList, HttpStatus.OK);
            } else {
                System.out.println("NOTIF USER NULL");
                return new ResponseEntity((MultiValueMap)null, HttpStatus.OK);
            }
        } catch (Exception var5) {
            System.out.println("ERROR");
            if (this.notificationRepository.findAllByUsernameOrderByTimestampDesc(username) != null) {
                System.out.println("NOTIF USERNAME NOT NULL ");
            }

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping({"/viewnotification/{id}"})
    public ResponseEntity<?> viewNotification(@PathVariable int id) {
        System.out.println("VIEWNOTIF START");

        try {
            LocalDateTime now = LocalDateTime.now();
            Notification notification = this.notificationRepository.findById(id);
            notification.setViewed(true);
            if (notification.getExpirationDate() == null) {
                notification.setExpirationDate(now.plusMinutes(2));
                System.out.println(LocalDateTime.now());

                System.out.println(notification.getExpirationDate());
                System.out.println("NEW EXPIRATION DATE");
                System.out.println(ChronoUnit.SECONDS.between(LocalDateTime.now(), notification.getExpirationDate()));
            }

            this.notificationRepository.save(notification);
            return new ResponseEntity((MultiValueMap)null, HttpStatus.OK);
        } catch (Exception var4) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
