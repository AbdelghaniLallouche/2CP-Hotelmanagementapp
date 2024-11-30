package com.example.login_page;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ManageusersController implements Initializable {
    @FXML
    Button reset,change,adduser;
    @FXML
    TextField username;
    String zz;

    Connection con = null;
    String query = null;
    Statement s ;
    ResultSet res ;
    ObservableList<User> userlist = FXCollections.observableArrayList();
    public void setVariable(String ee){
        this.username.setText(ee);
        this.zz = ee;
    }


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


    public void dataload() throws SQLException {
        con = Connectdb.getConnect();
        query = "SELECT id ,username , password , typeofuser.type FROM `users` , `typeofuser` WHERE users.type = typeofuser.type_id";
         s = con.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE , java.sql.ResultSet.CONCUR_UPDATABLE);
         res = s.executeQuery(query);
         while (res.next()){
             userlist.add(new User(res.getInt("id") , res.getString("username") , res.getString("password") , res.getString("type")));
             table.setItems(userlist);
         }

        Callback<TableColumn<User, String>, TableCell<User, String>> cellFoctory = (TableColumn<User, String> param) -> {
            // make cell containing buttons
            final TableCell<User, String> cell = new TableCell<User, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        ImageView deleteIcon = new ImageView(new Image("file:///C:/Users/abdel/IdeaProjects/Project/Login_Page/src/main/resources/remove.png"));
                        deleteIcon.setStyle(" -fx-cursor: hand");
                        deleteIcon.setFitHeight(20);
                        deleteIcon.setFitWidth(20);
                        deleteIcon.setPreserveRatio(false);
                        ImageView editIcon = new ImageView(new Image("file:///C:/Users/abdel/IdeaProjects/Project/Login_Page/src/main/resources/edit.png"));
                        editIcon.setFitWidth(20);
                        editIcon.setFitHeight(20);
                        editIcon.setPreserveRatio(false);
                        editIcon.setStyle(" -fx-cursor: hand");



                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            User us = table.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Deletedialogue.fxml"));
                            try {
                                Pane pane = loader.load();
                                DeleteController deleteController = loader.getController();
                                deleteController.setId_Query(us.getId() , "DELETE FROM users WHERE `users`.`id` = ");
                                Scene sc = new Scene(pane);
                                Stage stage = new Stage(StageStyle.UNDECORATED);
                                stage.setScene(sc);
                                stage.setX(580);
                                stage.setY(250);
                                stage.show();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });



                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                           User user = table.getSelectionModel().getSelectedItem();
                           FXMLLoader loader = new FXMLLoader(getClass().getResource("edituser.fxml"));
                            try {
                                Pane pane = loader.load();
                                EditUserController editUserController = loader.getController();
                                editUserController.setVars(user.getId() , user.getUsername() , user.getPass());
                                Scene sc = new Scene(pane);
                                Stage st = new Stage(StageStyle.UNDECORATED);
                                st.setScene(sc);
                                st.setX(530);
                                st.setY(250);
                                st.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };

        actions.setCellFactory(cellFoctory);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user.setCellValueFactory(new PropertyValueFactory<>("username"));
        motpass.setCellValueFactory(new PropertyValueFactory<>("pass"));
        tipe.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    @FXML
    public void onReset(){
        username.setText("");
        pass.setText("");
        repass.setText("");
    }
    @FXML
    public void onAdd() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adduser.fxml"));
        Pane alertPane = loader.load();
        AdduserController controller = loader.getController();
        Scene scenee = new Scene(alertPane);
        Stage bb = new Stage();
        bb.initStyle(StageStyle.UNDECORATED);
        bb.setScene(scenee);
        bb.setX(530);
        bb.setY(170);
        bb.show();
    }
    @FXML
    public void refresh() throws SQLException {
        userlist.clear();
        dataload();
    }
    @FXML
    TextField pass;
    @FXML
    TextField repass;
    @FXML
    TextField search;
    @FXML
    TableView<User> table;
    @FXML
    TableColumn<User , Integer> id;
    @FXML
    TableColumn<User , String> user , motpass,tipe;
    @FXML
    TableColumn<User , String> actions;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dataload();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(username.getText());
    }
}
