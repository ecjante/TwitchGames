package com.enrico.twitchgames.models.igdb;

import com.google.auto.value.AutoValue;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class IgdbPegi {

    public abstract int rating();

    public static Builder builder() {
        return new AutoValue_IgdbPegi.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder rating(int rating);
        public abstract IgdbPegi build();
    }
}
