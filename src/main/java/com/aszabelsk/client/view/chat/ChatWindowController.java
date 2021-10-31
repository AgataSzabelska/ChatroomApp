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
import java.util.ResourceBundle;

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

    public ChatWindowController(Stage stage, Client client, ResourceBundle resourceBundle) {
        this.stage = stage;
        this.client = client;
        initView(resourceBundle);
    }

    private void initView(ResourceBundle resourceBundle) {
        loadFxml(resourceBundle);
        initStage(resourceBundle);
        initMessageListView();
        addTooltips(resourceBundle);
        messageTextArea.requestFocus();
    }

    private void loadFxml(ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        fxmlLoader.setResources(resourceBundle);
        fxmlLoader.setLocation(getClass().getResource("chatWindow.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initStage(ResourceBundle resourceBundle) {
        stage.setTitle(resourceBundle.getString("chatWindowController.chatroomTitle"));
        stage.setScene(new Scene(root, 900, 600));
        stage.setOnCloseRequest(event -> client.disconnect());
        stage.show();
    }

    private void initMessageListView() {
        messageListView.setItems(messages);
        messageListView.setCellFactory(new MessageListCellFactory(client.getUserUUID()));
    }

    private void addTooltips(ResourceBundle resourceBundle) {
        Tooltip.install(settingsButton, new Tooltip(resourceBundle.getString("chatWindowController.settings")));
        Tooltip.install(addChatroomButton, new Tooltip(resourceBundle.getString("chatWindowController.addNewChatroom")));
        Tooltip.install(emojiButton, new Tooltip(resourceBundle.getString("chatWindowController.insertEmoji")));
        Tooltip.install(sendButton, new Tooltip(resourceBundle.getString("chatWindowController.sendMessage")));
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
