package com.example.login_page;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class TribleRoomReservation implements Initializable {
    @FXML
    private TextField rdcard;

    @FXML
    private TextField rdname;

    @FXML
    private TextField rdphone;
    @FXML
    private TextField sndcard;

    @FXML
    private TextField sndname;

    @FXML
    private TextField sndphone;
    @FXML
    private TextField card;

    @FXML
    private DatePicker from;

    @FXML
    private TextField name;

    @FXML
    private Pane pane;

    @FXML
    private TextField phone;

    @FXML
    private TextField price;

    @FXML
    private ComboBox<String> status;

    @FXML
    private DatePicker to;
    int a = 2;
    int room;

    public void setRoom(int room) {
        this.room = room;
    }

    @FXML
    public void setsc(){
        a = status.getSelectionModel().getSelectedIndex() + 2;
    }

    ObservableList<String> list = FXCollections.observableArrayList("booked" , "checked in");

    @FXML
    void add() throws IOException {
        if(from.getValue() == null || to.getValue() == null || price.getText() == null || name.getText()==null || card.getText() == null || phone.getText() == null || sndname.getText()==null || sndcard.getText() == null || sndphone.getText() == null || rdname.getText()==null || rdcard.getText() == null || rdphone.getText() == null || price.getText().isEmpty() || name.getText().isEmpty() || card.getText().isEmpty() || phone.getText().isEmpty() || sndname.getText().isEmpty() || sndcard.getText().isEmpty() || sndphone.getText().isEmpty() || rdname.getText().isEmpty() || rdcard.getText().isEmpty() || rdphone.getText().isEmpty()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
            Pane pane = loader.load();
            DialogueController dialogueController = loader.getController();
            dialogueController.setContent_title("seems like you forget to fill some fields or invalid date, please try again !" , "BOOKING FAILED !");
            Scene scene = new Scene(pane);
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setX(530);
            stage.setY(250);
            stage.show();
        }else {
            try{
                Connection connection = Connectdb.getConnect();
                String query  ="INSERT INTO `client` (`name`, `card`, `phone`, `room`, `fromdate`, `todate`) VALUES (?,?,?,?,?,?) , (?,?,?,?,?,?) , (?,?,?,?,?,?)";
                PreparedStatement pr = connection.prepareStatement(query);
                pr.setString(1 , this.name.getText());
                pr.setString(2 , this.card.getText());
                pr.setString(3 , this.phone.getText());
                pr.setInt(4 , room);
                pr.setDate(5 , Date.valueOf(this.from.getValue()));
                pr.setDate(6, Date.valueOf(this.to.getValue()));
                pr.setString(7 , this.sndname.getText());
                pr.setString(8 , this.sndcard.getText());
                pr.setString(9 , this.sndphone.getText());
                pr.setInt(10 , room);
                pr.setDate(11 , Date.valueOf(this.from.getValue()));
                pr.setDate(12, Date.valueOf(this.to.getValue()));

                pr.setString(13 , this.rdname.getText());
                pr.setString(14 , this.rdcard.getText());
                pr.setString(15 , this.rdphone.getText());
                pr.setInt(16 , room);
                pr.setDate(17 , Date.valueOf(this.from.getValue()));
                pr.setDate(18, Date.valueOf(this.to.getValue()));

                String ss = "INSERT INTO `earnings` (`amount` , `roomtype` , `date`) VALUES (? , ? , ?)";
                PreparedStatement p = connection.prepareStatement(ss);
                p.setInt(1 , Integer.parseInt(this.price.getText()));
                p.setInt(2 , 3);
                p.setDate(3 , Date.valueOf(this.from.getValue()));

                String lil = "UPDATE `chambres` SET `status` = ? WHERE `chambres`.`num` = ?";;
                PreparedStatement haggami = connection.prepareStatement(lil);
                haggami.setInt(1 , a);
                haggami.setInt(2,room);
                int row = p.executeUpdate();
                if(row > 0){
                    int rr = pr.executeUpdate();
                    if (rr > 0){
                        int oho = haggami.executeUpdate();
                        if(oho > 0){
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
                        }
                    }
                }
            }catch (Exception ee){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
                Pane pane = loader.load();
                DialogueController dialogueController = loader.getController();
                dialogueController.setContent_title(ee.getMessage() , "BOOKING FAILED !");
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
    void cancel() {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void reset(ActionEvent event) {
        this.from.setValue(null);
        this.to.setValue(null);
        this.price.setText(null);
        this.name.setText(null);
        this.card.setText(null);
        this.phone.setText(null);
        this.sndname.setText(null);
        this.sndcard.setText(null);
        this.sndphone.setText(null);
        this.rdname.setText(null);
        this.rdcard.setText(null);
        this.rdphone.setText(null);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        status.setItems(list);
        status.setValue("booked");
    }
}