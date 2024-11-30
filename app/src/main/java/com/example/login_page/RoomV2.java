package com.example.login_page;

import java.text.DecimalFormat;

// Messaoudi created this !!!
public class RoomV2 {
    private String type;
    private String income;
    public RoomV2(String type, int income) {
        this.type = type;
        Integer amount = Integer.parseInt(Integer.toString(income));
        DecimalFormat formatter = new DecimalFormat("#,###");
        this.income = formatter.format(amount) + " DA";
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getIncome() {
        return income;
    }
    public void setIncome(int income) {
        this.income = Integer.toString(income);
    }
}
