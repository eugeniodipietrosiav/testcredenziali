//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.model;

public class RoleIDs {
    public String username;
    private String roleName;
    private int ID;

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getID() {
        return this.ID;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public RoleIDs(String username, String roleName, int iD) {
        this.username = username;
        this.roleName = roleName;
        this.ID = iD;
    }

    public RoleIDs() {
    }
}
