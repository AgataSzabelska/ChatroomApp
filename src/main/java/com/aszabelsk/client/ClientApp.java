package com.aszabelsk.client;

import com.aszabelsk.client.model.Client;
import com.aszabelsk.client.view.ConnectionErrorAlert;
import com.aszabelsk.client.view.LoginDialog;
import com.aszabelsk.client.view.chat.ChatWindowController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
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
        LoginDialog loginDialog = new LoginDialog();
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
                startChat(loginDialog.getUsernameTextField().getText());
            } else {
                System.exit(0);
            }
        });
    }

    private void startChat(String username) {
        client.logIn(username);
        ChatWindowController chatWindowController = new ChatWindowController(primaryStage, client);
        chatWindowController.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
