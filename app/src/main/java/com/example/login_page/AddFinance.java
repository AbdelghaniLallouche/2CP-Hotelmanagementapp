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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddFinance implements Initializable {
    int a = 0;
    @FXML
    ComboBox type;
    @FXML
    public void settype(){
    a = type.getSelectionModel().getSelectedIndex();
    }

    ObservableList<String> list = FXCollections.observableArrayList();
    void loadtypes() throws SQLException {
        String query = "SELECT type FROM `expensetype`";
        Connection con = Connectdb.getConnect();
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            list.add(resultSet.getString("type"));
        }
        type.setItems(list);
    }
    @FXML
    private TextArea complaint;

    @FXML
    private DatePicker date;

    @FXML
    private Pane pane;

    @FXML
    private TextField title;

    @FXML
    void add() throws IOException {
      if(title.getText().isBlank() || title.getText() == null || complaint.getText().isBlank() || complaint.getText() == null || date.getValue() == null){
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
      }else {
          try{
              Connection con = Connectdb.getConnect();
              String query = "INSERT INTO `losses` (`cost`, `spenton` , `date`) VALUES (?,?,?)";
              String queryy = "INSERT INTO `expenses` (`cost`, `description` ,`date` , `type`) VALUES (?,?,?,?)";
              PreparedStatement pr = con.prepareStatement(query);
              pr.setInt(1 , Integer.parseInt(this.title.getText()));
              pr.setString(2,this.complaint.getText());
              pr.setDate(3 , Date.valueOf(this.date.getValue()));

              PreparedStatement prr = con.prepareStatement(queryy);
              prr.setInt(1 , Integer.parseInt(this.title.getText()));
              prr.setString(2 , this.complaint.getText());
              prr.setDate(3 , Date.valueOf(this.date.getValue()));
              prr.setInt(4,a+1);

             int i = prr.executeUpdate();
             if (i > 0){
                 int y = pr.executeUpdate();
                 if (y > 0){
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
                     close();
                 }
             }else {
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
                 Pane pane = loader.load();
                 DialogueController dialogueController = loader.getController();
                 dialogueController.setContent_title("something went wrong please try again !" , "ADDING FAILED !");
                 Scene scene = new Scene(pane);
                 Stage stage = new Stage(StageStyle.UNDECORATED);
                 stage.setScene(scene);
                 stage.setX(530);
                 stage.setY(250);
                 stage.show();
             }

          }catch (Exception e){
              FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
              Pane pane = loader.load();
              DialogueController dialogueController = loader.getController();
              dialogueController.setContent_title(e.getMessage() , "ADDING FAILED !");
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
    void close() {
     Stage stage = (Stage) pane.getScene().getWindow();
     stage.close();
    }

    @FXML
    void reset() {
    this.date.setValue(null);
    this.complaint.setText("");
    this.title.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadtypes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}