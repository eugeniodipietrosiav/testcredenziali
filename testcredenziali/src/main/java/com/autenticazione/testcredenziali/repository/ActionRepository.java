//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.repository;

import com.autenticazione.testcredenziali.model.Action;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    List<Action> findAllByUserName(String userName);

    @Query("SELECT MAX(a.actionTime) FROM Action a WHERE a.userName = :userName")
    LocalDateTime findLastByUserName(@Param("userName") String userName);
}
