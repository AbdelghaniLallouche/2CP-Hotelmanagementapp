package com.example.login_page;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.sql.Date;

public class EditComplainController {
    @FXML
    Pane pane;
    @FXML
    TextField title;
    @FXML
    TextArea complaint;
    @FXML
    DatePicker date;
    int id;
    void setdata(String t , String c , Date date , int id){
        this.title.setText(t);
        this.complaint.setText(c);
        this.date.setValue(date.toLocalDate());
        this.id = id;
    }
    @FXML
    public void edit() throws IOException {
      if(title.getText() == null || complaint.getText() == null || date.getValue() == null){
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
      } else {
          Connection connection = Connectdb.getConnect();
          try {
              String queryyy = "UPDATE `complaints` SET title=?, complaint=?, date=? WHERE id = ?";
              PreparedStatement preparedStatement = connection.prepareStatement(queryyy);
              preparedStatement.setString(1 , title.getText());
              preparedStatement.setString(2 , complaint.getText());
              preparedStatement.setDate(3 , Date.valueOf(date.getValue()));
              preparedStatement.setInt(4 , id);
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
    public void reset(){
        this.date.setValue(null);
        this.complaint.setText(null);
        this.title.setText(null);
    }
    @FXML
    public void cancel(){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
}
