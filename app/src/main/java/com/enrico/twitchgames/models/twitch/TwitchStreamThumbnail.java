package com.enrico.twitchgames.models.twitch;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchStreamThumbnail extends TwitchImage {

    private static final String EXTRA_LARGE = "720x405";
    private static final String LARGE = "640x360";
    private static final String MEDIUM = "320x180";
    private static final String SMALL = "80x45";

    public static JsonAdapter<TwitchStreamThumbnail> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchStreamThumbnail.MoshiJsonAdapter(moshi);
    }

    public String getExtraLarge() {
        return buildUrl(EXTRA_LARGE);
    }
    public String getLarge() {
        return buildUrl(LARGE);
    }
    public String getMedium() {
        return buildUrl(MEDIUM);
    }
    public String getSmall() {
        return buildUrl(SMALL);
    }
}
