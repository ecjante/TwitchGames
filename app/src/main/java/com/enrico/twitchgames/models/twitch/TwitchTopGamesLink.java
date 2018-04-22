package com.enrico.twitchgames.models.twitch;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchTopGamesLink {

    public abstract int limit();
    public abstract int selfOffset();
    public abstract int nextOffset();
//    public abstract String self();
//    public abstract String next();

//    public int selfOffset1() {
//        String offset = self().split("\\?")[1]
//                .split("&")[1]
//                .split("=")[1];
//        return Integer.parseInt(offset);
//    }
//    public int nextOffset1() {
//        String offset = next().split("\\?")[1]
//                .split("&")[1]
//                .split("=")[1];
//        return Integer.parseInt(offset);
//    }

//    public static JsonAdapter<TwitchTopGamesLink> jsonAdapter(Moshi moshi) {
//        return new AutoValue_TwitchTopGamesLink.MoshiJsonAdapter(moshi);
//    }

    static Builder builder() {
        return new AutoValue_TwitchTopGamesLink.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setLimit(int limit);
        abstract Builder setSelfOffset(int offset);
        abstract Builder setNextOffset(int offset);
        abstract TwitchTopGamesLink build();
    }
}
