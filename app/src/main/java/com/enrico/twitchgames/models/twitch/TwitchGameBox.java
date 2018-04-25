package com.enrico.twitchgames.models.twitch;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchGameBox extends TwitchImage {

    private static final String EXTRA_LARGE = "520x720";
    private static final String LARGE = "272x380";
    private static final String MEDIUM = "136x190";
    private static final String SMALL = "52x72";

    public static JsonAdapter<TwitchGameBox> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchGameBox.MoshiJsonAdapter(moshi);
    }

    public static Builder builder() {
        return new AutoValue_TwitchGameBox.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder template(String Template);
        public abstract TwitchGameBox build();
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
