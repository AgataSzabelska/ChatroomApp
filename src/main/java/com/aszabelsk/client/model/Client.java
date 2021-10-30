package com.aszabelsk.client.model;

import com.aszabelsk.commons.Message;
import javafx.beans.property.ObjectProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private String username;

    private final Socket socket;
    private final ObjectInputStream reader;
    private final ObjectOutputStream writer;

    private final MessageReceiverService receiverService;

    public Client() throws IOException {
        socket = new Socket("127.0.0.1", 2000);
        reader = new ObjectInputStream(socket.getInputStream());
        writer = new ObjectOutputStream(socket.getOutputStream());
        receiverService = new MessageReceiverService(reader);
    }

    public void start() {
        receiverService.start();
    }

    public void sendMessage(String message) {
        (new Thread(new MessageSender(writer, message, username))).start();
    }

    public void logIn(String username) {
        this.username = username;
    }

    public ObjectProperty<Message> lastMessageProperty() {
        return receiverService.lastMessageProperty();
    }

    public void disconnect() {
        receiverService.setOnSucceeded(event -> {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        receiverService.stop();
    }
}