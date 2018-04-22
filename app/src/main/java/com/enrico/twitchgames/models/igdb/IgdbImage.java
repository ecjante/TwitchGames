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

    String buildScreenshot(String size) {
        StringBuilder sb = new StringBuilder(URL_PREFIX);
        sb.append(size);
        sb.append(cloudinaryId());
        sb.append(URL_IMAGE_EXTENSION);
        return sb.toString();
    }
}
