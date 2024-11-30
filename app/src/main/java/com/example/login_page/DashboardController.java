package com.example.login_page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    int total = 0 , com = 0 , staf = 0 , free = 0 , book = 0 , check = 0;
    public void loaddata() throws SQLException {
        Connection connection = Connectdb.getConnect();
        Statement statement = null;
        try {
            statement = connection.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE , java.sql.ResultSet.CONCUR_UPDATABLE);

            String s = "SELECT COUNT(*) FROM chambres";
            String s1 = "SELECT COUNT(*) FROM complaints";
            String s2 = "SELECT COUNT(*) FROM staff";
            String s3 = "SELECT COUNT(*) FROM chambres WHERE status = 1";
            String s4 = "SELECT COUNT(*) FROM chambres WHERE status = 2";
            String s5 = "SELECT COUNT(*) FROM chambres WHERE status = 3";

            ResultSet rs = statement.executeQuery(s);
            rs.next();
            total = rs.getInt(1);
            rs.close();

            rs = statement.executeQuery(s1);
            rs.next();
            com = rs.getInt(1);
            rs.close();

            rs = statement.executeQuery(s2);
            rs.next();
            staf = rs.getInt(1);
            rs.close();

            rs = statement.executeQuery(s3);
            rs.next();
            free = rs.getInt(1);
            rs.close();

            rs = statement.executeQuery(s4);
            rs.next();
            book = rs.getInt(1);
            rs.close();

            rs = statement.executeQuery(s5);
            rs.next();
            check = rs.getInt(1);
            rs.close();

        } catch (SQLException e) {
            // Handle any exceptions
            e.printStackTrace();
        } finally {
            // Close the statement and connection objects
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
@FXML
    Label totalrooms , complaints , staff , available , booked , checked;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loaddata();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        totalrooms.setText(String.valueOf(total));
        complaints.setText(String.valueOf(com));
        staff.setText(String.valueOf(staf));
        available.setText(String.valueOf(free));
        booked.setText(String.valueOf(book));
        checked.setText(String.valueOf(check));
        //code to bring data and show it
    }
}
