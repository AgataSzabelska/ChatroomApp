package com.aszabelsk.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ClientApp extends Application {

    private Client client;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginDialog();
    }

    private void showLoginDialog() {
        LoginDialog<ButtonType> loginDialog = new LoginDialog<>();
        loginDialog.getOkButton().addEventFilter(ActionEvent.ACTION, event -> {
            try {
                client = new Client();
            } catch (IOException e) {
                event.consume();
                showConnectionErrorAlert();
            }
        });
        loginDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                startChat(loginDialog.getUsernameTextField());
            } else {
                System.exit(0);
            }
        });
    }

    private void showConnectionErrorAlert() {
        Alert connectionErrorAlert = new Alert(Alert.AlertType.INFORMATION); //TODO separate class?
        connectionErrorAlert.setHeaderText("Cannot connect to server");
        connectionErrorAlert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        connectionErrorAlert.showAndWait();
    }

    private void startChat(TextField usernameTextField) {
        client.logIn(usernameTextField.getText());
        ChatWindowController chatWindowController = new ChatWindowController(primaryStage, client);
        chatWindowController.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
