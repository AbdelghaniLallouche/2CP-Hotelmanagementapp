package com.example.login_page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageroomsController implements Initializable {
    @FXML
    private TableColumn actions;

    @FXML
    private TableColumn changecol;

    @FXML
    private TableColumn<Room, Integer> numcol;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<Room, String> statuscol;

    @FXML
    private TableView<Room> table;

    @FXML
    private TableColumn<Room, String> typecol;
    ObservableList list = FXCollections.observableArrayList();

    @FXML
    public void refresh() throws SQLException {
        list.clear();
        loaddata();
    }
    public void loaddata() throws SQLException {
        String query = "SELECT num , roomstype.type , roomstatus.status FROM `chambres` , roomstype , roomstatus WHERE roomstype.id = chambres.type AND roomstatus.id = chambres.status";
        Connection connection = Connectdb.getConnect();
        PreparedStatement pr = connection.prepareStatement(query);
        ResultSet resultSet = pr.executeQuery();
        while (resultSet.next()){
            list.add(new Room(resultSet.getInt("num") , resultSet.getString("type") , resultSet.getString("status")));
            table.setItems(list);
        }



        Callback<TableColumn<Room, String>, TableCell<Room, String>> cellFoctory = (TableColumn<Room, String> param) -> {
            // make cell containing buttons
            final TableCell<Room, String> cell = new TableCell<Room, String>() {
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
                        ImageView viewIcon = new ImageView(new Image("file:///C:/Users/abdel/IdeaProjects/Project/Login_Page/src/main/resources/eye.png"));
                        viewIcon.setFitWidth(20);
                        viewIcon.setFitHeight(20);
                        viewIcon.setPreserveRatio(false);
                        viewIcon.setStyle(" -fx-cursor: hand");





                        viewIcon.setOnMouseClicked((MouseEvent event) ->{
                            Room room = table.getSelectionModel().getSelectedItem();
                            if(room.getStatus().equals("free")){
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogue.fxml"));
                                Pane pane = null;
                                try {
                                    pane = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                DialogueController dialogueController = loader.getController();
                                dialogueController.setContent_title("free room doesn't contain any consumers !" , "NO CONSUMERS !");
                                Scene scene = new Scene(pane);
                                Stage stage = new Stage(StageStyle.UNDECORATED);
                                stage.setScene(scene);
                                stage.setX(530);
                                stage.setY(250);
                                stage.show();
                            }
                            else{
                                ObservableList<Consumer> consumer = FXCollections.observableArrayList();
                                String query = "SELECT name , card , phone , fromdate , todate FROM `client` WHERE client.room = ?";
                                Connection connection1 = Connectdb.getConnect();
                                PreparedStatement preparedStatement = null;
                                try {
                                    preparedStatement = connection1.prepareStatement(query);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                try {
                                    preparedStatement.setInt(1 , room.getNum());
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                ResultSet res = null;
                                try {
                                    res = preparedStatement.executeQuery();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                while (true){
                                    try {
                                        if (!res.next()) break;
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                    try {
                                        consumer.add(new Consumer(res.getString("name") , res.getString("card"), res.getString("phone") , res.getDate("fromdate") , res.getDate("todate") ));
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                }


                                if(room.getType().equals("single")){
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("seesingleroom.fxml"));
                                Pane pane = null;
                                try {
                                    pane = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                SeeSingleroomController single = loader.getController();
                                single.setdata(room.getNum() , room.getType() , room.getStatus() , consumer.get(0).getFrom() ,consumer.get(0).getTo() , consumer.get(0).getName() , consumer.get(0).getCard() , consumer.get(0).getPhone() );
                                Stage stage = new Stage(StageStyle.UNDECORATED);
                                stage.setScene(new Scene(pane));
                                stage.setX(560);
                                stage.setY(100);
                                stage.show();
                            }
                            else if (room.getType().equals("double")) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("seedoubleroom.fxml"));
                                Pane pane = null;
                                try {
                                    pane = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                SeeDoubleRoomController single = loader.getController();
                                    single.setdata(room.getNum() , room.getType() , room.getStatus() , consumer.get(0).getFrom() ,consumer.get(0).getTo() , consumer.get(0).getName() , consumer.get(0).getCard() , consumer.get(0).getPhone() , consumer.get(1).getName() , consumer.get(1).getCard() , consumer.get(1).getPhone() );
                                Stage stage = new Stage(StageStyle.UNDECORATED);
                                stage.setScene(new Scene(pane));
                                stage.setX(560);
                                stage.setY(100);
                                stage.show();
                            }else {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("seetribleroom.fxml"));
                                Pane pane = null;
                                try {
                                    pane = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                SeeTribleRommController single = loader.getController();
                                    single.setdata(room.getNum() , room.getType() , room.getStatus() , consumer.get(0).getFrom() ,consumer.get(0).getTo() , consumer.get(0).getName() , consumer.get(0).getCard() , consumer.get(0).getPhone() , consumer.get(1).getName() , consumer.get(1).getCard() , consumer.get(1).getPhone() , consumer.get(2).getName() , consumer.get(2).getCard() , consumer.get(2).getPhone() );
                                Stage stage = new Stage(StageStyle.UNDECORATED);
                                stage.setScene(new Scene(pane));
                                stage.setX(560);
                                stage.setY(70);
                                stage.show();
                            } }
                        });



                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Room room = table.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Deletedialogue.fxml"));
                            try {
                                Pane pane = loader.load();
                                DeleteController deleteController = loader.getController();
                                deleteController.setId_Query(room.getNum() , "DELETE FROM chambres WHERE `chambres`.`num` = ");
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



                        HBox managebtn = new HBox(deleteIcon,viewIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        managebtn.setSpacing(3);
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 2));
                        HBox.setMargin(viewIcon, new Insets(2, 2, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };







        Callback<TableColumn<Room, String>, TableCell<Room, String>> cellButton = (TableColumn<Room, String> param) -> {
            // make cell containing buttons
            final TableCell<Room, String> cell = new TableCell<Room, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        Button btn = new Button();
                        Room room = getTableRow().getItem();
                        if(room.getStatus().equals("free")){
                            btn.setText("BOOK !");
                            btn.getStyleClass().add("button-style-4");

                        }
                        else if (room.getStatus().equals("booked")){
                            btn.setText("CHECK IN !");
                            btn.getStyleClass().add("button-style-5");
                        }
                        else {
                            btn.setText("CHECK OUT !");
                            btn.getStyleClass().add("button-style-6");
                        }

                        btn.setOnMouseClicked((MouseEvent event) ->{
                            Room chambre = getTableRow().getItem();
                            if(chambre.getStatus().equals("free")){
                               if(chambre.getType().equals("single")){
                                 FXMLLoader loader = new FXMLLoader(getClass().getResource("reservationsingle.fxml"));
                                   Pane pane = null;
                                   try {
                                       pane = loader.load();
                                   } catch (IOException e) {
                                       throw new RuntimeException(e);
                                   }
                                   SingleRoomReservation single = loader.getController();
                                   single.setRoom(chambre.getNum());
                                   Stage stag = new Stage(StageStyle.UNDECORATED);
                                   stag.setScene(new Scene(pane));
                                   stag.setY(200);
                                   stag.setX(470);
                                   stag.show();
                               } else if (chambre.getType().equals("double")) {
                                   FXMLLoader loader = new FXMLLoader(getClass().getResource("reservationdouble.fxml"));
                                   Pane pane = null;
                                   try {
                                       pane = loader.load();
                                   } catch (IOException e) {
                                       throw new RuntimeException(e);
                                   }
                                   DoubleRoomReservation single = loader.getController();
                                   single.setRoom(chambre.getNum());
                                   Stage stag = new Stage(StageStyle.UNDECORATED);
                                   stag.setScene(new Scene(pane));
                                   stag.setY(200);
                                   stag.setX(470);
                                   stag.show();
                               }else {
                                   FXMLLoader loader = new FXMLLoader(getClass().getResource("reservationtrible.fxml"));
                                   Pane pane = null;
                                   try {
                                       pane = loader.load();
                                   } catch (IOException e) {
                                       throw new RuntimeException(e);
                                   }
                                   TribleRoomReservation single = loader.getController();
                                   single.setRoom(chambre.getNum());
                                   Stage stag = new Stage(StageStyle.UNDECORATED);
                                   stag.setScene(new Scene(pane));
                                   stag.setY(140);
                                   stag.setX(470);
                                   stag.show();
                               }


                            }
                            else if(chambre.getStatus().equals("booked")){
                             FXMLLoader loader = new FXMLLoader(getClass().getResource("checkinroom.fxml"));
                             Pane pane = null;
                             try {
                                    pane = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                             Bookdialogue bookdialogue = loader.getController();
                             bookdialogue.setLabel("DO U REALLY WANT TO CHECK IN THIS ROOM !" , chambre.getNum() , 3);
                             Stage stage = new Stage(StageStyle.UNDECORATED);
                             stage.setX(530);
                             stage.setY(260);
                             stage.setScene(new Scene(pane));
                             stage.show();
                            }else {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("checkinroom.fxml"));
                                Pane pane = null;
                                try {
                                    pane = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Bookdialogue bookdialogue = loader.getController();
                                bookdialogue.setLabel("DO U REALLY WANT TO FREE THIS ROOM !" , chambre.getNum() , 1);
                                Stage stage = new Stage(StageStyle.UNDECORATED);
                                stage.setX(530);
                                stage.setY(260);
                                stage.setScene(new Scene(pane));
                                stage.show();
                            }
                        });





                        HBox managebtn = new HBox(btn);
                        managebtn.setStyle("-fx-alignment:center");
                        managebtn.setSpacing(3);
                        HBox.setMargin(btn, new Insets(2, 2, 0, 2));
                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };



        actions.setCellFactory(cellFoctory);
        changecol.setCellFactory(cellButton);
        numcol.setCellValueFactory(new PropertyValueFactory<Room , Integer>("num"));
        typecol.setCellValueFactory(new PropertyValueFactory<Room , String>("type"));
        statuscol.setCellValueFactory(new PropertyValueFactory<Room , String>("status"));

        FilteredList<Room> filteredList = new FilteredList<>(list , b -> true);
        search.textProperty().addListener((observable , oldval , newval) -> {
            filteredList.setPredicate(room -> {
                if (newval.isEmpty() || newval.isBlank() || newval == null){
                    return true;
                }
                String keywoard = newval.toLowerCase();
                if (room.getStatus().toLowerCase().indexOf(keywoard) > -1){
                    return true;
                }else if (room.getType().toLowerCase().indexOf(keywoard) > -1){
                    return true;
                } else if (String.valueOf(room.getNum()).toLowerCase().indexOf(keywoard) > -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Room> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    @FXML
    void addroom(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addroom.fxml"));
        Pane pane = loader.load();
        AddRoomController addroom = loader.getController();
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.setX(550);
        stage.setY(130);
        stage.setScene(new Scene(pane));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loaddata();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
