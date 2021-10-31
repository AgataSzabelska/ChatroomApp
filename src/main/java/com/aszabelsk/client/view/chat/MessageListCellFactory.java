package com.aszabelsk.client.view.chat;

import com.aszabelsk.commons.Message;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MessageListCellFactory implements Callback<ListView<Message>, ListCell<Message>> {

    private final UUID userUUID;

    private final String loggedInUserMessageStyleClass = "logged-in-user-message";
    private final String otherUserMessageStyleClass = "other-user-message";
    private final String messageBoxStyleClass = "message-box";

    public MessageListCellFactory(UUID userUUID) {
        this.userUUID = userUUID;
    }

    @Override
    public ListCell<Message> call(ListView<Message> p) {
        return new ListCell<Message>() {
            final VBox root = new VBox();
            final Label usernameLabel = new Label();
            final Label dateLabel = new Label();
            final Label messageTextLabel = new Label();
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy");

            {
                HBox.setHgrow(usernameLabel, Priority.ALWAYS);
                usernameLabel.setMaxWidth(10E6);
                usernameLabel.getStyleClass().add("bold-label");
                messageTextLabel.setWrapText(true);
                root.getChildren().add(new HBox(usernameLabel, dateLabel));
                root.getChildren().add(messageTextLabel);
                root.getStyleClass().add(messageBoxStyleClass);
                listViewProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        root.prefWidthProperty().bind(getListView().widthProperty().subtract(60));
                    }
                });
            }

            @Override
            protected void updateItem(Message item, boolean bln) {
                super.updateItem(item, bln);
                if (item != null) {
                    usernameLabel.setText(item.getUsername());
                    dateLabel.setText(formatDate(item));
                    messageTextLabel.setText(item.getMessage());
                    setGraphic(root);
                    setStyleClass(item.getUserUUID());
                } else {
                    setGraphic(null);
                }
            }

            private String formatDate(Message item) {
                ZonedDateTime zonedDateTime = item.getZonedDateTime();
                ZonedDateTime convertedDateTime = zonedDateTime.withZoneSameInstant(ZonedDateTime.now().getZone());
                return convertedDateTime.format(formatter);
            }

            private void setStyleClass(UUID messageAuthorUUID) {
                ObservableList<String> styleClassList = root.getStyleClass();
                String messageStyleClass;
                if (messageAuthorUUID.equals(userUUID)) {
                    messageStyleClass = loggedInUserMessageStyleClass;
                    styleClassList.removeAll(otherUserMessageStyleClass);
                } else {
                    messageStyleClass = otherUserMessageStyleClass;
                    styleClassList.removeAll(loggedInUserMessageStyleClass);
                }
                if (!styleClassList.contains(messageStyleClass)) {
                    styleClassList.add(messageStyleClass);
                }
            }
        };
    }
}
