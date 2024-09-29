package com.dungmac.quanlymonhoc;

public class Grade {
    private int id;
    private String Name;


    public Grade() {
    }

    public Grade(String name) {
        Name = name;
    }

    public Grade(int id, String name) {
        this.id = id;
        Name = name;
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


