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

public class AddstaffmembreController implements Initializable {
    int a = 0;
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
    @FXML
    public void onAdd() throws IOException {
        if(name.getText().isEmpty() || shift.getText().isEmpty() || salary.getText().isEmpty() || jdate.getValue() == null || name.getText() == null || shift.getText() == null  || salary.getText() == null){
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
                String query = "INSERT INTO `staff` (`name`, `section`, `shift`, `joiningdate`, `salary`) VALUES (?,?,?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1 , name.getText());
                preparedStatement.setInt(2 , a+1);
                preparedStatement.setString(3,shift.getText());
                preparedStatement.setDate(4 , Date.valueOf(jdate.getValue()));
                preparedStatement.setInt(5 , Integer.parseInt(salary.getText()));
                int rows = preparedStatement.executeUpdate();
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

            }catch (Exception exception){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
                Pane pane = loader.load();
                DialogueController dialogueController = loader.getController();
                dialogueController.setContent_title(exception.getMessage() , "ADDING FAILED !");
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
        Stage stage = (Stage) addpane.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void reset(){
        name.setText(null);
        salary.setText(null);
        shift.setText(null);
        jdate.setValue(null);
    }
    ObservableList list = FXCollections.observableArrayList();

    public void loadsections() throws SQLException {
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
