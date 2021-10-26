package com.aszabelsk.client;

import com.aszabelsk.commons.Message;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatWindowController {

    private final Client client;

    private final Stage stage;
    private GridPane root;

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
    private ListView<Message> messageListView;

    private final ObservableList<Message> messages = FXCollections.observableArrayList();

    public ChatWindowController(Stage stage, Client client) {
        this.stage = stage;
        this.client = client;
        initView();
    }

    private void initView() {
        loadFxml();
        initStage();
        initMessageListView();
        addTooltips();
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

    private void initStage() {
        stage.setTitle("Chatroom App");
        stage.setScene(new Scene(root, 900, 600));
        stage.setOnCloseRequest(event -> client.disconnect());
        stage.show();
    }

    private void initMessageListView() {
        messageListView.setItems(messages);
        messageListView.setCellFactory(new MessageListCellFactory());
    }

    private void addTooltips() {
        Tooltip.install(settingsButton, new Tooltip("Settings"));
        Tooltip.install(addChatroomButton, new Tooltip("Add new chatroom"));
        Tooltip.install(emojiButton, new Tooltip("Add emoji"));
        Tooltip.install(sendButton, new Tooltip("Send message"));
    }

    public void start() {
        registerListeners(client);
        client.start();
    }

    private void registerListeners(Client client) {
        sendButton.setOnMouseClicked(event -> {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                client.sendMessage(message);
                messageField.clear();
            }
        });

        client.lastMessageProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> messages.add(newValue));
        });

        emojiButton.setOnMouseClicked(event -> showEmojiMenu());
    }

    private void showEmojiMenu() {
        EmojiMenu emojiMenu = new EmojiMenu(stage, messageField);
        emojiMenu.show(emojiButton);
    }

    @FXML
    public void initialize() {
    }
}
