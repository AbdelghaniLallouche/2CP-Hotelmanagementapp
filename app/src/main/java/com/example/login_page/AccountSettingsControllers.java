package com.example.login_page;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountSettingsControllers {
        String zz;
        public void setVariable(String ee){
               this.username.setText(ee);
               this.zz = ee;
        }


        @FXML
        private Button change;

        @FXML
        private TextField pass;

        @FXML
        private TextField repass;

        @FXML
        private Button reset;

        @FXML
        private TextField username;

       @FXML
       public void onChange() throws SQLException, IOException {
              setChange(this.pass.getText() , this.repass.getText() , this.username.getText());
       }

       public void setChange(String a , String b , String cc) throws IOException, SQLException {
              if (!a.equals(b) || cc.isEmpty() || a.isEmpty()){
                     FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue2.fxml"));
                     Pane alertPane = loader.load();
                     DialogueController controller = loader.getController();
                     Scene scene = new Scene(alertPane);
                     Stage ss = new Stage();
                     ss.initStyle(StageStyle.UNDECORATED);
                     ss.setScene(scene);
                     ss.setX(530);
                     ss.setY(250);
                     ss.show();
              }
              else {
                     try {
                            Connection con = Connectdb.getConnect();
                            String str = "UPDATE `users` SET `username` = ? , `password` = ? WHERE `username` = ?";
                            PreparedStatement pr = con.prepareStatement(str);
                            pr.setString(1, cc);
                            pr.setString(2, b);
                            pr.setString(3, zz);

                            int rowsUpdated = pr.executeUpdate();

                            if (rowsUpdated > 0) {
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
                                   //succes dialogue
                            }
                     }catch(Exception e){
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
                            Pane root = loader.load();
                            DialogueController controller = loader.getController();
                            controller.setContent_title( "username already in use try another username please !", "CHANGMENT FAILED !");
                            Scene scenee = new Scene(root);
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
        void onReset() {
               username.setText("");
               pass.setText("");
               repass.setText("");
        }

    }

