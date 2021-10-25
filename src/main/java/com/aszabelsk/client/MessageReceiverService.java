package com.aszabelsk.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageReceiverService extends Service<Void> {
    private final BufferedReader reader;
    private final StringProperty lastMessageProperty = new SimpleStringProperty();

    public MessageReceiverService(BufferedReader reader) {
        this.reader = reader;
    }

    protected Task<Void> createTask() {
        return new Task<Void>() {
            protected Void call() {
                String message;
                try {
                    while ((message = reader.readLine()) != null) {
                        lastMessageProperty.set(message);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    public StringProperty lastMessageProperty() {
        return lastMessageProperty;
    }
}
