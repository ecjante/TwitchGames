package com.enrico.twitchgames.models.igdb;

import com.squareup.moshi.Json;

/**
 * Created by enrico.
 */
public abstract class IgdbImage {

    private static final String URL_PREFIX = "https://images.igdb.com/igdb/image/upload/";
    private static final String URL_IMAGE_EXTENSION = ".jpg";

    @Json(name = "cloudinary_id")
    public abstract String cloudinaryId();

    private String buildScreenshot(String size) {
        StringBuilder sb = new StringBuilder(URL_PREFIX);
        sb.append(size);
        sb.append("/");
        sb.append(cloudinaryId());
        sb.append(URL_IMAGE_EXTENSION);
        return sb.toString();
    }

    protected String small() {
        return "";
    }
    protected String medium() {
        return "";
    }
    protected String large() {
        return "";
    }
    protected String extraLarge() {
        return "";
    }

    public String getSmall() {
        return buildScreenshot(small());
    }
    public String getMedium() {
        return buildScreenshot(medium());
    }
    public String getLarge() {
        return buildScreenshot(large());
    }
    public String getExtraLarge() {
        return buildScreenshot(extraLarge());
    }
}
