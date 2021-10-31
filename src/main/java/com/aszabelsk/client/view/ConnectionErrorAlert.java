package com.aszabelsk.client.view;

import javafx.scene.control.Alert;

import java.util.Objects;
import java.util.ResourceBundle;

public class ConnectionErrorAlert extends Alert {

    public ConnectionErrorAlert(ResourceBundle resourceBundle) {
        super(Alert.AlertType.INFORMATION);
        setHeaderText(null);
        setGraphic(null);
        setContentText(resourceBundle.getString("connectionErrorAlert.cannotConnectToServer"));
        getDialogPane().getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
    }
}
