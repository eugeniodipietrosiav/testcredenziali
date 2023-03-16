//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Table
@Entity
@XmlRootElement
public class Roles {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private String roleName;
    @ManyToMany
    @JoinTable(
            name = "users_roles"
    )
    private Set<Users> users;

    public Roles() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @XmlElement
    public Set<Users> getUsers() {
        return this.users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }
}
