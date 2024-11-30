package com.example.login_page;

public class ExpenseType {
    private int num;
    private String type;
    public ExpenseType(int num, String type) {
        this.num = num;
        this.type = type;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}