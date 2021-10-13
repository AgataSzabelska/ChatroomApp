package com.aszabelsk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String username;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client() throws IOException {
        socket = new Socket("127.0.0.1", 2000);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
    }

    public void start(String username) {
        this.username = username;
        initReceiverThread();
        initSenderThread();
    }

    private void initSenderThread() {
        Thread senderThread = new Thread(new MessageSender());
        senderThread.start();
    }

    public class MessageSender implements Runnable {
        @Override
        public void run() {
            String message = null;
            Scanner scanner = new Scanner(System.in);
            while (!"quit".equals(message)) {
                message = scanner.nextLine();
                writer.println(username + ": " + message);
                writer.flush();
            }
            scanner.close();
        }
    }

    public void initReceiverThread() {
        Thread receiverThread = new Thread(new MessageReceiver());
        receiverThread.start();
    }

    public class MessageReceiver implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}