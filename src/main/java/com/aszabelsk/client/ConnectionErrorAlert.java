package com.aszabelsk.client;

import javafx.scene.control.Alert;

import java.util.Objects;

public class ConnectionErrorAlert extends Alert {

    public ConnectionErrorAlert() {
        super(Alert.AlertType.INFORMATION);
        setHeaderText("Cannot connect to server");
        getDialogPane().getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
    }
}
