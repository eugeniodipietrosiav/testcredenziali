//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.model.RoleIDs;
import com.autenticazione.testcredenziali.model.Roles;
import com.autenticazione.testcredenziali.model.Users;
import com.autenticazione.testcredenziali.repository.LoginAttemptsRepository;
import com.autenticazione.testcredenziali.repository.UserRepository;
import com.autenticazione.testcredenziali.service.Converter;
import com.autenticazione.testcredenziali.service.DocxToPdfConverter;
import com.autenticazione.testcredenziali.service.XMLService;
import com.autenticazione.testcredenziali.service.ZipFolder;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExportRoleController {
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
    @Autowired
    DocxToPdfConverter docxToPdfConverter;
    @Value("${export.path}")
    private String path;

    public ExportRoleController() {
    }

    @GetMapping({"/exportroles/{username}/{token}"})
    public ResponseEntity<?> exportRoles(@PathVariable String username, @PathVariable String token) throws IOException {
        transactionId.set(UUID.randomUUID().toString());
        System.out.println(username + "input");
        Users user = this.userRepository.findByUsernameIgnoreCase(username);
        System.out.println(token);
        System.out.println(user.getToken());

        try {
            if (!user.getToken().equals(token)) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            } else {
                List<RoleIDs> roleList = new ArrayList();
                Iterator var5 = user.getRoles().iterator();

                while(var5.hasNext()) {
                    Roles role = (Roles)var5.next();
                    System.out.println(username + "usernameinloop");
                    RoleIDs roleID = new RoleIDs(user.getUsername(), role.getRoleName(), role.getId());
                    roleList.add(roleID);
                }

                RoleIDs instance = new RoleIDs();
                System.out.println(user.getUsername() + " before export");
                this.xmlService.exportXML(roleList, instance, (String)transactionId.get());
                this.zipFolder.zipFolder(username, (String)transactionId.get());
                this.converter.convert(username, (String)transactionId.get());
                String fileName = "\\tabella_new" + this.zipFolder.time + "-" + username + ".pdf";
                System.out.println(fileName);
                String filePath = this.path + fileName;
                System.out.println(filePath);
                Resource resource = new FileSystemResource(filePath);
                if (resource.exists()) {
                    System.out.println("ok");
                    logger.info((String)transactionId.get() + " ___ role table export success");
                    transactionId.remove();
                    return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().header("Content-Disposition", new String[]{"attachment; filename=" + fileName})).body(resource);
                } else {
                    System.out.println("ko");
                    logger.info((String)transactionId.get() + " ___ role table export not found");
                    transactionId.remove();
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            }
        } catch (Exception var9) {
            logger.error((String)transactionId.get() + " ___ role table export failed");
            transactionId.remove();
            var9.printStackTrace();
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
