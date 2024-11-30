package com.example.login_page;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdduserController implements Initializable {
    @FXML
    Pane alertPane;
    int a = 2;
    @FXML
    public void addRow() throws SQLException, IOException {
        onadd(usernameadd.getText() , passwordadd.getText() , a);
    }
    public void onadd(String a ,String b , int aaa) throws IOException, SQLException {
        if(a.isEmpty() || b.isEmpty()){
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
        else{
               try {
                   String query = "INSERT INTO `users` (`username`, `password`, `type`) VALUES (?,?,?)";
                Connection con = Connectdb.getConnect();
                Statement ss = con.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE , java.sql.ResultSet.CONCUR_UPDATABLE);
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, a);
                pstmt.setString(2, b);
                pstmt.setInt(3,aaa);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
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
                //succes dialogue
            } else {
                System.out.println("error");
            }
               } catch (Exception e) {
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
    }
    @FXML
    public void close(){
        Stage stage = (Stage) alertPane.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void resett(){
        usernameadd.setText("");
        passwordadd.setText("");
    }
    @FXML
    public void typee(){
        if(typeadd.getSelectionModel().toString().equals("receptionist")){
            a = 2;
        }else {
            a = 1;
        };
    }
    @FXML
    Button adduserbtn , resetuser;
    @FXML
    TextField usernameadd , passwordadd;
    @FXML
    ComboBox typeadd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("receptionist" , "admin");
        typeadd.setItems(list);
    }
}
