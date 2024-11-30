package com.example.login_page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class ManageFinancesController implements Initializable {
     int s;
        @FXML
        private TableColumn actions;

        @FXML
        private TableColumn<Expense, Integer> costcol;

        @FXML
        private TableColumn<Expense, String> spentcol;

        @FXML
        private TableView<Expense> table;
        ObservableList<Expense> list = FXCollections.observableArrayList();

        void loaddata() throws SQLException {
            Connection connection = Connectdb.getConnect();
            String query = "SELECT * FROM losses";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(new Expense(resultSet.getInt("id"), resultSet.getInt("cost") , resultSet.getString("spenton") , resultSet.getDate("date")));
                table.setItems(list);
            }




            Callback<TableColumn<Expense, String>, TableCell<Expense, String>> cellFoctory = (TableColumn<Expense, String> param) -> {
                // make cell containing buttons
                final TableCell<Expense, String> cell = new TableCell<Expense, String>() {
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
                                Expense expense = table.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("Deletedialogue.fxml"));
                                try {
                                    Pane pane = loader.load();
                                    DeleteController deleteController = loader.getController();
                                    deleteController.setId_Query(expense.getId() , "DELETE FROM losses WHERE id = ");
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
                                Expense expense = table.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("editfinance.fxml"));
                                Pane pane = null;
                                try {
                                    pane = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                EditFinance editFinance = loader.getController();
                                Connection con = Connectdb.getConnect();
                                PreparedStatement pre = null;
                                try {
                                    pre = con.prepareStatement("SELECT `id` FROM `expenses` WHERE `cost` = ? and `description` = ? and `date` = ?");
                                    pre.setInt(1 , expense.getCost());
                                    pre.setString(2 , expense.getSpent());
                                    pre.setDate(3, expense.getDate());
                                    ResultSet res = pre.executeQuery();
                                    res.next();
                                    s = res.getInt("id");
                                } catch (SQLException e) {
                                   System.out.println(e.getMessage());
                                }


                                editFinance.setData(expense.getId() , expense.getCost() , expense.getSpent() , expense.getDate() , s);
                                Stage stage = new Stage(StageStyle.UNDECORATED);
                                stage.setX(470);
                                stage.setY(230);
                                stage.setScene(new Scene(pane));
                                stage.show();

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
            costcol.setCellValueFactory(new PropertyValueFactory<Expense , Integer>("cost"));
            spentcol.setCellValueFactory(new PropertyValueFactory<Expense , String>("spent"));
        }
        @FXML
        void add() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addfinance.fxml"));
            Pane pane = loader.load();
            AddFinance addFinance = loader.getController();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setX(470);
            stage.setY(230);
            stage.setScene(new Scene(pane));
            stage.show();
        }

        @FXML
        void refresh() throws SQLException {
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



