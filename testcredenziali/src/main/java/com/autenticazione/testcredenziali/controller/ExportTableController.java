//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.model.AttemptInput;
import com.autenticazione.testcredenziali.model.LoginAttempts;
import com.autenticazione.testcredenziali.repository.LoginAttemptsRepository;
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
@RequestMapping({"/exporttable/{username}"})
public class ExportTableController {
    private static ThreadLocal<String> transactionId = new ThreadLocal();
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginAttemptsRepository loginAttemptsRepository;

    public ExportTableController() {
    }

    @GetMapping
    @ResponseBody
    private List<AttemptInput> retrieveData(@PathVariable String username) {
        transactionId.set(UUID.randomUUID().toString());

        try {
            System.out.println(username);
            List<AttemptInput> dataList = new ArrayList();
            List<LoginAttempts> attemptList = this.loginAttemptsRepository.findAllByUser(this.userRepository.findByUsernameIgnoreCase(username));
            System.out.println(attemptList.toString());
            Iterator var4 = attemptList.iterator();

            while(var4.hasNext()) {
                LoginAttempts attempt = (LoginAttempts)var4.next();
                System.out.println(attempt.toString() + "koko");
                AttemptInput attemptInput = new AttemptInput(attempt.getId(), attempt.getUser().getUsername(), attempt.getTime().toString(), String.valueOf(attempt.isResult()), attempt.getType());
                System.out.println(attempt.isResult() + " RESULT");
                System.out.println(attempt.toString() + "attempt");
                dataList.add(attemptInput);
            }

            System.out.println(String.valueOf(dataList) + "lista");
            logger.info((String)transactionId.get() + " ___ access table preview success");
            transactionId.remove();
            return dataList;
        } catch (Exception var7) {
            logger.error((String)transactionId.get() + " ___ access table preview failed");
            transactionId.remove();
            return null;
        }
    }
}
