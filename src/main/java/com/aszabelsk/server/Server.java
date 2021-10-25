package com.aszabelsk.server;

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
            serverSocket = new ServerSocket(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Server started at " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                System.out.println("Client added: " + clientSocket.getInetAddress() + ":" + clientSocket.getLocalPort());
                outputStreams.add(writer);
                Thread thread = new Thread(new MessageForwarder(clientSocket));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class MessageForwarder implements Runnable {
        BufferedReader reader;
        Socket socket;

        public MessageForwarder(Socket socket) {
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
                    if (message.equals("quit")) { //TODO change condition
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
