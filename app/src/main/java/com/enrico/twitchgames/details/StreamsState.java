package com.enrico.twitchgames.details;

import android.support.annotation.Nullable;

import com.enrico.twitchgames.models.twitch.TwitchStream;
import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by enrico.
 */
@AutoValue
abstract class StreamsState {

    abstract boolean loading();

    @Nullable
    abstract List<TwitchStream> streams();

    @Nullable
    abstract Integer errorRes();

    static Builder builder() {
        return new AutoValue_StreamsState.Builder();
    }

    boolean isSuccess() {
        return errorRes() == null;
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder loading(boolean loading);
        abstract Builder streams(List<TwitchStream> streams);
        abstract Builder errorRes(Integer errorRes);
        abstract StreamsState build();
    }
}
