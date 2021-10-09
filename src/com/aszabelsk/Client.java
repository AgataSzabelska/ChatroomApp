package com.aszabelsk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client() {
        try {
            socket = new Socket("127.0.0.1", 2000);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Thread receiverThread = new Thread(new MessageReceiver());
        receiverThread.start();
        String message = null;
        Scanner scanner = new Scanner(System.in);
        while (!"quit".equals(message)) {
            message = scanner.nextLine();
            writer.println(message);
            writer.flush();
        }
        scanner.close();
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