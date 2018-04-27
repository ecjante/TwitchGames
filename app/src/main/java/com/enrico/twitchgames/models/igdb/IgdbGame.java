package com.enrico.twitchgames.models.igdb;

import android.support.annotation.Nullable;

import com.enrico.twitchgames.database.igdb.DbIgdbGame;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by enrico.
 *
 * Model to hold game info from IGDB
 */
@AutoValue
public abstract class IgdbGame {

    @Nullable public abstract Long id();

    @Nullable public abstract Long twitchId();

    public abstract String name();

    @Nullable public abstract String summary();

    @Json(name = "first_release_date")
    @Nullable public abstract ZonedDateTime firstReleaseDate();

    @Nullable public abstract List<IgdbGameScreenshot> screenshots();

    @Nullable public abstract List<IgdbGameVideo> videos();

    @Nullable public abstract IgdbGameCover cover();

    @Nullable public abstract IgdbEsrb esrb();

    @Nullable public abstract IgdbPegi pegi();

    @Nullable public abstract List<IgdbWebsite> websites();

    public List<IgdbGameScreenshot> getScreenshots() {
        return screenshots() != null ? screenshots() : Collections.emptyList();
    }

    public List<IgdbGameVideo> getVideos() {
        return videos() != null ? videos() : Collections.emptyList();
    }

    public List<IgdbWebsite> getWebsites() {
        return websites() != null ? websites() : Collections.emptyList();
    }

    public static IgdbGame fromDb(DbIgdbGame game) {
        return builder()
                .id(game.getIgdbId())
                .twitchId(game.getId())
                .name(game.getName())
                .summary(game.getSummary())
                .firstReleaseDate(game.getFirstReleaseDate())
                .screenshots(game.getScreenshots())
                .videos(game.getVideos())
                .cover(game.getCover())
                .esrb(game.getEsrb())
                .pegi(game.getPegi())
                .websites(game.getWebsites())
                .build();
    }

    public static JsonAdapter<IgdbGame> jsonAdapter(Moshi moshi) {
        return new AutoValue_IgdbGame.MoshiJsonAdapter(moshi);
    }

    protected abstract Builder toBuilder();

    public IgdbGame withTwitchId(Long id) {
        return toBuilder().twitchId(id).build();
    }

    public static Builder builder() {
        return new AutoValue_IgdbGame.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(Long id);
        public abstract Builder twitchId(Long twitchId);
        public abstract Builder name(String name);
        public abstract Builder summary(String summary);
        public abstract Builder firstReleaseDate(ZonedDateTime firstReleaseDate);
        public abstract Builder screenshots(List<IgdbGameScreenshot> screenshots);
        public abstract Builder videos(List<IgdbGameVideo> videos);
        public abstract Builder cover(IgdbGameCover cover);
        public abstract Builder esrb(IgdbEsrb esrb);
        public abstract Builder pegi(IgdbPegi pegi);
        public abstract Builder websites(List<IgdbWebsite> websites);
        public abstract IgdbGame build();
    }
}
