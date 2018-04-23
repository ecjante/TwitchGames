package com.enrico.twitchgames.data.responses;

import com.enrico.twitchgames.models.twitch.TwitchPaginateLink;
import com.enrico.twitchgames.models.twitch.TwitchStream;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchStreamsResponse {

    public abstract List<TwitchStream> streams();
    @Json(name = "_links") public abstract TwitchPaginateLink links();

    public static JsonAdapter<TwitchStreamsResponse> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchStreamsResponse.MoshiJsonAdapter(moshi);
    }
}
