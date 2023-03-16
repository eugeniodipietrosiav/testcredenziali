//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.model.Roles;
import com.autenticazione.testcredenziali.repository.RoleRepository;
import com.autenticazione.testcredenziali.repository.UserRepository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/myroles/{username}"})
public class MyRolesController {
    private static ThreadLocal<String> transactionId = new ThreadLocal();
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    public MyRolesController() {
    }

    @GetMapping
    @ResponseBody
    private List<String> myRoles(@PathVariable String username) {
        transactionId.set(UUID.randomUUID().toString());

        try {
            List<String> roleList = new ArrayList();
            new ArrayList();
            Iterator var4 = this.userRepository.findByUsernameIgnoreCase(username).getRoles().iterator();

            while(var4.hasNext()) {
                Roles role = (Roles)var4.next();
                roleList.add(role.getRoleName());
            }

            logger.info((String)transactionId.get() + " ___ role table preview success");
            transactionId.remove();
            return roleList;
        } catch (Exception var6) {
            logger.error((String)transactionId.get() + " ___ role table preview failed");
            transactionId.remove();
            return null;
        }
    }
}
