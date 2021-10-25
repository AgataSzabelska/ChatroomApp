package com.aszabelsk.client;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private String username;

    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    private MessageReceiverService receiverService;
    private Thread senderThread;

    private BooleanProperty running = new SimpleBooleanProperty(true);

    public Client() throws IOException {
        socket = new Socket("127.0.0.1", 2000);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
        receiverService = new MessageReceiverService(reader);
    }

    public void start() {
        receiverService.start();
    }

    public void sendMessage(String message) {
        senderThread = new Thread(new MessageSender(writer, message, username));
        senderThread.start();
    }

    public void logIn(String username) {
        this.username = username;
    }

    public StringProperty lastMessageProperty() {
        return receiverService.lastMessageProperty();
    }

    public void disconnect() {
        running.set(false);
        // TODO fix threads
//        try {
//            receiverThread.join();
//            senderThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try {
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}