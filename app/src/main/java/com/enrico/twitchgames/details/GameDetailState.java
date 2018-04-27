package com.enrico.twitchgames.details;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.Collections;
import java.util.List;

/**
 * Created by enrico.
 *
 * Model to hold the state of the game details
 */
@AutoValue
abstract class GameDetailState {

    abstract boolean loading();

    @Nullable
    abstract String mainScreenShot();

    @Nullable
    abstract String cover();

    @Nullable
    abstract String name();

    @Nullable
    abstract String releaseDate();

    @Nullable
    abstract String summary();

    @Nullable
    abstract Boolean screenshots();

    @Nullable
    abstract Boolean videos();

    @Nullable
    abstract Integer errorRes();

    static Builder builder() {
        return new AutoValue_GameDetailState.Builder();
    }

    boolean isSuccess() {
        return errorRes() == null;
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder loading(boolean loading);
        abstract Builder mainScreenShot(String screenShot);
        abstract Builder cover(String cover);
        abstract Builder name(String name);
        abstract Builder releaseDate(String releaseDate);
        abstract Builder summary(String summary);
        abstract Builder screenshots(Boolean screenshots);
        abstract Builder videos(Boolean videos);
        abstract Builder errorRes(Integer errorRes);
        abstract GameDetailState build();
    }

    public boolean hasScreenshots() {
        //noinspection ConstantConditions
        return screenshots() != null ? screenshots() : false;
    }

    public boolean hasVideos() {
        //noinspection ConstantConditions
        return videos() != null ? videos() : false;
    }
}
