package com.aszabelsk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatWindowController {
    private final Client client;

    private Stage stage;
    private HBox root;

    @FXML
    private Button sendButton;

    @FXML
    private TextField messageField;

    @FXML
    private ListView<String> messageListView; //TODO <Message>

    private final ObservableList<String> messages = FXCollections.emptyObservableList();

    public ChatWindowController(Stage stage, Client client) {
        this.stage = stage;
        this.client = client;
        loadFxml();
        initStage();
        initMessageListView();
        registerListeners(client);
        client.start(messages);
    }

    private void registerListeners(Client client) {
        sendButton.setOnAction(event -> {
            client.sendMessage(messageField.getText());
            messageField.clear();
        });
    }

    private void initMessageListView() {
        messageListView.setItems(messages);
        // TODO cell factory
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
