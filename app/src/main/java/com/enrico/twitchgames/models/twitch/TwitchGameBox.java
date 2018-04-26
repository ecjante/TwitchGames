package com.enrico.twitchgames.models.twitch;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchGameBox extends TwitchImage {

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

    @Override
    protected String extraLarge() {
        return "520x720";
    }

    @Override
    protected String large() {
        return "272x380";
    }

    @Override
    protected String medium() {
        return "136x190";
    }

    @Override
    protected String small() {
        return "52x72";
    }
}
