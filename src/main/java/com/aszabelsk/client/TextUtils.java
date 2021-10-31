package com.aszabelsk.client;

import javafx.scene.control.TextArea;

public class TextUtils {
    public static void insertText(TextArea messageTextArea, String text) {
        int caretPosition = messageTextArea.getCaretPosition();
        StringBuilder stringBuilder = new StringBuilder(messageTextArea.getText());
        stringBuilder.insert(caretPosition, text);
        messageTextArea.setText(stringBuilder.toString());
        messageTextArea.positionCaret(caretPosition + text.length());
    }
}
