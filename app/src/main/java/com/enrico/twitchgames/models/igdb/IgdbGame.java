package com.enrico.twitchgames.models.igdb;

import android.support.annotation.Nullable;

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
 */
@AutoValue
public abstract class IgdbGame {

    public abstract Long id();

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

    public static JsonAdapter<IgdbGame> jsonAdapter(Moshi moshi) {
        return new AutoValue_IgdbGame.MoshiJsonAdapter(moshi);
    }

    public List<IgdbGameScreenshot> getScreenshots() {
        return screenshots() != null ? screenshots() : Collections.emptyList();
    }

    public List<IgdbGameVideo> getVideos() {
        return videos() != null ? videos() : Collections.emptyList();
    }

    public List<IgdbWebsite> getWebsites() {
        return websites() != null ? websites() : Collections.emptyList();
    }

    public List<String> getMediumScreenshotUrls() {
        List<String> stringScreenshots = new ArrayList<>();
        for (IgdbGameScreenshot screenshot : getScreenshots()) {
            stringScreenshots.add(screenshot.medium());
        }
        return stringScreenshots;
    }

    public List<String> getBigScreenshotUrls() {
        List<String> stringScreenshots = new ArrayList<>();
        for (IgdbGameScreenshot screenshot : getScreenshots()) {
            stringScreenshots.add(screenshot.big());
        }
        return stringScreenshots;
    }

    public List<String> getVideoIds() {
        List<String> stringVideos = new ArrayList<>();
        for (IgdbGameVideo video : getVideos()) {
            stringVideos.add(video.videoId());
        }
        return stringVideos;
    }
}
