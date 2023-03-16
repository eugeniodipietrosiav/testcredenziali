//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@NamedEntityGraph(
        name = "Users.roles",
        attributeNodes = {@NamedAttributeNode("roles")}
)
@Entity
@XmlRootElement
public class Users {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private String username;
    @Column(
            nullable = false
    )
    private String password;
    @ManyToMany
    @JoinTable(
            name = "users_roles"
    )
    private Set<Roles> roles;
    @Column
    private int loginAttemptCounter;
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.ALL}
    )
    private List<LoginAttempts> loginAttempts;
    @Column
    private boolean isBanned = false;
    @Column
    private LocalDateTime timeOfBan;
    @Column
    private String token;

    public Users() {
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @XmlElementWrapper(
            name = "loginAttempts"
    )
    @XmlElement(
            name = "loginAttempt"
    )
    public List<LoginAttempts> getLoginAttempts() {
        return this.loginAttempts;
    }

    public void setLoginAttempts(List<LoginAttempts> loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public LocalDateTime getTimeOfBan() {
        return this.timeOfBan;
    }

    public void setTimeOfBan(LocalDateTime timeOfBan) {
        this.timeOfBan = timeOfBan;
    }

    public int getLoginAttemptCounter() {
        return this.loginAttemptCounter;
    }

    public void setLoginAttemptCounter(int loginAttemptCounter) {
        this.loginAttemptCounter = loginAttemptCounter;
    }

    @XmlElement
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElementWrapper(
            name = "roles"
    )
    @XmlElement(
            name = "role"
    )
    public Set<Roles> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public String toString() {
        Integer var10000 = this.id;
        String roleString = "Users [id=" + var10000 + ", username=" + this.username + ", password=" + this.password + ", roles=" + String.valueOf(this.roles) + "]";

        Roles role;
        for(Iterator var2 = this.roles.iterator(); var2.hasNext(); roleString = roleString + role.getRoleName()) {
            role = (Roles)var2.next();
        }

        return roleString;
    }

    public boolean isBanned() {
        return this.isBanned;
    }

    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
