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

    public static Builder builder() {
        return new AutoValue_TwitchGame.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(Long id);
        public abstract Builder box(TwitchGameBox box);
        public abstract Builder name(String name);
        public abstract Builder popularity(Integer popularity);
        public abstract TwitchGame build();
    }

    public static TwitchGame buildGame(Long id, String name, String boxTemplate) {
        return TwitchGame.builder()
                .id(id)
                .name(name)
                .box(TwitchGameBox.builder().template(boxTemplate).build())
                .build();
    }
}
