package com.aszabelsk.commons;

import java.io.Serializable;
import java.time.LocalDate;

public class Message implements Serializable {
    private String username;
    private String message;
    private LocalDate now;

    public Message(String username, String message, LocalDate now) {
        this.username = username;
        this.message = message;
        this.now = now;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getNow() {
        return now;
    }
}
