package com.aszabelsk.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
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
        promptLogin();
    }

    private void promptLogin() {
        Dialog<ButtonType> loginDialog = new Dialog<>();
        loginDialog.setTitle("My Chatroom App");
        FXMLLoader fxmlLoader = new FXMLLoader();
        loadLoginDialogFxml(loginDialog, fxmlLoader);
        TextField usernameTextField = ((LoginDialogController) fxmlLoader.getController()).getUsernameTextField();
        initButtons(loginDialog, usernameTextField);
        showLoginDialog(loginDialog, usernameTextField);
    }

    private void loadLoginDialogFxml(Dialog<ButtonType> dialog, FXMLLoader fxmlLoader) {
        fxmlLoader.setLocation(getClass().getResource("loginDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons(Dialog<ButtonType> dialog, TextField usernameTextField) {
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.isEmpty());
        });
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                client = new Client();
            } catch (IOException e) {
                event.consume();
                showConnectionErrorAlert();
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

    private void showLoginDialog(Dialog<ButtonType> loginDialog, TextField usernameTextField) {
        loginDialog.setOnShowing(event -> usernameTextField.requestFocus());
        loginDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                startChat(usernameTextField);
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
