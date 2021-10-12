package com.aszabelsk;

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

    @Override
    public void start(Stage primaryStage) {
        initLoginDialog();
    }

    private void initLoginDialog() {
        Dialog<ButtonType> loginDialog = new Dialog<>();
        FXMLLoader fxmlLoader = new FXMLLoader();
        loadFxml(loginDialog, fxmlLoader);
        LoginDialogController controller = fxmlLoader.getController();
        initButtons(loginDialog, controller);
        loginDialog.setTitle("My Messaging App");
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
                String username = controller.getUsernameTextField().getText();
                client.start(username);
            } else {
                System.exit(0);
            }
        });
    }

    private void initButtons(Dialog<ButtonType> dialog, LoginDialogController controller) {
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        final Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                client = new Client();
            } catch (IOException e) {
                event.consume();
                showConnectionErrorAlert();
            }
        });

        controller.getUsernameTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(newValue.isEmpty());
        });
    }

    private void loadFxml(Dialog<ButtonType> dialog, FXMLLoader fxmlLoader) {
        fxmlLoader.setLocation(getClass().getResource("loginDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
