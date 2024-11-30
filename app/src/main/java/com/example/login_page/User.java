package com.example.login_page;

public class User {
    int id;
    String username,pass,type;
    User(int i , String u , String p ,String t){
        this.id = i;
        this.username = u;
        this.pass = p;
        this.type = t;
    }

    public int getId() {
        return id;
    }

    public String getPass() {
        return pass;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }
}
