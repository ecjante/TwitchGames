package com.enrico.twitchgames.models.twitch;

/**
 * Created by enrico.
 */
public abstract class TwitchImage {

    private static final String TEMPLATE = "{width}x{height}";

    public abstract String template();

    private String buildUrl(String size) {
        return size.isEmpty() ? null : template().replace(TEMPLATE, size);
    }

    protected String extraLarge() {
        return "";
    }
    protected String large() {
        return "";
    }
    protected String medium() {
        return "";
    }
    protected String small() {
        return "";
    }

    public String getExtraLarge() {
        return buildUrl(extraLarge());
    }
    public String getLarge() {
        return buildUrl(large());
    }
    public String getMedium() {
        return buildUrl(medium());
    }
    public String getSmall() {
        return buildUrl(small());
    }
}
