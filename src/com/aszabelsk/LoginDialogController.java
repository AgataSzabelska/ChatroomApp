package com.aszabelsk;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginDialogController {
    @FXML
    private TextField usernameTextField;

    @FXML
    public void initialize() {

    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }
}
