package com.aszabelsk.client.model;

import com.aszabelsk.commons.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.ZonedDateTime;
import java.util.UUID;

public class MessageSender implements Runnable {
    private final ObjectOutputStream writer;
    private final String username;
    private final UUID userUUID;
    private final String message;

    public MessageSender(ObjectOutputStream writer, String message, String username, UUID userUUID) {
        this.writer = writer;
        this.username = username;
        this.userUUID = userUUID;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            writer.writeObject(new Message(username, userUUID, message, ZonedDateTime.now()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}