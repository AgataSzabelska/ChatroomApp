package com.aszabelsk.client.view.chat;

import com.aszabelsk.commons.Message;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
            final GridPane root = new GridPane();
            final Label usernameLabel = new Label();
            final Label dateLabel = new Label();
            final Label messageTextLabel = new Label();
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm  dd-MM-yyyy");

            {
                messageTextLabel.setWrapText(true);
                root.add(usernameLabel, 0, 0);
                root.add(dateLabel, 1, 0);
                root.add(messageTextLabel, 0, 1);
                root.getStyleClass().add(messageBoxStyleClass);
                GridPane.setHgrow(usernameLabel, Priority.ALWAYS);
                GridPane.setHgrow(messageTextLabel, Priority.ALWAYS);
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
                String messageStyleClass;
                if (messageAuthorUUID.equals(userUUID)) {
                    messageStyleClass = loggedInUserMessageStyleClass;
                } else {
                    messageStyleClass = otherUserMessageStyleClass;
                }
                ObservableList<String> styleClassList = root.getStyleClass();
                if (!styleClassList.contains(messageStyleClass)) {
                    styleClassList.add(messageStyleClass);
                }
            }
        };
    }
}