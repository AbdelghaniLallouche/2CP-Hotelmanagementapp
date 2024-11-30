package com.example.login_page;

import java.sql.Date;

public class Complaint {
    int id;
    String title , complaint;
    Date date;

    Complaint(int i , String t , String c , Date d){
        this.id = i;
        this.title = t;
        this.complaint = c;
        this.date = d;
    }
    public int getId() {
        return id;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }
}
