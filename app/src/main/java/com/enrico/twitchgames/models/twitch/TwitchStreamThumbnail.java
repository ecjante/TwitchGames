package com.enrico.twitchgames.models.twitch;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchStreamThumbnail extends TwitchImage {

    public static JsonAdapter<TwitchStreamThumbnail> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchStreamThumbnail.MoshiJsonAdapter(moshi);
    }

    @Override
    protected String extraLarge() {
        return "720x405";
    }

    @Override
    protected String large() {
        return "640x360";
    }

    @Override
    protected String medium() {
        return "320x180";
    }

    @Override
    protected String small() {
        return "80x45";
    }
}
