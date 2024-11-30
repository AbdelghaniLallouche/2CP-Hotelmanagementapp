package com.example.login_page;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DialogueController {
    @FXML
    Pane alertPane;
    @FXML
    Label title,content;

    public void setContent_title(String content , String title) {
        this.content.setText(content);
        this.title.setText(title);
    }

    @FXML
    private void closeAlert() {
        Stage stage = (Stage) alertPane.getScene().getWindow();
        stage.close();
    }
}
