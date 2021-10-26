package com.aszabelsk.commons;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Message implements Serializable {
    private final String username;
    private final String message;
    private final ZonedDateTime zonedDateTime;

    public Message(String username, String message, ZonedDateTime zonedDateTime) {
        this.username = username;
        this.message = message;
        this.zonedDateTime = zonedDateTime;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }
}
