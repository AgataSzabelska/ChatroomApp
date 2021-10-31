package com.aszabelsk.client;

import javafx.scene.control.TextArea;

public class TextUtils {
    public static void insertText(TextArea textArea, String text) {
        int caretPosition = textArea.getCaretPosition();
        StringBuilder stringBuilder = new StringBuilder(textArea.getText());
        stringBuilder.insert(caretPosition, text);
        textArea.setText(stringBuilder.toString());
        textArea.positionCaret(caretPosition + text.length());
    }
}
