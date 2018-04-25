package com.enrico.twitchgames.details;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.Collections;
import java.util.List;

/**
 * Created by enrico.
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
    public abstract List<String> screenshots();

    @Nullable
    public abstract List<String> videos();

    @Nullable
    abstract Integer errorRes();

    static Builder builder() {
        return new AutoValue_GameDetailState.Builder()
                .screenshots(Collections.emptyList())
                .videos(Collections.emptyList());
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
        abstract Builder screenshots(List<String> screenshots);
        abstract Builder videos(List<String> videos);
        abstract Builder errorRes(Integer errorRes);
        abstract GameDetailState build();
    }
}
