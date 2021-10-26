package com.aszabelsk.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EmojiMenuController {

    private final Stage stage;
    private GridPane root;

    public EmojiMenuController(Stage stage) {
        this.stage = stage;
        loadFxml();
    }

    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        fxmlLoader.setLocation(getClass().getResource("emojiMenu.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        stage.setScene(new Scene(root));
        stage.show();
    }
}
