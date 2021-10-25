package com.aszabelsk.client;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginDialogController {
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize() {
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }
}
