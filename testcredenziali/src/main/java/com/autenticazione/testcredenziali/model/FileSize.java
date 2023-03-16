//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.model;

public class FileSize {
    public String name;
    public Long size;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public FileSize(String name, Long size) {
        this.name = name;
        this.size = size;
    }

    public FileSize() {
    }

    public String toString() {
        return "FileSize [name=" + this.name + ", size=" + this.size + "]";
    }
}
