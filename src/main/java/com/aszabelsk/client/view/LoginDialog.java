package com.aszabelsk.client.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginDialog extends Dialog<ButtonType> {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    private Button okButton;

    public LoginDialog(ResourceBundle resourceBundle) {
        setTitle(resourceBundle.getString("loginDialog.loginTitle"));
        loadFxml(resourceBundle);
        initButtons();
        getDialogPane().getStylesheets().add((Objects.requireNonNull(this.getClass().getResource("styles.css")).toExternalForm()));
        setOnShowing(event -> usernameTextField.requestFocus());
    }

    private void loadFxml(ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        fxmlLoader.setResources(resourceBundle);
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
