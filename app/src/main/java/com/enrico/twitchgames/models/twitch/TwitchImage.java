package com.enrico.twitchgames.models.twitch;

/**
 * Created by enrico.
 */
public abstract class TwitchImage {

    private static final String TEMPLATE = "{width}x{height}";

    public abstract String template();

    protected String buildUrl(String size) {
        return template().replace(TEMPLATE, size);
    }
}
