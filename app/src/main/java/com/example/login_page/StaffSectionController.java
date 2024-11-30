package com.example.login_page;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

public class StaffSectionController implements Initializable {
    @FXML
    TableView<StaffMembre> table;
    @FXML
    TextField search;
    @FXML
    TableColumn<StaffMembre, Integer> idcol, salarycol;
    @FXML
    TableColumn<StaffMembre, String> namecol, staffcol, shiftcol, actions;
    @FXML
    TableColumn<StaffMembre, Date> jdatecol;

    ObservableList<StaffMembre> list = FXCollections.observableArrayList();


    public void loaddata() throws SQLException {
        String query = "SELECT id , name , sections.sectionname , shift , joiningdate , salary FROM staff , sections WHERE sections.sectionid = staff.section";
        Connection connection = Connectdb.getConnect();
        Statement statement = connection.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            list.add(new StaffMembre(resultSet.getInt("id"), resultSet.getInt("salary"), resultSet.getString("name"), resultSet.getString("sectionname"), resultSet.getString("shift"), resultSet.getDate("joiningdate")));
            table.setItems(list);
        }


        Callback<TableColumn<StaffMembre, String>, TableCell<StaffMembre, String>> cellFoctory = (TableColumn<StaffMembre, String> param) -> {
            // make cell containing buttons
            final TableCell<StaffMembre, String> cell = new TableCell<StaffMembre, String>() {
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
                            StaffMembre st = table.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Deletedialogue.fxml"));
                            try {
                                Pane pane = loader.load();
                                DeleteController deleteController = loader.getController();
                                deleteController.setId_Query(st.getId() , "DELETE FROM staff WHERE `staff`.`id` = ");
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
                            StaffMembre staffMembre = table.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("editstaffmembre.fxml"));
                            try {
                                Pane pane = loader.load();
                                EditStaffmembreController editstaffController = loader.getController();
                                editstaffController.setsct(staffMembre.getSection());
                                editstaffController.setId(staffMembre.getId());
                                editstaffController.setA(getA(staffMembre.getId()));
                                editstaffController.seteverything(staffMembre.getName() , staffMembre.shift , staffMembre.salary, staffMembre.jdate);
                                Scene sc = new Scene(pane);
                                Stage stage = new Stage(StageStyle.UNDECORATED);
                                stage.setScene(sc);
                                stage.setX(460);
                                stage.setY(230);
                                stage.show();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 2));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };


        actions.setCellFactory(cellFoctory);
        idcol.setCellValueFactory(new PropertyValueFactory<StaffMembre, Integer>("id"));
        salarycol.setCellValueFactory(new PropertyValueFactory<StaffMembre, Integer>("salary"));
        namecol.setCellValueFactory(new PropertyValueFactory<StaffMembre, String>("name"));
        staffcol.setCellValueFactory(new PropertyValueFactory<StaffMembre, String>("section"));
        shiftcol.setCellValueFactory(new PropertyValueFactory<StaffMembre, String>("shift"));
        jdatecol.setCellValueFactory(new PropertyValueFactory<StaffMembre, Date>("jdate"));


        FilteredList<StaffMembre> filteredList = new FilteredList<>(list , b -> true);
        search.textProperty().addListener((observable , oldval , newval) -> {
            filteredList.setPredicate(staffMembre -> {
                if (newval.isEmpty() || newval.isBlank() || newval == null){
                    return true;
                }
                String keywoard = newval.toLowerCase();
                if (staffMembre.getName().toLowerCase().indexOf(keywoard) > -1){
                    return true;
                }else {
                    return false;
                }
            });
        });
        SortedList<StaffMembre> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    public int getA(int a) throws SQLException {
        int s = 0;
        Connection cn = Connectdb.getConnect();
        PreparedStatement pr = cn.prepareStatement("SELECT id , name , sections.sectionid FROM staff , sections WHERE staff.section = sections.sectionid AND id =" + a );
        ResultSet re = pr.executeQuery();
        while (re.next()){
           s = re.getInt("sectionid");
        }
        return s;
    }

    @FXML
    public void refresh() throws SQLException {
        list.clear();
        loaddata();
    }

    @FXML
    public void addmembre() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addstaffmembre.fxml"));
        Pane pane = loader.load();
        AddstaffmembreController addstaffmembreController = loader.getController();
        Scene scene = new Scene(pane);
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setX(460);
        stage.setY(230);
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
