package com.example.login_page;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Date;

public class SeeDoubleRoomController {
    public void setdata(int number , String type , String status , Date from , Date to , String name , String card , String phone , String scndname , String scndcard , String scndphone){
        this.roomnumber.setText(String.valueOf(number));
        this.roomtype.setText(type);
        this.roomstatus.setText(status);
        this.from.setText(String.valueOf(from));
        this.to.setText(String.valueOf(to));
        this.name.setText(name);
        this.card.setText(card);
        this.phone.setText(phone);
        this.scndname.setText(scndname);
        this.scndcard.setText(scndcard);
        this.scndphone.setText(scndphone);

    }
    @FXML
    public void close(){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
    @FXML
    private Label card;

    @FXML
    private Label from;

    @FXML
    private Label name;
    @FXML
    private Label scndcard;

    @FXML
    private Label scndname;

    @FXML
    private Label scndphone;

    @FXML
    private Pane pane;

    @FXML
    private Label phone;

    @FXML
    private Label roomnumber;

    @FXML
    private Label roomstatus;

    @FXML
    private Label roomtype;

    @FXML
    private Label to;
}
