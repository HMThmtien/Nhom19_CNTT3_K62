package com.app.qlns.model;

public class HistoryLogin {
    int id;
    String username;
    String time;

    public HistoryLogin() {
    }

    public HistoryLogin(int id, String username, String time) {
        this.id = id;
        this.username = username;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
