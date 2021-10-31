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
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientApp extends Application {

    private Client client;

    private Stage primaryStage;

    private ResourceBundle resourceBundle;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        resourceBundle = ResourceBundle.getBundle("com/aszabelsk/client/view/translation", new Locale(Locale.getDefault().getLanguage()));
        showLoginDialog();
    }

    private void showLoginDialog() {
        LoginDialog loginDialog = new LoginDialog(resourceBundle);
        loginDialog.getOkButton().addEventFilter(ActionEvent.ACTION, event -> {
            try {
                client = new Client();
            } catch (IOException e) {
                event.consume();
                new ConnectionErrorAlert(resourceBundle).showAndWait();
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
        ChatWindowController chatWindowController = new ChatWindowController(primaryStage, client, resourceBundle);
        chatWindowController.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
