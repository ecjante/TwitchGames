package com.enrico.twitchgames.data.responses;

import android.support.annotation.Nullable;

import com.enrico.twitchgames.models.twitch.TwitchTopGame;
import com.enrico.twitchgames.models.twitch.TwitchPaginateLink;
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

    @Json(name = "_links") @Nullable
    public abstract TwitchPaginateLink links();
    @Json(name = "top")
    public abstract List<TwitchTopGame> games();

    public static JsonAdapter<TwitchTopGamesResponse> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchTopGamesResponse.MoshiJsonAdapter(moshi);
    }
}
