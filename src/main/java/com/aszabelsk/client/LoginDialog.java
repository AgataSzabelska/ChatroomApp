package com.aszabelsk.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginDialog<T> extends Dialog<T> {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    private Button okButton;

    public LoginDialog() {
        setTitle("My Chatroom App");
        loadFxml();
        initButtons();
        getDialogPane().getStylesheets().add((this.getClass().getResource("styles.css").toExternalForm()));
        setOnShowing(event -> usernameTextField.requestFocus());
    }

    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        fxmlLoader.setLocation(getClass().getResource("loginDialog.fxml"));
        try {
            getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        getDialogPane().getButtonTypes().add(ButtonType.OK);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.isEmpty());
        });

    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public Button getOkButton() {
        return okButton;
    }
}