package com.aszabelsk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatWindowController {
    private final Client client;

    private HBox root;

    public ChatWindowController(Client client) {
        this.client = client;
        loadFxml();
        initStage();
    }

    private void initStage() {
        Stage stage = new Stage();
        stage.setTitle("Messaging App");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        fxmlLoader.setLocation(getClass().getResource("chatWindow.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {

    }
}
