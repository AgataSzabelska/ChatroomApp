package com.aszabelsk;

import java.io.PrintWriter;

public class MessageSender implements Runnable {
    private final PrintWriter writer;
    private final String username;
    private final String message;

    public MessageSender(PrintWriter writer, String message, String username) {
        this.writer = writer;
        this.username = username;
        this.message = message;
    }

    @Override
    public void run() {
        writer.println(username + ": " + message);
        writer.flush();
    }
}