package com.enrico.twitchgames.models.twitch;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchGame {

    @Json(name = "_id")
    public abstract Long id();
    @Nullable public abstract TwitchGameBox box();
    public abstract String name();
    @Nullable public abstract Integer popularity();

    public static JsonAdapter<TwitchGame> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchGame.MoshiJsonAdapter(moshi);
    }
}
