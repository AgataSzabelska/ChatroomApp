package com.aszabelsk.client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Arrays;
import java.util.Iterator;

public class EmojiMenu extends GridPane {

    private Stage stage;
    private GridPane root;

    private int rows = 5;
    private int columns = 4;

    public EmojiMenu(Stage ownerStage, TextField messageField) {
        initEmojiButtons(messageField);
        initStage(ownerStage);
    }

    private void initEmojiButtons(TextField messageField) {
        Iterator<Emoji> it = Arrays.stream(Emoji.values()).iterator();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                add(initEmojiButton(messageField, it), col, row);
            }
        }
    }

    private Button initEmojiButton(TextField messageField, Iterator<Emoji> it) {
        Button emojiButton = new Button(it.next().getValue());
        emojiButton.setOnAction(event -> {
            int caretPosition = messageField.getCaretPosition();
            StringBuilder text = new StringBuilder(messageField.getText());
            text.insert(caretPosition, emojiButton.getText());
            messageField.setText(text.toString());
            //TODO fix caret pos
            stage.close();
        });
        return emojiButton;
    }

    private void initStage(Stage ownerStage) {
        stage = new Stage(StageStyle.UNDECORATED);
        stage.initOwner(ownerStage);
        stage.setScene(new Scene(this));
    }

    public void show() {
        stage.show();
    }
}
