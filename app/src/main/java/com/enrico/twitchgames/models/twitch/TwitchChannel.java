package com.enrico.twitchgames.models.twitch;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchChannel {

    @Json(name = "_id") public abstract long id();
    @Json(name = "display_name") public abstract String displayName();
    public abstract String status();
    public abstract String logo();
    public abstract int followers();

    public static JsonAdapter<TwitchChannel> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchChannel.MoshiJsonAdapter(moshi);
    }
}
