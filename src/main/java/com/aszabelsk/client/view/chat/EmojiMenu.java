package com.aszabelsk.client.view.chat;

import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Popup;

import java.util.Arrays;
import java.util.Iterator;

public class EmojiMenu extends Popup {

    private GridPane root = new GridPane();

    private final int rows = 5;
    private final int columns = 4;

    public EmojiMenu(TextField messageField) {
        initEmojiButtons(messageField);
        getContent().add(root);
        setAutoHide(true);
        root.getStylesheets().add("com/aszabelsk/client/view/styles.css");
    }

    private void initEmojiButtons(TextField messageField) {
        Iterator<Emoji> it = Arrays.stream(Emoji.values()).iterator();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                root.add(initEmojiButton(messageField, it.next()), col, row);
            }
        }
    }

    private Button initEmojiButton(TextField messageField, Emoji emoji) {
        Button emojiButton = new Button(emoji.getValue());
        emojiButton.getStyleClass().add("icon-button");
        emojiButton.setOnAction(event -> {
            messageField.requestFocus();
            int caretPosition = messageField.getCaretPosition();
            StringBuilder stringBuilder = new StringBuilder(messageField.getText());
            stringBuilder.insert(caretPosition, emojiButton.getText());
            messageField.setText(stringBuilder.toString());
            messageField.positionCaret(caretPosition + 2);
            hide();
        });
        return emojiButton;
    }

    public void show(Region ownerNode) {
        Bounds boundsInScreen = ownerNode.localToScreen(ownerNode.getBoundsInLocal());
        show(ownerNode, 0, 0);
        setX(boundsInScreen.getMinX() - getWidth() + ownerNode.getWidth());
        setY(boundsInScreen.getMinY() - getHeight());
    }
}
