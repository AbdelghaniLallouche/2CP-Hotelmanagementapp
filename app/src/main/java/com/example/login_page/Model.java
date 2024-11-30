package com.example.login_page;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Model {
    private int sum = 0;
    static final String DB_URL = "jdbc:mysql://localhost/hotelmanagment?serverTimezone=UTC";
    static final String USER = "lallouche";
    static final String PASS = "gghani123";
    public Model() {

    }
    public String getCurrentYearRevenue() {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select sum(amount) from earnings where YEAR(date) = " + java.time.LocalDate.now().getYear())) {
            rs.next();
            sum = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.toString(sum);
    }
    public String getLastYearRevenue() {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select sum(amount) from earnings where YEAR(date) = " + (java.time.LocalDate.now().getYear() - 1))) {
            rs.next();
            sum = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.toString(sum);
    }
    public String getRevenueChange() {
        return Float.toString((Float.parseFloat(getCurrentYearRevenue())*100)/Float.parseFloat(getLastYearRevenue()));
    }
    public ArrayList<RoomV2> getRevenueByCategory() {
        ArrayList<RoomV2> rooms = new ArrayList<>();
        int sum = 0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select amount from earnings where roomtype = 1")) {
            while (rs.next()) {
                sum += rs.getInt(1);
            }
            rooms.add(new RoomV2("single",sum));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sum = 0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select amount from earnings where roomtype = 2")) {
            while (rs.next()) {
                sum += rs.getInt(1);
            }
            rooms.add(new RoomV2("double",sum));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sum = 0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select amount from earnings where roomtype = 3")) {
            while (rs.next()) {
                sum += rs.getInt(1);
            }
            rooms.add(new RoomV2("triple",sum));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
    public String getCurrentYearExpenses() {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select sum(cost) from expenses where YEAR(date) = " + java.time.LocalDate.now().getYear())) {
            rs.next();
            sum = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.toString(sum);
    }
    public int getRevenueByMonth(String month) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select sum(amount) from earnings where YEAR(date) =" + java.time.LocalDate.now().getYear() + " and MONTH(date) = " + month)) {
            rs.next();
            sum = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
    public int getExpenseByMonth(String month) {
        int sum = 0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select sum(cost) from expenses where YEAR(date) =" + java.time.LocalDate.now().getYear() + " and MONTH(date) = " + month)) {
            rs.next();
            sum = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
    public ArrayList<ExpenseType> getExpensesTypes() {
        ArrayList<ExpenseType> arrayList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM expensetype")) {
            while (rs.next()) {
                arrayList.add(new ExpenseType(rs.getInt(1),rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    public int getCostByExpenseType(int expenseTypeNum) {
        int sum = 0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sum(cost) FROM expenses where type = " + expenseTypeNum)) {
            rs.next();
            sum = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
    public ArrayList<Staff> getStaff() {
        ArrayList<Staff> arrayList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM staff")) {
            while (rs.next()) {
                if (rs.getDate(7) != null) {
                    arrayList.add(new Staff(rs.getInt(1),rs.getDate(5).toLocalDate(),rs.getInt(6),rs.getDate(7).toLocalDate()));
                } else {
                    arrayList.add(new Staff(rs.getInt(1),rs.getDate(5).toLocalDate(),rs.getInt(6),null));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    public void updateLastTimePayed(Staff staff) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement stmt = conn.prepareStatement("update staff set last_time_paid = '" + java.time.LocalDate.now() +"' where id = " + staff.getId());
            stmt.executeUpdate();
            System.out.println("Query executed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addToSalaryPayment(int amount, LocalDate localDate) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement stmt = conn.prepareStatement("insert into salary_payments (amount, date) VALUES (" + amount +","+ localDate.toString() +")");
            stmt.executeUpdate();
            System.out.println("Query executed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getTotalSalaryPaymentsThisYear() {
        int sum = 0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sum(amount) FROM salary_payments where YEAR(date) = " + java.time.LocalDate.now().getYear() )) {
            rs.next();
            sum = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
    public String getNumberOfEmployees() {
        int sum = 0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from staff;")) {
            while (rs.next()) {
                sum ++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.toString(sum);
    }
    public String getNumberOfClientsThisMonth() {
        int sum = 0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from earnings where month(date) = " + java.time.LocalDate.now().getMonth().getValue())) {
            while (rs.next()) {
                sum ++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.toString(sum);
    }
    public String getNumberOfClientsLastMonth() {
        int sum = 0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from earnings where MONTH(date) = " + java.time.LocalDate.now().getMonth().minus(1).getValue())) {
            while (rs.next()) {
                sum ++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.toString(sum);
    }
}