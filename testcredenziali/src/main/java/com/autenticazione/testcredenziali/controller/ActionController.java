//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.model.Action;
import com.autenticazione.testcredenziali.model.Notification;
import com.autenticazione.testcredenziali.model.Users;
import com.autenticazione.testcredenziali.repository.ActionRepository;
import com.autenticazione.testcredenziali.repository.NotificationRepository;
import com.autenticazione.testcredenziali.repository.UserRepository;
import com.autenticazione.testcredenziali.service.Converter;
import com.autenticazione.testcredenziali.service.DocxToPdfConverter;
import com.autenticazione.testcredenziali.service.XMLService;
import com.autenticazione.testcredenziali.service.ZipFolder;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActionController {
    private static ThreadLocal<String> transactionId = new ThreadLocal();
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    ActionRepository actionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    XMLService xmlService;
    @Autowired
    ZipFolder zipFolder;
    @Autowired
    Converter converter;
    @Autowired
    DocxToPdfConverter docxToPdfConverter;
    @Autowired
    NotificationRepository notificationRepository;
    @Value("${export.path}")
    private String path;

    public ActionController() {
    }

    @PostMapping({"/action"})
    public void action(@RequestBody Action inputAction) {
        LocalDateTime now = LocalDateTime.now();
        inputAction.setActionTime(now);
        String userName = inputAction.getUserName();
        this.actionRepository.save(inputAction);
        System.out.println(inputAction);
        transactionId.set(UUID.randomUUID().toString());
        if (!inputAction.getActionType().equals("mouse click")) {
            Logger var10000 = logger;
            String var10001 = (String)transactionId.get();
            var10000.info(var10001 + " ___ action " + inputAction.getActionType() + ": " + inputAction.getResult());
        }

        int priority = 1;
        if (inputAction.getDescription() != null && (inputAction.getActionType().equals("drive upload") || inputAction.getActionType().equals("drive download") || inputAction.getActionType().equals("drive delete") || inputAction.getActionType().equals("task"))) {
            boolean result;
            if (inputAction.getResult().toLowerCase().equals("success")) {
                result = true;
            } else {
                result = false;
                priority = 2;
            }

            Notification notification = new Notification(userName, inputAction.getActionType().toUpperCase() + ": " + inputAction.getDescription(), result, now, (LocalDateTime)null, priority);
            this.notificationRepository.save(notification);
            System.out.println(notification.getMessage());
        }

        transactionId.remove();
    }

    @GetMapping({"/exportaction/{username}/{token}"})
    public ResponseEntity<?> exportUser(@PathVariable String username, @PathVariable String token) throws IOException {
        transactionId.set(UUID.randomUUID().toString());
        System.out.println(username);
        Users user = this.userRepository.findByUsernameIgnoreCase(username);
        System.out.println(token);
        System.out.println(user.getToken());

        try {
            if (!user.getToken().equals(token)) {
                logger.info((String)transactionId.get() + " ___ action report export not authorized");
                transactionId.remove();
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            } else {
                List<Action> actions = this.actionRepository.findAllByUserName(username);
                Action instance = new Action();
                List<Action> actionInput = new ArrayList();
                Iterator var7 = actions.iterator();

                while(var7.hasNext()) {
                    Action action = (Action)var7.next();
                    if (!action.getActionType().equals("mouse click")) {
                        Action input = new Action(action.getId(), action.getActionType(), action.getUserName(), action.getResult(), action.getActionTime(), action.getDescription());
                        actionInput.add(input);
                    }
                }

                System.out.println(username);
                this.xmlService.exportXML(actionInput, instance, (String)transactionId.get());
                this.zipFolder.zipFolder(username, (String)transactionId.get());
                this.converter.convert(username, (String)transactionId.get());
                String fileName = "\\tabella_new" + this.zipFolder.time + "-" + username + ".pdf";
                System.out.println(fileName);
                String filePath = this.path + fileName;
                System.out.println(filePath);
                Resource resource = new FileSystemResource(filePath);
                if (resource.exists()) {
                    System.out.println("ok");
                    return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().header("Content-Disposition", new String[]{"attachment; filename=" + fileName})).body(resource);
                } else {
                    System.out.println("ko");
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            }
        } catch (Exception var10) {
            logger.error((String)transactionId.get() + " ___ error exporting action report");
            transactionId.remove();
            var10.printStackTrace();
            logger.info((String)transactionId.get() + " ___ action report successfully exported");
            transactionId.remove();
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
