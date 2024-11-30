package com.example.login_page;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Bookdialogue {

    @FXML
    private Label label;
    int room = 0 , status = 2;


    public void setLabel(String nn , int room , int status) {
        this.label.setText(nn);
        this.room = room;
        this.status = status;
    }

    @FXML
    Pane pane;

    @FXML
    void cancel() {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void ok() throws SQLException, IOException {
        if (status == 3){
            try {
                String query = "UPDATE `chambres` SET `status` = ? WHERE `chambres`.`num` = ?";
                Connection connection = Connectdb.getConnect();
                PreparedStatement pr = connection.prepareStatement(query);
                pr.setInt(1, 3);
                pr.setInt(2, room);
                int rows = pr.executeUpdate();
                if (rows > 0) {
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
                dialogueController.setContent_title(ee.getMessage() , "CHECK IN FAILED !");
                Scene scene = new Scene(pane);
                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.setX(530);
                stage.setY(250);
                stage.show();
            }
        }else {
            try {
                String query = "DELETE FROM client WHERE room = ?";
                Connection connection = Connectdb.getConnect();
                PreparedStatement pr = connection.prepareStatement(query);
                pr.setInt(1, room);
                String s = "UPDATE `chambres` SET `status` = ? WHERE `chambres`.`num` = ?";
                PreparedStatement ss = connection.prepareStatement(s);
                ss.setInt(1, 1);
                ss.setInt(2, room);
                int rowone = pr.executeUpdate();
                int rowtwo = ss.executeUpdate();
                if (rowone > 0 && rowtwo > 0) {
                    FXMLLoader succes = new FXMLLoader(getClass().getResource("SuccesDialogue.fxml"));
                    Pane spane = succes.load();
                    SuccedDialogue succedDialogue = succes.getController();
                    Scene sss = new Scene(spane);
                    Stage st = new Stage(StageStyle.UNDECORATED);
                    st.setScene(sss);
                    st.setX(530);
                    st.setY(250);
                    st.show();
                    Duration duration = Duration.seconds(1);
                    Timeline timeline = new Timeline(new KeyFrame(duration, event -> st.close()));
                    timeline.play();
                    cancel();
                }else{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
                    Pane pane = loader.load();
                    DialogueController dialogueController = loader.getController();
                    dialogueController.setContent_title("SOMETHING WENT WRONG !" , "CHECK OUT FAILED !");
                    Scene scene = new Scene(pane);
                    Stage stage = new Stage(StageStyle.UNDECORATED);
                    stage.setScene(scene);
                    stage.setX(530);
                    stage.setY(250);
                    stage.show();
                }
            }catch (Exception ee){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
                Pane pane = loader.load();
                DialogueController dialogueController = loader.getController();
                dialogueController.setContent_title(ee.getMessage() , "CHECK OUT FAILED !");
                Scene scene = new Scene(pane);
                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.setX(530);
                stage.setY(250);
                stage.show();
            }
        }
    }

}

