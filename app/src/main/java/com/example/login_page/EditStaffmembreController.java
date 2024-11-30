package com.example.login_page;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditStaffmembreController implements Initializable {
    int a ;

    public void seteverything(String name , String shift , int salary , Date date) {
        this.name.setText(name);
        this.salary.setText(String.valueOf(salary));
        this.shift.setText(shift);
        this.jdate.setValue(date.toLocalDate());
    }

    public void setA(int a) {
        this.a = a - 1;
    }
    void setsct(String a){
        section.setValue(a);
    }

    @FXML
    public void setSection(){
        a = section.getSelectionModel().getSelectedIndex();
    }
    @FXML
    Pane addpane;
    @FXML
    TextField name,shift,salary;
    @FXML
    DatePicker jdate;
    @FXML
    ComboBox section;
    int id;

    public void setId(int id) {
        this.id = id;
    }

    ObservableList list = FXCollections.observableArrayList();
    @FXML
    public void onEdit() throws IOException {
            if(jdate.getValue() == null || name.getText() == null || shift.getText() == null  || salary.getText() == null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
                Pane pane = loader.load();
                DialogueController dialogueController = loader.getController();
                dialogueController.setContent_title("seems like you forget to fill some fields or invalid date, please try again !" , "EDITING FAILED !");
                Scene scene = new Scene(pane);
                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.setX(530);
                stage.setY(250);
                stage.show();
            }
            else {
                Connection connection = Connectdb.getConnect();
                try {
                    String queryyy = "UPDATE `staff` SET name=?, section=?, shift=?, joiningdate=?, salary=? WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(queryyy);
                    preparedStatement.setString(1, this.name.getText());
                    preparedStatement.setInt(2, a+1);
                    preparedStatement.setString(3, this.shift.getText());
                    preparedStatement.setDate(4, Date.valueOf(this.jdate.getValue()));
                    preparedStatement.setInt(5, Integer.parseInt(this.salary.getText()));
                    preparedStatement.setInt(6, this.id);
                    int index = preparedStatement.executeUpdate();
                    if(index > 0) {
                        FXMLLoader ll = new FXMLLoader(getClass().getResource("SuccesDialogue.fxml"));
                        Pane pp = ll.load();
                        SuccedDialogue succedDialogue = ll.getController();
                        Scene sv = new Scene(pp);
                        Stage stage = new Stage(StageStyle.UNDECORATED);
                        stage.setScene(sv);
                        stage.setX(530);
                        stage.setY(250);
                        stage.show();
                        Duration duration = Duration.seconds(1);
                        Timeline timeline = new Timeline(new KeyFrame(duration, event -> stage.close()));
                        timeline.play();
                        cancel();
                    }else{
                        System.out.println("nope");
                    }
                }catch (Exception ee){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
                    Pane pane = loader.load();
                    DialogueController dialogueController = loader.getController();
                    dialogueController.setContent_title(ee.getMessage() , "EDITING FAILED !");
                    Scene scene = new Scene(pane);
                    Stage stage = new Stage(StageStyle.UNDECORATED);
                    stage.setScene(scene);
                    stage.setX(530);
                    stage.setY(250);
                    stage.show();
                }
        }
    }
    @FXML
    public void cancel(){
        Stage stage= (Stage) addpane.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void reset(){
        this.name.setText(null);
        this.jdate.setValue(null);
        this.salary.setText(null);
        this.shift.setText(null);
    }


    void loadsections() throws SQLException {
        Connection cc = Connectdb.getConnect();
        String query = "SELECT `sectionname` FROM `sections` WHERE 1;";
        PreparedStatement preparedStatement = cc.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            list.add(resultSet.getString("sectionname"));
            section.setItems(list);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadsections();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

