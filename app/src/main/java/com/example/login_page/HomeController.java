package com.example.login_page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class HomeController implements Initializable{
    @FXML
    Label user;
    @FXML
    Label username;
    String dz;
    public void setUser(String aa , String bb){
        user.setText(aa.toUpperCase());
        username.setText(bb.toUpperCase());
        this.dz = bb;
    }
    @FXML
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,minimizeButton,maximizeButton;
    @FXML
    private AnchorPane Container;
    @FXML
    private BorderPane borderPane;
    @FXML
    void SetReservation() throws Exception {
        OnClick(2);
        ShowPage("managefinance.fxml" , btn2);
    }
    @FXML
    void SetDashboard() throws Exception {
        OnClick(1);
        ShowPage("dashbord.fxml" , btn1);
    }
    @FXML
    void SetManageRooms() throws Exception {
        OnClick(3);
        ShowPage("managerooms.fxml" , btn3);
    }
    @FXML
    void SetStaff() throws Exception {
        OnClick(4);
        ShowPage("staffsection.fxml" , btn4);
    }
    @FXML
    void SetComplaints() throws Exception {
        OnClick(5);
        ShowPage("managecomplaints.fxml" , btn5);
    }
    @FXML
    void SetStatistics() throws Exception {
        OnClick(6);
        ShowPage("statistics-view.fxml" , btn6);
    }
    @FXML
    void SetManageUsers() throws Exception {
        OnClick(7);
        ShowPage("Manageusers.fxml",dz,btn7);
    }
    @FXML
    void Cancel(){
        System.exit(0);
    }
    @FXML
    public void onMinimizeButtonClick() {
        minimizeButton.setOnAction(e -> {
            ((Stage)((Button)e.getSource()).getScene().getWindow()).setIconified(true);
        });
    }
    @FXML
    public void onMaximizeButtonClick() throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }
    public void OnClick(int i){
        btn1.setStyle("-fx-background-color:  #4c4cf2 ; -fx-text-fill: white ; -fx-fill: white");
        btn2.setStyle("-fx-background-color:  #4c4cf2 ; -fx-text-fill: white ; -fx-fill: white");
        btn3.setStyle("-fx-background-color:  #4c4cf2 ; -fx-text-fill: white ; -fx-fill: white");
        btn4.setStyle("-fx-background-color:  #4c4cf2 ; -fx-text-fill: white ; -fx-fill: white");
        btn5.setStyle("-fx-background-color:  #4c4cf2 ; -fx-text-fill: white ; -fx-fill: white");
        btn6.setStyle("-fx-background-color:  #4c4cf2 ; -fx-text-fill: white ; -fx-fill: white");
        btn7.setStyle("-fx-background-color:  #4c4cf2 ; -fx-text-fill: white ; -fx-fill: white");
        switch (i) {
            case 1 -> btn1.setStyle("-fx-background-color: white ; -fx-text-fill: black ; -fx-fill: black ; -fx-border-radius: 0 ; -fx-background-radius: 0");
            case 2 -> btn2.setStyle("-fx-background-color: white ; -fx-text-fill: black ; -fx-fill: black ; -fx-border-radius: 0 ; -fx-background-radius: 0");
            case 3 -> btn3.setStyle("-fx-background-color: white ; -fx-text-fill: black ; -fx-fill: black; -fx-border-radius: 0 ; -fx-background-radius: 0");
            case 4 -> btn4.setStyle("-fx-background-color: white ; -fx-text-fill: black ; -fx-fill: black; -fx-border-radius: 0 ; -fx-background-radius: 0");
            case 5 -> btn5.setStyle("-fx-background-color: white ; -fx-text-fill: black ; -fx-fill: black; -fx-border-radius: 0 ; -fx-background-radius: 0");
            case 6 -> btn6.setStyle("-fx-background-color: white ; -fx-text-fill: black ; -fx-fill: black; -fx-border-radius: 0 ; -fx-background-radius: 0");
            case 7 -> btn7.setStyle("-fx-background-color: white ; -fx-text-fill: black ; -fx-fill: black; -fx-border-radius: 0 ; -fx-background-radius: 0");
        }
    }
    void ShowPage(String fxml, String variable, Button btn) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        ManageusersController controller = loader.getController();
        controller.setVariable(variable);
        Scene scene = btn.getScene();
        Container.getChildren().add(root);
        AnchorPane.setTopAnchor(root,0.0);
        AnchorPane.setLeftAnchor(root,0.0);
        AnchorPane.setRightAnchor(root,0.0);
        AnchorPane.setBottomAnchor(root,0.0);
    }
    void ShowPage(String fxml , Button btn) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Scene scene = btn.getScene();
        Container.getChildren().add(root);
        AnchorPane.setTopAnchor(root,0.0);
        AnchorPane.setLeftAnchor(root,0.0);
        AnchorPane.setRightAnchor(root,0.0);
        AnchorPane.setBottomAnchor(root,0.0);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn1.setStyle("-fx-text-fill: black; -fx-border-radius: 0;-fx-background-radius: 0;-fx-background-color: white");
        try {
            SetDashboard();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
