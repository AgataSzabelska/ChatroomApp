package com.aszabelsk.server;

import com.aszabelsk.commons.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    private final List<ObjectOutputStream> outputStreams = new ArrayList<>();

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
                outputStreams.add(writer);
                Thread thread = new Thread(new MessageForwarder(clientSocket));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class MessageForwarder implements Runnable {
        ObjectInputStream reader;
        Socket socket;

        public MessageForwarder(Socket socket) {
            this.socket = socket;
            try {
                reader = new ObjectInputStream(socket.getInputStream());
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
//                    if (message.equals("quit")) { //TODO change condition
//                        //TODO remove client
//                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void forwardToAll(Message message) throws IOException {
            for (ObjectOutputStream writer : outputStreams) {
                writer.writeObject(message);
//                writer.flush();
            }
        }
    }
}
