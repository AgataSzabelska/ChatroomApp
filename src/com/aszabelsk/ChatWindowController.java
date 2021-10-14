package com.aszabelsk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatWindowController {
    private final Client client;

    private Stage stage;
    private HBox root;

    public ChatWindowController(Stage stage, Client client) {
        this.stage = stage;
        this.client = client;
        loadFxml();
        initStage();
    }

    private void initStage() {
        stage.setTitle("Messaging App");
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(event -> {
            client.disconnect();
        });
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
