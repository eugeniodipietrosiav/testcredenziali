//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.repository;

import com.autenticazione.testcredenziali.model.LoginAttempts;
import com.autenticazione.testcredenziali.model.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginAttemptsRepository extends JpaRepository<LoginAttempts, Long> {
    List<LoginAttempts> findAllByUser(Users user);
}
