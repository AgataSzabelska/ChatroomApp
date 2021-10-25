package com.aszabelsk.server;

import com.aszabelsk.commons.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private ServerSocket serverSocket;
    private final Map<Socket, ObjectOutputStream> socketToObjectOutputStream = new HashMap<>();

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
                ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
                System.out.println("Client added: " + clientSocket.getInetAddress() + ":" + clientSocket.getLocalPort());
                socketToObjectOutputStream.put(clientSocket, writer);
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class ClientHandler implements Runnable {
        ObjectInputStream reader;
        Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                reader = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            Message message;
            try {
                while ((message = (Message) reader.readObject()) != null) {
                    forwardToAll(message);
                }
            } catch (EOFException e) {
                System.out.println("Client disconnected: " + clientSocket.getInetAddress() + ":" + clientSocket.getLocalPort());
                try {
                    reader.close();
                    socketToObjectOutputStream.remove(clientSocket).close();
                    clientSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void forwardToAll(Message message) throws IOException {
            for (ObjectOutputStream writer : socketToObjectOutputStream.values()) {
                writer.writeObject(message);
            }
        }
    }
}
