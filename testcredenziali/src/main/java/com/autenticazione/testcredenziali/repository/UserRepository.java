//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.repository;

import com.autenticazione.testcredenziali.model.Users;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsernameIgnoreCase(String username);

    @Query("SELECT u FROM Users u JOIN u.roles r WHERE u.username = :username AND r.roleName = :role")
    Users findByUsernameAndRole(@Param("username") String username, @Param("role") String role);

    @EntityGraph(
            value = "Users.roles",
            type = EntityGraphType.LOAD
    )
    List<Users> findAll();
}
