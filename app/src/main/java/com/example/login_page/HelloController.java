package com.example.login_page;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class HelloController{
    private double xOffset = 0;
    private double yOffset = 0;
     public void auth(String a , String b , String d ){
         try{
             boolean sui = false;
             Connection c = DriverManager.getConnection(
                     "jdbc:mysql://localhost/hotelmanagment?serverTimezone=UTC" ,
                     "lallouche" ,
                     "gghani123");
             Statement s = c.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE , java.sql.ResultSet.CONCUR_UPDATABLE);
             ResultSet result = s.executeQuery("SELECT username , password , typeofuser.type FROM `users` , `typeofuser` WHERE users.type = typeofuser.type_id");
             while (result.next() && sui != true){
                 if(result.getString("type").equals(d) && result.getString("username").equals(a) && result.getString("password").equals(b)){
                     sui = true;
                     loginn();
                 }
             }
             if (sui == false){
                 System.out.println("login failedddd");
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
                 Pane alertPane = loader.load();
                 DialogueController controller = loader.getController();
                 controller.setContent_title( "invalid password or username  please try again !", "LOGIN FAILED !");
                 Scene scene = new Scene(alertPane);
                 Stage ss = new Stage();
                 ss.initStyle(StageStyle.UNDECORATED);
                 ss.setScene(scene);
                 ss.show();
             }
         }catch (SQLException e){
             System.out.println("================================= " + e + " ============================");
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }
     @FXML
     public void finlog(){
         auth(username.getText(), password.getText() , "receptionist");
     }
@FXML
    Button logbtn;
@FXML
TextField username;
@FXML
    PasswordField password;
@FXML
void Cancel(){
    System.exit(0);
}
public void onLogin(String txt , Button btn , int s) throws IOException {
    if(s == 1){
    FXMLLoader loader = new FXMLLoader(getClass().getResource(txt));
    Parent root = loader.load();
    HomeController secondController = loader.getController();
    secondController.setUser("admin" , username.getText());
    Stage stage = (Stage) btn.getScene().getWindow();
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        Scene scene = new Scene(root);
        scene.getStylesheets().add("Styling.css");
        stage.setScene(scene);
    }else {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(txt));
        Parent root = loader.load();
        ReceptionHomeController secondController = loader.getController();
        secondController.setUser("receptionist" , username.getText());
        Stage stage = (Stage) btn.getScene().getWindow();
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        Scene scene = new Scene(root);
        scene.getStylesheets().add("Styling.css");
        stage.setScene(scene);
    }
}

public void Login(String txt) throws IOException {
    if (txt.equals("admin")){
    onLogin("HomePage.fxml" , logbtn , 1);}
    else{
        onLogin("recHome.fxml" , logbtn , 2);
    }
}
@FXML
public void loginn() throws IOException {
    Login("receptionist");
}
@FXML
public void goadmin() throws IOException {
FXMLLoader loader = new FXMLLoader(getClass().getResource("adminlogin.fxml"));
Pane pane = loader.load();
AdminLogin adminLogin = loader.getController();
Stage stage = new Stage(StageStyle.UNDECORATED);
stage.setScene(new Scene(pane));
stage.show();
}


}