package com.example.login_page;

import java.time.LocalDate;

public class Staff {
    private int id;
    private LocalDate joiningDate;
    private int salary;
    private LocalDate lastTimePayed;

    public Staff(int id, LocalDate joiningDate, int salary, LocalDate lastTimePayed) {
        this.id = id;
        this.joiningDate = joiningDate;
        this.salary = salary;
        this.lastTimePayed = lastTimePayed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getLastTimePayed() {
        return lastTimePayed;
    }

    public void setLastTimePayed(LocalDate lastTimePayed) {
        this.lastTimePayed = lastTimePayed;
    }
}
