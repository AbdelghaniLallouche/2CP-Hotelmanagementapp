package com.example.login_page;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Date;

public class ShowComplaintdetailsController{
    @FXML
    Pane pane;
    @FXML
    Label title,content,date;
    @FXML
    public void close(){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
    public void setdata(String t , String c , Date d){
        this.title.setText(t);
        this.content.setText(c);
        this.date.setText(d.toString());
    }

}
