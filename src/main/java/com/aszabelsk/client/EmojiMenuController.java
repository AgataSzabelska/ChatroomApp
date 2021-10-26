package com.aszabelsk.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EmojiMenuController {

    private Stage stage;
    private GridPane root;

    public EmojiMenuController(Stage ownerStage) {
        loadFxml();
        initStage(ownerStage);
    }

    private void initStage(Stage ownerStage) {
        stage = new Stage(StageStyle.UNDECORATED);
        stage.initOwner(ownerStage);
//        stage.initModality(Mo);
        stage.setScene(new Scene(root));
    }

    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("emojiMenu.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        stage.show();
    }
}
