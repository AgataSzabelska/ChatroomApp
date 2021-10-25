package com.aszabelsk.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class MessageReceiverService extends Service<List<String>> {
    private final BufferedReader reader;
    private final StringProperty lastMessageProperty = new SimpleStringProperty();

    public MessageReceiverService(BufferedReader reader) {
        this.reader = reader;
    }

    protected Task<List<String>> createTask() {
        return new Task<List<String>>() {
            protected List<String> call() {
                String message;
                try {
                    while ((message = reader.readLine()) != null) {
                        lastMessageProperty.set(message);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return getValue();
            }
        };
    }

    public StringProperty lastMessageProperty() {
        return lastMessageProperty;
    }
}
