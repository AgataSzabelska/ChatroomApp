package com.aszabelsk.client.view.chat;

public enum Emoji {
    SLIGHT_SMILE("🙂"),
    WINK("😉"),
    GRIN("😀"),
    TEETH_GRIN("😁"),
    HEART_EYES("😍"),
    HEART_KISS("😘"),
    TONGUE("😛"),
    TONGUE_WINK("😜"),
    SMIRK("😏"),
    LAUGH_CRY("😂"),
    LAUGH_CRY_ROLLING("🤣"),
    UPSIDE_DOWN("🙃"),
    EXPRESSIONLESS("😑"),
    WORRIED("😕"),
    SAD("🙁"),
    OPEN_MOUTH("😮"),
    CRYING("😢"),
    CRYING_LOUDLY("😭"),
    FEARFUL("😨"),
    FEARFUL_SCREAM("😱");

    private final String value;

    Emoji(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
