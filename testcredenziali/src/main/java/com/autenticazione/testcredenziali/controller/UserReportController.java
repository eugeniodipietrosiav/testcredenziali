//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.model.Users;
import com.autenticazione.testcredenziali.repository.LoginAttemptsRepository;
import com.autenticazione.testcredenziali.repository.UserRepository;
import com.autenticazione.testcredenziali.service.Converter;
import com.autenticazione.testcredenziali.service.XMLService;
import com.autenticazione.testcredenziali.service.ZipFolder;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserReportController {
    private static ThreadLocal<String> transactionId = new ThreadLocal();
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginAttemptsRepository loginAttemptsRepository;
    @Autowired
    XMLService xmlService;
    @Autowired
    ZipFolder zipFolder;
    @Autowired
    Converter converter;

    public UserReportController() {
    }

    @GetMapping({"/exportalluser/{username}"})
    public ResponseEntity<?> exportUser(@PathVariable String username) throws IOException {
        transactionId.set(UUID.randomUUID().toString());
        username = username.substring(1, username.length() - 1);
        List<Users> users = this.userRepository.findAll();
        Users user = new Users();

        try {
            System.out.println(username);
            this.xmlService.exportXML(users, user, (String)transactionId.get());
            this.zipFolder.zipFolder(username, (String)transactionId.get());
            this.converter.convert(username, (String)transactionId.get());
            String fileName = "tabella_new" + this.zipFolder.time + "-" + username + ".pdf";
            String filePath = "C:\\Users\\EDipietro\\Downloads\\test_xml\\" + fileName;
            Resource resource = new FileSystemResource(filePath);
            return resource.exists() ? ((ResponseEntity.BodyBuilder)ResponseEntity.ok().header("Content-Disposition", new String[]{"attachment; filename=" + fileName})).body(resource) : new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception var7) {
            var7.printStackTrace();
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
