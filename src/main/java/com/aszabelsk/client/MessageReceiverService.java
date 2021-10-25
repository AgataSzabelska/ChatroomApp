package com.aszabelsk.client;

import com.aszabelsk.commons.Message;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageReceiverService extends Service<Void> {
    private final ObjectInputStream reader;
    private final ObjectProperty<Message> lastMessageProperty = new SimpleObjectProperty<>();
    private boolean stop;

    public MessageReceiverService(ObjectInputStream reader) {
        this.reader = reader;
    }

    protected Task<Void> createTask() {
        return new Task<Void>() {
            protected Void call() {
                Message message;
                try {
                    while (!stop && (message = (Message) reader.readObject()) != null) {
                        lastMessageProperty.set(message);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    public void stop() {
        stop = true;
    }

    public ObjectProperty<Message> lastMessageProperty() {
        return lastMessageProperty;
    }
}
