package com.enrico.twitchgames.models.twitch;

import android.support.annotation.Nullable;

import com.enrico.poweradapter.item.RecyclerItem;
import com.enrico.twitchgames.database.favorites.FavoriteTwitchGame;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchTopGame implements RecyclerItem {

    @Nullable public abstract Integer channels();
    @Nullable public abstract Integer viewers();
    public abstract TwitchGame game();

    @Override
    public String renderKey() {
        return "TwitchTopGame";
    }

    @Override
    public long getId() {
        return game().id();
    }

    public TwitchTopGame withViewers(int viewers) {
        return toBuilder().viewers(viewers).build();
    }

    public static TwitchTopGame buildFromDb(FavoriteTwitchGame game) {
        return builder()
                .game(TwitchGame.buildGame(game.getId(), game.getName(), game.getBoxTemplate()))
                .build();
    }

    public static JsonAdapter<TwitchTopGame> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchTopGame.MoshiJsonAdapter(moshi);
    }

    abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_TwitchTopGame.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder channels(Integer channels);
        public abstract Builder viewers(Integer viewers);
        public abstract Builder game(TwitchGame game);
        public abstract TwitchTopGame build();
    }
}
