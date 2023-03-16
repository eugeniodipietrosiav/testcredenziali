//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.model.Roles;
import com.autenticazione.testcredenziali.service.LoginService;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewRoleController {
    private static ThreadLocal<String> transactionId = new ThreadLocal();
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;

    public NewRoleController() {
    }

    @PostMapping({"/newrole"})
    public String newRole(@RequestBody Roles role) {
        transactionId.set(UUID.randomUUID().toString());
        if (this.loginService.newRole(role)) {
            logger.info((String)transactionId.get() + "ruolo aggiunto");
            transactionId.remove();
            return "ruolo aggiunto";
        } else {
            logger.info((String)transactionId.get() + "ruolo gia esistente");
            transactionId.remove();
            return "ruolo gia' esistente";
        }
    }
}
