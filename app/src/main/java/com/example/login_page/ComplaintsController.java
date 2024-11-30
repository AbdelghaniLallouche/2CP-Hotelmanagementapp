package com.example.login_page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.sql.*;
import java.util.ResourceBundle;

public class ComplaintsController implements Initializable {
    @FXML
    TableView<Complaint> table;
    @FXML
    TableColumn<Complaint , Integer> idcol;
    @FXML
    TableColumn<Complaint,String> titlecol , compcol;
    @FXML
    TableColumn<Complaint , Date> cdatecol;
    @FXML
    TableColumn actions;
    ObservableList<Complaint> list = FXCollections.observableArrayList();
    @FXML
    public void onAdd() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addcomplaint.fxml"));
        Pane pane = loader.load();
        AddComplaintController addComplaintController = loader.getController();
        Scene scene = new Scene(pane);
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.setX(460);
        stage.setY(250);
        stage.setScene(scene);
        stage.show();
    }

    public void loaddata() throws SQLException {
        Connection connection = Connectdb.getConnect();
        String query = "SELECT * FROM `complaints` WHERE 1";
        PreparedStatement pr = connection.prepareStatement(query);
        ResultSet resultSet = pr.executeQuery();
        while (resultSet.next()){
            list.add(new Complaint(resultSet.getInt("id") , resultSet.getString("title") , resultSet.getString("complaint") , resultSet.getDate("date")));
            table.setItems(list);
        }


        Callback<TableColumn<Complaint, String>, TableCell<Complaint, String>> cellFoctory = (TableColumn<Complaint, String> param) -> {
            // make cell containing buttons
            final TableCell<Complaint, String> cell = new TableCell<Complaint, String>() {
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
                        ImageView viewIcon = new ImageView(new Image("file:///C:/Users/abdel/IdeaProjects/Project/Login_Page/src/main/resources/eye.png"));
                        viewIcon.setFitWidth(20);
                        viewIcon.setFitHeight(20);
                        viewIcon.setPreserveRatio(false);
                        viewIcon.setStyle(" -fx-cursor: hand");





                        viewIcon.setOnMouseClicked((MouseEvent event) ->{
                            Complaint complaint = table.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("seecomplaint.fxml"));
                            try {
                                Pane pane = loader.load();
                                ShowComplaintdetailsController show = loader.getController();
                                show.setdata(complaint.getTitle() , complaint.getComplaint() , complaint.getDate());
                                Stage stage = new Stage(StageStyle.UNDECORATED);
                                Scene scene = new Scene(pane);
                                stage.setX(530);
                                stage.setY(260);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        });



                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Complaint complaint = table.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Deletedialogue.fxml"));
                            try {
                                Pane pane = loader.load();
                                DeleteController deleteController = loader.getController();
                                deleteController.setId_Query(complaint.getId() , "DELETE FROM complaints WHERE id = ");
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
                            Complaint complaint = table.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editcomplaint.fxml"));
                            try {
                                Pane pane = fxmlLoader.load();
                                EditComplainController edot = fxmlLoader.getController();
                                edot.setdata(complaint.getTitle() , complaint.getComplaint() , complaint.getDate() , complaint.getId());
                                Stage stage = new Stage(StageStyle.UNDECORATED);
                                Scene scene = new Scene(pane);
                                stage.setY(240);
                                stage.setX(420);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }


                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon,viewIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        managebtn.setSpacing(3);
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 2));
                        HBox.setMargin(editIcon, new Insets(2, 2, 0, 2));
                        HBox.setMargin(viewIcon, new Insets(2, 2, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };



        actions.setCellFactory(cellFoctory);
        idcol.setCellValueFactory(new PropertyValueFactory<Complaint , Integer>("id"));
        titlecol.setCellValueFactory(new PropertyValueFactory<Complaint , String>("title"));
        compcol.setCellValueFactory(new PropertyValueFactory<Complaint , String>("complaint"));
        cdatecol.setCellValueFactory(new PropertyValueFactory<Complaint , Date>("date"));
    }
    @FXML
    public void refresh() throws SQLException {
        list.clear();
        loaddata();
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
