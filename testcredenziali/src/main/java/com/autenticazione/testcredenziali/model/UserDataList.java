//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDataList {
    private List<UserData> userDataList;

    public UserDataList() {
        this.userDataList = new ArrayList();
    }

    public UserDataList(List<UserData> userDataList) {
        this.userDataList = userDataList;
    }

    @XmlElement(
            name = "userData"
    )
    public List<UserData> getUserDataList() {
        return this.userDataList;
    }

    public void setUserDataList(List<UserData> userDataList) {
        this.userDataList = userDataList;
    }
}
