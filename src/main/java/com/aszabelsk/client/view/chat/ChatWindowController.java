package com.aszabelsk.client.view.chat;

import com.aszabelsk.client.TextUtils;
import com.aszabelsk.client.model.Client;
import com.aszabelsk.commons.Message;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatWindowController {

    private final Client client;

    private final Stage stage;
    private Parent root;

    @FXML
    private StackPane sendButton;

    @FXML
    private StackPane settingsButton;

    @FXML
    private StackPane addChatroomButton;

    @FXML
    private StackPane emojiButton;

    @FXML
    private TextArea messageTextArea;

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
        messageTextArea.requestFocus();
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
        messageListView.setCellFactory(new MessageListCellFactory(client.getUserUUID()));
    }

    private void addTooltips() {
        Tooltip.install(settingsButton, new Tooltip("Settings"));
        Tooltip.install(addChatroomButton, new Tooltip("Add new chatroom"));
        Tooltip.install(emojiButton, new Tooltip("Add emoji"));
        Tooltip.install(sendButton, new Tooltip("Send message"));
    }

    public void start() {
        registerListeners();
        client.start();
    }

    private void registerListeners() {
        sendButton.setOnMouseClicked(event -> sendMessage());

        messageTextArea
                .setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        if (event.isAltDown()) {
                            TextUtils.insertText(messageTextArea, "\n");
                        } else {
                            sendMessage();
                        }
                        event.consume();
                    }
                });

        client.lastMessageProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> messages.add(newValue));
        });

        emojiButton.setOnMouseClicked(event -> showEmojiMenu());

        addChatroomButton.setOnMouseClicked(event -> showAddChatroomPopup());
    }

    private void sendMessage() {
        String message = messageTextArea.getText();
        if (!message.isEmpty()) {
            client.sendMessage(message);
            messageTextArea.clear();
        }
    }

    private void showEmojiMenu() {
        EmojiMenu emojiMenu = EmojiMenu.getInstance(messageTextArea);
        emojiMenu.show(emojiButton);
    }

    private void showAddChatroomPopup() {
        //TODO
    }

    @FXML
    public void initialize() {
    }
}
