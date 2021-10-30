package com.aszabelsk.commons;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

public class Message implements Serializable {

    private static final long serialVersionUID = 1885690545520940926L;

    private final String username;
    private final UUID userUUID;
    private final String message;
    private final ZonedDateTime zonedDateTime;

    public Message(String username, UUID userUUID, String message, ZonedDateTime zonedDateTime) {
        this.username = username;
        this.userUUID = userUUID;
        this.message = message;
        this.zonedDateTime = zonedDateTime;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }
}
