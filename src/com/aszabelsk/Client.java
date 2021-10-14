package com.aszabelsk;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;

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

    private Thread receiverThread;
    private Thread senderThread;

    private BooleanProperty running = new SimpleBooleanProperty(true);

    public Client() throws IOException {
        socket = new Socket("127.0.0.1", 2000);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
    }

    public void start(ObservableList<String> messages) {
//        initReceiverThread(messages);
        initReceiverThread();
    }

    public void sendMessage(String message) {
        senderThread = new Thread(new MessageSender(message));
        senderThread.start();
    }

    public void logIn(String username) {
        this.username = username;
    }

    public class MessageSender implements Runnable {
        private final String message;

        public MessageSender(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            writer.println(username + ": " + message);
            writer.flush();
        }
    }

    public void initReceiverThread() {
        receiverThread = new Thread(new MessageReceiver());
        receiverThread.start();
    }

    public class MessageReceiver implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                while (running.get() && (message = reader.readLine()) != null) {
                    System.out.println(message);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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