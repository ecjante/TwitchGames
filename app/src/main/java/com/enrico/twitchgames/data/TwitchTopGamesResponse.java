package com.enrico.twitchgames.data;

import com.enrico.twitchgames.models.twitch.TwitchTopGame;
import com.enrico.twitchgames.models.twitch.TwitchTopGamesLink;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchTopGamesResponse {

    @Json(name = "_links")
    public abstract TwitchTopGamesLink links();
    @Json(name = "top")
    public abstract List<TwitchTopGame> games();

    public static JsonAdapter<TwitchTopGamesResponse> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchTopGamesResponse.MoshiJsonAdapter(moshi);
    }
}
