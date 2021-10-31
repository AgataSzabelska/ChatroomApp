package com.aszabelsk.client.view.chat;

import com.aszabelsk.client.TextUtils;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Popup;

import java.util.Arrays;
import java.util.Iterator;

public class EmojiMenu extends Popup {

    private static EmojiMenu instance;

    private final GridPane root = new GridPane();

    private final int rows = 5;
    private final int columns = 4;

    public static EmojiMenu getInstance(TextArea messageTextArea) {
        if (instance == null) {
            instance = new EmojiMenu(messageTextArea);
        }
        return instance;
    }

    private EmojiMenu(TextArea messageTextArea) {
        initEmojiButtons(messageTextArea);
        getContent().add(root);
        setAutoHide(true);
        root.getStylesheets().add("com/aszabelsk/client/view/styles.css");
        root.getStyleClass().add("root");
    }

    private void initEmojiButtons(TextArea messageTextArea) {
        Iterator<Emoji> it = Arrays.stream(Emoji.values()).iterator();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                root.add(initEmojiButton(messageTextArea, it.next()), col, row);
            }
        }
    }

    private Button initEmojiButton(TextArea messageTextArea, Emoji emoji) {
        Button emojiButton = new Button(emoji.getValue());
        emojiButton.getStyleClass().add("icon-button");
        emojiButton.setOnAction(event -> {
            messageTextArea.requestFocus();
            TextUtils.insertText(messageTextArea, emojiButton.getText());
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
