//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.repository.ActionRepository;
import com.autenticazione.testcredenziali.repository.UserRepository;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenTestController {
    private static ThreadLocal<String> transactionId = new ThreadLocal();
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    ActionRepository actionRepository;
    @Value("${server.servlet.session.timeout}")
    private int timeout;

    public TokenTestController() {
    }

    @GetMapping({"/token/{username}/{token}"})
    public boolean token(@PathVariable String username, @PathVariable String token) throws IOException {
        transactionId.set(UUID.randomUUID().toString());
        System.out.println("tokentest");

        try {
            if (this.userRepository.findByUsernameIgnoreCase(username).getToken() == null) {
                logger.info((String)transactionId.get() + " ___ token null");
                transactionId.remove();
                System.out.println("token null");
                return false;
            }
        } catch (Exception var7) {
            logger.info((String)transactionId.get() + " ___ error checking token");
            transactionId.remove();
            return false;
        }

        if (!this.userRepository.findByUsernameIgnoreCase(username).getToken().equals(token)) {
            System.out.println("token test non valido");
            System.out.println(token);
            System.out.println(username);
            logger.info((String)transactionId.get() + " ___ token non valido");
            transactionId.remove();
            return false;
        } else {
            LocalDateTime lastUserAction = this.actionRepository.findLastByUserName(username);
            Duration duration = Duration.between(lastUserAction, LocalDateTime.now());
            long seconds = duration.getSeconds();
            if (seconds > (long)this.timeout) {
                System.out.println(seconds);
                System.out.println("sessione scaduta");
                System.out.println(token);
                System.out.println(username);
                logger.info((String)transactionId.get() + " ___ sessione scaduta");
                transactionId.remove();
                return false;
            } else {
                System.out.println("token test valido");
                System.out.println(token);
                System.out.println(username);
                logger.info((String)transactionId.get() + " ___ token valido");
                transactionId.remove();
                return true;
            }
        }
    }
}
