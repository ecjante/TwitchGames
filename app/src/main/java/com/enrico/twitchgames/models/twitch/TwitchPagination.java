package com.enrico.twitchgames.models.twitch;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchPagination {

    public abstract String cursor();

    public static JsonAdapter<TwitchPagination> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchPagination.MoshiJsonAdapter(moshi);
    }
}
