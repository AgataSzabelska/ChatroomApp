package com.aszabelsk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Dialog<ButtonType> dialog = new Dialog<>();
        FXMLLoader fxmlLoader = new FXMLLoader();
        loadFxml(dialog, fxmlLoader);
        LoginDialogController controller = fxmlLoader.getController();
        initButtons(dialog, controller);

        dialog.setTitle("My Messaging App");

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("Hello, " + controller.getUsernameTextField().getText());
                Client client = new Client();
                client.start();
            } else {
                System.exit(0);
            }
        });
    }

    private void initButtons(Dialog<ButtonType> dialog, LoginDialogController controller) {
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
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
