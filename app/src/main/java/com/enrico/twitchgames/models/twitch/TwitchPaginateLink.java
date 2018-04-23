package com.enrico.twitchgames.models.twitch;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchPaginateLink {

    @Nullable public abstract Integer limit();
    @Nullable public abstract Integer selfOffset();
    @Nullable public abstract Integer nextOffset();

    public static Builder builder() {
        return new AutoValue_TwitchPaginateLink.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder limit(Integer limit);
        public abstract Builder selfOffset(Integer offset);
        public abstract Builder nextOffset(Integer offset);
        public abstract TwitchPaginateLink build();
    }
}
