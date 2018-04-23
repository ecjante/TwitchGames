package com.enrico.twitchgames.models.igdb;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.threeten.bp.ZonedDateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class IgdbGame {

    public abstract Long id();
    public abstract String name();
    public abstract String summary();
    @Json(name = "first_release_date")
    public abstract ZonedDateTime firstReleaseDate();
    public abstract List<Integer> platforms();
    public abstract List<IgdbGameScreenshot> screenshots();
    public abstract List<IgdbGameVideo> videos();
    @Nullable public abstract IgdbGameCover cover();
    @Nullable public abstract IgdbEsrb esrb();
    @Nullable public abstract IgdbPegi pegi();
    @Nullable public abstract List<IgdbWebsite> websites();

    public static JsonAdapter<IgdbGame> jsonAdapter(Moshi moshi) {
        return new AutoValue_IgdbGame.MoshiJsonAdapter(moshi);
    }
}
