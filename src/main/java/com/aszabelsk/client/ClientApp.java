package com.aszabelsk.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
                new ConnectionErrorAlert().showAndWait();
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

    private void startChat(TextField usernameTextField) {
        client.logIn(usernameTextField.getText());
        ChatWindowController chatWindowController = new ChatWindowController(primaryStage, client);
        chatWindowController.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
