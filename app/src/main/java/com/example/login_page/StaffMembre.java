package com.example.login_page;

import java.sql.Date;

public class StaffMembre {
    int id , salary;
    String name , section , shift;
    Date jdate;

    StaffMembre(int a , int b , String u , String k ,String sh , Date date){
        this.id = a;
        this.salary = b;
        this.name = u;
        this.section = k;
        this.shift = sh;
        this.jdate =date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getJdate() {
        return jdate;
    }

    public int getSalary() {
        return salary;
    }

    public String getSection() {
        return section;
    }

    public String getShift() {
        return shift;
    }

}
