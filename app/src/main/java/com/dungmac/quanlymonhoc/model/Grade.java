package com.dungmac.quanlymonhoc.model;

public class Grade {
    private int id;
    private String Name;
    private Boolean Status;

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        this.Status = status;
    }

    public Grade() {
    }

    public Grade(String name) {
        Name = name;
        Status = false;
    }

    public Grade(int id, String name) {
        this.id = id;
        Name = name;
        Status = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {Name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }
}


