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
import java.sql.Date;
import java.sql.PreparedStatement;

public class AddComplaintController {
    @FXML
    Pane pane;
    @FXML
    TextField title;
    @FXML
    TextArea complaint;
    @FXML
    DatePicker date;
    @FXML
    void add() throws IOException {
        if (this.title.getText() == null || this.complaint.getText() == null || this.date.getValue() == null){
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
                Connection connection = Connectdb.getConnect();
                String query = "INSERT INTO `complaints` (`title`, `complaint`, `date`) VALUES ( ?, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,title.getText());
                preparedStatement.setString(2 , complaint.getText());
                preparedStatement.setDate(3, Date.valueOf(date.getValue()));
                int rows = preparedStatement.executeUpdate();
                if (rows > 0){
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
                    close();
                }
            }catch (Exception ee){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
                Pane pane = loader.load();
                DialogueController dialogueController = loader.getController();
                dialogueController.setContent_title(ee.getMessage(), "ADDING FAILED !");
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
    void reset(){
        this.complaint.setText(null);
        this.title.setText(null);
        this.date.setValue(null);
    }
    @FXML
    void close(){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
}
