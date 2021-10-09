package com.aszabelsk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    private final List<PrintWriter> outputStreams = new ArrayList<>();

    public Server() {
        try {
            this.serverSocket = new ServerSocket(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
//        while (!outputStreams.isEmpty()) {
        while (true) { //TODO stop condition
            try {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                outputStreams.add(writer);
                Thread thread = new Thread(new ClientNotification(clientSocket));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class ClientNotification implements Runnable {
        BufferedReader reader;
        Socket socket;

        public ClientNotification(Socket socket) { //TODO rename
            this.socket = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    forwardToAll(message);
                    if (message.equals("quit")) {
                        //TODO remove client
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void forwardToAll(String message) {
            for (PrintWriter writer : outputStreams) {
                writer.println(message);
                writer.flush();
            }
        }
    }
}
