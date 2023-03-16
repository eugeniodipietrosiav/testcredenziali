//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.model.LoginResponse;
import com.autenticazione.testcredenziali.model.Users;
import com.autenticazione.testcredenziali.repository.UserRepository;
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
public class LoginController {
    private static ThreadLocal<String> transactionId = new ThreadLocal();
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserRepository userRepository;

    public LoginController() {
    }

    @PostMapping({"/login"})
    public LoginResponse login(@RequestBody Users user) {
        transactionId.set(UUID.randomUUID().toString());
        logger.info((String)transactionId.get() + "chiamata REST di login eseguita. ID: ");
        String[] userInfo = user.getUsername().split("/");
        String username = userInfo[1];
        System.out.println(username);
        String role = userInfo[0];
        String status = "";
        String token = null;
        LoginResponse resp = new LoginResponse((String)transactionId.get(), status, token, username);
        System.out.println(resp.toString());
        if (this.loginService.login(user, (String)transactionId.get())) {
            if (this.userRepository.findByUsernameAndRole(username, role) == null) {
                logger.info((String)transactionId.get() + " ___ login fallito.");
                transactionId.remove();
                status = "KO";
                resp.setToken((String)null);
                resp.setStatus(status);
                return resp;
            } else {
                Users storedUser = this.userRepository.findByUsernameAndRole(username, role);
                token = Jwts.builder().setSubject(storedUser.getUsername()).claim("id", storedUser.getId()).claim("username", storedUser.getUsername()).claim("timestamp", System.currentTimeMillis()).signWith(SignatureAlgorithm.HS256, "secretKey").compact();
                System.out.println(storedUser.getToken());
                if (storedUser.isBanned()) {
                    logger.info((String)transactionId.get() + " ___ utente disabilitato.");
                    transactionId.remove();
                    status = "l'utente " + storedUser.getUsername() + " e' disabilitato";
                    resp.setToken((String)null);
                    resp.setStatus(status);
                    return resp;
                } else {
                    logger.info((String)transactionId.get() + " ___ login completato.");
                    transactionId.remove();
                    status = "OK ";
                    resp.setStatus(status);
                    resp.setToken(token);
                    this.userRepository.findByUsernameIgnoreCase(username).setToken(token);
                    System.out.println(storedUser.getToken() + "??????");
                    this.userRepository.save(storedUser);
                    return resp;
                }
            }
        } else {
            logger.info((String)transactionId.get() + " ___ login fallito.");
            transactionId.remove();
            status = "KO";
            resp.setToken((String)null);
            resp.setStatus(status);
            return resp;
        }
    }
}
