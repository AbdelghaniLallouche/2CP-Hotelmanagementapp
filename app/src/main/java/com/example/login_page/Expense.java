package com.example.login_page;

import java.sql.Date;

public class Expense {
    int id , cost;
    Date date;

    public Date getDate() {
        return date;
    }

    String spent;
    Expense(int i , int cost , String s , Date date){
        this.id = i;
        this.cost = cost;
        this.spent = s;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public String getSpent() {
        return spent;
    }
}
