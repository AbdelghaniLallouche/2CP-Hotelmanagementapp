package com.example.login_page;
public class Room {
    int num;
    String type , status;
    Room(int i , String t , String s){
        this.num = i;
        this.type = t;
        this.status = s;
    }

    public String getType() {
        return type;
    }

    public int getNum() {
        return num;
    }

    public String getStatus() {
        return status;
    }
}
