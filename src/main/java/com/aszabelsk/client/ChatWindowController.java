package com.aszabelsk.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatWindowController {

    private final Client client;

    private Stage stage;
    private VBox root;

    @FXML
    private StackPane sendButton;

    @FXML
    private StackPane settingsButton;

    @FXML
    private StackPane addChatroomButton;

    @FXML
    private StackPane emojiButton;

    @FXML
    private TextField messageField;

    @FXML
    private ListView<String> messageListView; //TODO <Message>

    private final ObservableList<String> messages = FXCollections.observableArrayList();

    public ChatWindowController(Stage stage, Client client) {
        this.stage = stage;
        this.client = client;
        loadFxml();
        initStage();
        initMessageListView();
        addTooltips();
        registerListeners(client);
        client.start(messages);
    }

    private void addTooltips() {
        Tooltip.install(settingsButton, new Tooltip("Settings"));
        Tooltip.install(addChatroomButton, new Tooltip("Add new chatroom"));
        Tooltip.install(emojiButton, new Tooltip("Add emoji"));
        Tooltip.install(sendButton, new Tooltip("Send message"));
    }

    private void registerListeners(Client client) {
        sendButton.setOnMouseClicked(event -> {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                client.sendMessage(message);
                messageField.clear();
            }
        });
    }

    private void initMessageListView() {
        messageListView.setItems(messages);
        // TODO cell factory
    }

    private void initStage() {
        stage.setTitle("Chatroom App");
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
