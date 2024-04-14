package com.app.qlns.model;

public class Staff {
    int id;
    String name;
    String phone;

    public Staff(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
    public Staff( String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
    public Staff( ) {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
