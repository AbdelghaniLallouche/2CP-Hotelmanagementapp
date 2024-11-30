package com.example.login_page;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteController {
    int id;

    public void setId_Query(int id , String q) {
        this.query = q;
        this.id = id;
    }

    @FXML
    Pane pane;
    String query;
    @FXML
    public void delete() throws SQLException, IOException {
        Connection connection = Connectdb.getConnect();
        String ii = this.query + this.id;
        PreparedStatement preparedStatement = connection.prepareStatement(ii);
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
        //refresh table
    }
    @FXML
    public void cancel(){
        Stage ss = (Stage) pane.getScene().getWindow();
        ss.close();
    }

}
