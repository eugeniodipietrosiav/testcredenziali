//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.service;

import com.autenticazione.testcredenziali.controller.LoginController;
import com.autenticazione.testcredenziali.model.LoginAttempts;
import com.autenticazione.testcredenziali.model.Roles;
import com.autenticazione.testcredenziali.model.Users;
import com.autenticazione.testcredenziali.repository.LoginAttemptsRepository;
import com.autenticazione.testcredenziali.repository.RoleRepository;
import com.autenticazione.testcredenziali.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private static ThreadLocal<String> transactionId = new ThreadLocal();
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LoginAttemptsRepository loginAttemptRepository;
    BCryptPasswordEncoder passwordEncoder;
    private static final String FIXED_SALT = "a0a4310f19";
    @Value("${password.regex}")
    private String passwordRegex;
    @Value("${login.attempts.max}")
    private int maxLoginAttempts;
    @Value("${cooldown}")
    private int cooldown;
    public boolean isUserBanned = false;
    Clock cl = Clock.systemUTC();

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String hashPassword(String password, String transactionId) {
        try {
            String saltedPassword = "a0a4310f19" + password;
            String hashedPassword = BCrypt.hashpw(saltedPassword, BCrypt.gensalt());
            logger.info(transactionId + " ___ password hashing success");
            return hashedPassword;
        } catch (Exception var5) {
            logger.info(transactionId + " ___ password hashing failed");
            return null;
        }
    }

    public boolean passwordCheck(Users user, String transactionId) {
        try {
            logger.info(transactionId + " ___ password regex check success");
            return user.getPassword().matches(this.passwordRegex);
        } catch (Exception var4) {
            logger.info(transactionId + " ___ password regex check success");
            return false;
        }
    }

    public boolean register(Users users, String transactionId) {
        logger.info(transactionId + " ___ inizio tentativo registrazione da user:" + users.getUsername());
        if (this.userRepository.findByUsernameIgnoreCase(users.getUsername()) == null && this.passwordCheck(users, transactionId)) {
            Set<Roles> storedRoles = new HashSet();
            Iterator var4 = users.getRoles().iterator();

            while(var4.hasNext()) {
                Roles role = (Roles)var4.next();
                if (this.roleRepository.findByRoleName(role.getRoleName()) == null) {
                    logger.error(transactionId + " ___ impossibile registrare utente");
                    return false;
                }

                storedRoles.add(this.roleRepository.findByRoleName(role.getRoleName()));
            }

            users.setRoles(storedRoles);
            String hashedPsw = this.hashPassword(users.getPassword(), transactionId);
            users.setPassword(hashedPsw);
            users.setLoginAttemptCounter(0);
            this.userRepository.save(users);
            logger.info(transactionId + " ___ utente registrato correttamente.");
            return true;
        } else {
            logger.error(transactionId + " ___ impossibile registrare utente");
            return false;
        }
    }

    public static boolean checkPassword(String password, String hashedPassword, String transactionId) {
        try {
            logger.info(transactionId + "encrypted password matching check success");
            String saltedPassword = "a0a4310f19" + password;
            return BCrypt.checkpw(saltedPassword, hashedPassword);
        } catch (Exception var4) {
            logger.info(transactionId + "encrypted password matching check failed");
            return false;
        }
    }

    public boolean login(Users user, String transactionId) {
        String[] userInfo = user.getUsername().split("/");
        String username = userInfo[1];
        String role = userInfo[0];
        System.out.println(username);
        System.out.println(role);
        Users storedUser = this.userRepository.findByUsernameAndRole(username, role);
        logger.info(transactionId + " ___ inizio tentativo login da user:" + username);
        if (storedUser == null) {
            logger.error(transactionId + " ___ login utente non valido.");
            return false;
        } else {
            LoginAttempts loginAttempt;
            if (storedUser.getLoginAttemptCounter() >= this.maxLoginAttempts && !storedUser.isBanned()) {
                storedUser.setBanned(true);
                this.isUserBanned = true;
                storedUser.setTimeOfBan(LocalDateTime.now(this.cl));
                this.userRepository.save(storedUser);
                logger.info(transactionId + " ___ l'utente " + username + "e' disabilitato.");
                loginAttempt = new LoginAttempts(storedUser, LocalDateTime.now(this.cl), false, "Banned");
                this.loginAttemptRepository.save(loginAttempt);
                return true;
            } else {
                if (storedUser.isBanned()) {
                    if (!storedUser.getTimeOfBan().isBefore(LocalDateTime.now(this.cl).minusMinutes((long)this.cooldown))) {
                        logger.info(transactionId + " ___ l'utente " + username + "e' disabilitato.");
                        loginAttempt = new LoginAttempts(storedUser, LocalDateTime.now(this.cl), false, "Banned");
                        this.loginAttemptRepository.save(loginAttempt);
                        return true;
                    }

                    storedUser.setBanned(false);
                    this.isUserBanned = false;
                    storedUser.setLoginAttemptCounter(0);
                }

                if (!checkPassword(user.getPassword(), this.userRepository.findByUsernameAndRole(username, role).getPassword(), transactionId)) {
                    storedUser.setLoginAttemptCounter(storedUser.getLoginAttemptCounter() + 1);
                    this.userRepository.save(storedUser);
                    logger.error(transactionId + " ___ login utente non valido.");
                    loginAttempt = new LoginAttempts(storedUser, LocalDateTime.now(this.cl), false, "Password");
                    this.loginAttemptRepository.save(loginAttempt);
                    return false;
                } else {
                    storedUser.setLoginAttemptCounter(0);
                    this.isUserBanned = false;
                    storedUser.setBanned(false);
                    this.userRepository.save(storedUser);
                    loginAttempt = new LoginAttempts(storedUser, LocalDateTime.now(this.cl), true, "Valid");
                    this.loginAttemptRepository.save(loginAttempt);
                    logger.info(transactionId + " ___ login utente valido.");
                    return true;
                }
            }
        }
    }

    public boolean newRole(Roles role) {
        if (this.roleRepository.findByRoleName(role.getRoleName()) == null) {
            this.roleRepository.save(role);
            return true;
        } else {
            return false;
        }
    }
}
