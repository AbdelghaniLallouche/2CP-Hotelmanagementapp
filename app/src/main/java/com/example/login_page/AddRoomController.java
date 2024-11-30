package com.example.login_page;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class AddRoomController implements Initializable {
    int a = 1;

    @FXML
    private Pane pane;
    @FXML
    public void setstatus(){
        a = roomstatus.getSelectionModel().getSelectedIndex() + 1;
    }

    @FXML
    private TextField roomnum;

    @FXML
    private ComboBox<String> roomstatus;

    @FXML
    void add() throws IOException {
      if(this.roomnum.getText() == null){
          FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
          Pane pane = loader.load();
          DialogueController dialogueController = loader.getController();
          dialogueController.setContent_title("seems like you forget to fill some fields or invalid date, please try again !" , "ADDING FAILED !");
          Scene scene = new Scene(pane);
          Stage stage = new Stage(StageStyle.UNDECORATED);
          stage.setScene(scene);
          stage.setX(530);
          stage.setY(250);
          stage.show();
      }
      else {
          try{
              Connection connection = Connectdb.getConnect();
              String query = "INSERT INTO `chambres` (num, status,type) VALUES (?, ?, ?)";
              PreparedStatement pr = connection.prepareStatement(query);
              pr.setInt(1 , Integer.parseInt(roomnum.getText()));
              pr.setInt(2 , 1);
              pr.setInt(3 , a);
              int rows = pr.executeUpdate();
              if (rows > 0){
                  FXMLLoader succes = new FXMLLoader(getClass().getResource("SuccesDialogue.fxml"));
                  Pane spane = succes.load();
                  SuccedDialogue succedDialogue = succes.getController();
                  Scene ss = new Scene(spane);
                  Stage st = new Stage(StageStyle.UNDECORATED);
                  st.setScene(ss);
                  st.setX(530);
                  st.setY(250);
                  st.show();
                  Duration duration = Duration.seconds(1);
                  Timeline timeline = new Timeline(new KeyFrame(duration, event -> st.close()));
                  timeline.play();
                  cancel();
              }

          }catch (Exception ee){
              FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
              Pane pane = loader.load();
              DialogueController dialogueController = loader.getController();
              dialogueController.setContent_title(ee.getMessage() , "ADDING FAILED !");
              Scene scene = new Scene(pane);
              Stage stage = new Stage(StageStyle.UNDECORATED);
              stage.setScene(scene);
              stage.setX(530);
              stage.setY(250);
              stage.show();
          }

      }
    }
    ObservableList<String> list = FXCollections.observableArrayList("single" , "double" , "trible");

    @FXML
    void cancel() {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomstatus.setItems(list);
        roomstatus.setValue("single");
    }
}
