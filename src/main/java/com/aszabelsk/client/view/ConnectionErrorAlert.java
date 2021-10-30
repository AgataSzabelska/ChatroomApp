package com.aszabelsk.client.view;

import javafx.scene.control.Alert;

import java.util.Objects;

public class ConnectionErrorAlert extends Alert {

    public ConnectionErrorAlert() {
        super(Alert.AlertType.INFORMATION);
        setHeaderText(null);
        setGraphic(null);
        setContentText("Cannot connect to server");
        getDialogPane().getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
    }
}
