package com.enrico.twitchgames.models.twitch;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchTopGamesLink {

    public abstract String self();
    public abstract String next();

    public int selfOffset() {
        String offset = self().split("\\?")[1]
                .split("&")[1]
                .split("=")[1];
        return Integer.parseInt(offset);
    }
    public int nextOffset() {
        String offset = next().split("\\?")[1]
                .split("&")[1]
                .split("=")[1];
        return Integer.parseInt(offset);
    }

    public static JsonAdapter<TwitchTopGamesLink> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchTopGamesLink.MoshiJsonAdapter(moshi);
    }
}
