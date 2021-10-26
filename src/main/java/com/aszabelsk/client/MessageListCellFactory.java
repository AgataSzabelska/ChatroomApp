package com.aszabelsk.client;

import com.aszabelsk.commons.Message;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MessageListCellFactory implements Callback<ListView<Message>, ListCell<Message>> {
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
                } else {
                    setGraphic(null);
                }
            }

            private String formatDate(Message item) {
                ZonedDateTime zonedDateTime = item.getZonedDateTime();
                ZonedDateTime convertedDateTime = zonedDateTime.withZoneSameInstant(ZonedDateTime.now().getZone());
                return convertedDateTime.format(formatter);
            }
        };
    }
}