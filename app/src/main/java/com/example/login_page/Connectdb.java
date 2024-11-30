package com.example.login_page;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Connectdb {
    public static Connection getConnect(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(  "jdbc:mysql://localhost/hotelmanagment?serverTimezone=UTC" ,
                     "lallouche" ,
                     "gghani123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
    public static void connect(){
        try{
            Connection c = DriverManager.getConnection(
                    "jdbc:mysql://localhost/hotelmanagment?serverTimezone=UTC" ,
                    "lallouche" ,
                    "gghani123");
            Statement s = c.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE , java.sql.ResultSet.CONCUR_UPDATABLE);
            ResultSet result = s.executeQuery("SELECT username , password , typeofuser.type FROM `users` , `typeofuser` WHERE users.id = typeofuser.type_id");
        }catch (SQLException e){
            System.out.println("================================= " + e + " ============================");
        }
    }}
