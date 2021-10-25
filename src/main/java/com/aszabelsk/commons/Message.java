package com.aszabelsk.commons;

import java.io.Serializable;
import java.time.LocalDate;

public class Message implements Serializable {
    private final String username;
    private final String message;
    private final LocalDate localDate;

    public Message(String username, String message, LocalDate localDate) {
        this.username = username;
        this.message = message;
        this.localDate = localDate;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
