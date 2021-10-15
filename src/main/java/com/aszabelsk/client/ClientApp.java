package com.aszabelsk.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {

    private Client client;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Dialog<ButtonType> loginDialog = new Dialog<>();
        FXMLLoader fxmlLoader = new FXMLLoader();
        loadLoginDialogFxml(loginDialog, fxmlLoader);
        LoginDialogController controller = fxmlLoader.getController();
        initButtons(loginDialog, controller);
        loginDialog.setTitle("My Chatroom App");
        showLoginDialog(loginDialog, controller);
    }

    private void showConnectionErrorAlert() {
        Alert connectionErrorAlert = new Alert(Alert.AlertType.INFORMATION);
        connectionErrorAlert.setHeaderText("Cannot connect to server");
        connectionErrorAlert.showAndWait();
    }

    private void showLoginDialog(Dialog<ButtonType> loginDialog, LoginDialogController controller) {
        loginDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                startChat(controller);
            } else {
                System.exit(0);
            }
        });
    }

    private void startChat(LoginDialogController controller) {
        client.logIn(controller.getUsernameTextField().getText());
        ChatWindowController chatWindowController = new ChatWindowController(primaryStage, client);
    }

    private void initButtons(Dialog<ButtonType> dialog, LoginDialogController controller) {
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);

        okButton.setDisable(true);
        controller.getUsernameTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(newValue.isEmpty());
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

    private void loadLoginDialogFxml(Dialog<ButtonType> dialog, FXMLLoader fxmlLoader) {
        fxmlLoader.setLocation(getClass().getResource("loginDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
