package com.example.login_page;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditUserController {
    int id;
    public void setVars(int i , String u , String p){
        this.id = i;
        this.username.setText(u);
        this.pass.setText(p);
    }
    @FXML
    TextField username,pass;
    @FXML
    Pane pane;
    @FXML
    public void edit() throws SQLException, IOException {
        Connection connection = Connectdb.getConnect();
        if(!username.getText().isEmpty() && !pass.getText().isEmpty()) {
          try {
              String query = "UPDATE users SET username = ?, password = ? WHERE id = ?";
              PreparedStatement preparedStatement = connection.prepareStatement(query);
              preparedStatement.setString(1,this.username.getText());
              preparedStatement.setString(2,this.pass.getText());
              preparedStatement.setInt(3,this.id);
              preparedStatement.execute();
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
              Stage ss = (Stage) pane.getScene().getWindow();
              ss.close();
              // refresh

          }catch (Exception e){
              FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
              Pane alertPane = loader.load();
              DialogueController controller = loader.getController();
              controller.setContent_title( "username already in use try another username please !", "ADDING FAILED !");
              Scene scenee = new Scene(alertPane);
              Stage bb = new Stage();
              bb.initStyle(StageStyle.UNDECORATED);
              bb.setScene(scenee);
              bb.setX(530);
              bb.setY(250);
              bb.show();
          }
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
            Pane alertPane = loader.load();
            DialogueController controller = loader.getController();
            controller.setContent_title( "username or password is empty please try again !", "ADDING FAILED !");
            Scene scene = new Scene(alertPane);
            Stage ss = new Stage();
            ss.initStyle(StageStyle.UNDECORATED);
            ss.setScene(scene);
            ss.setX(530);
            ss.setY(250);
            ss.show();
        }

    }
    @FXML
    public void cancel(){
        Stage ss = (Stage) pane.getScene().getWindow();
        ss.close();
    }
}
