//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.model.LoginResponse;
import com.autenticazione.testcredenziali.model.Users;
import com.autenticazione.testcredenziali.service.LoginService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private static ThreadLocal<String> transactionId = new ThreadLocal();
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;

    public RegisterController() {
    }

    @PostMapping({"/register"})
    public LoginResponse register(@RequestBody Users user) {
        transactionId.set(UUID.randomUUID().toString());
        logger.info((String)transactionId.get() + " ___ chiamata REST in di registrazione eseguita.");
        String status = "";
        String token = Jwts.builder().setSubject(user.getUsername()).claim("id", user.getId()).claim("username", user.getUsername()).signWith(SignatureAlgorithm.HS256, "secretKey").compact();
        LoginResponse resp = new LoginResponse((String)transactionId.get(), status, token, user.getUsername());
        if (this.loginService.register(user, (String)transactionId.get())) {
            logger.info((String)transactionId.get() + " ___ registrazione completata.");
            transactionId.remove();
            status = "registrazione completata " + user.getUsername();
            resp.setStatus(status);
            return resp;
        } else {
            logger.error((String)transactionId.get() + " ___ errore registrazione.");
            transactionId.remove();
            status = "errore registrazione";
            resp.setStatus(status);
            return resp;
        }
    }
}
