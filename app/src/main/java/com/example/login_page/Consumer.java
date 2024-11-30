package com.example.login_page;

import java.sql.Date;

public class Consumer {
    String name , card , phone ;
    Date from , to;
    Consumer(String n , String c , String p ,Date f , Date t){
        this.name = n;
        this.card = c;
        this.phone = p;
        this.from = f;
        this.to = t;
    }

    public String getName() {
        return name;
    }

    public Date getFrom() {
        return from;
    }

    public String getCard() {
        return card;
    }

    public Date getTo() {
        return to;
    }

    public String getPhone() {
        return phone;
    }
}
