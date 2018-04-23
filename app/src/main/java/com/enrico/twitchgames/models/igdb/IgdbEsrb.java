package com.enrico.twitchgames.models.igdb;

import com.google.auto.value.AutoValue;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class IgdbEsrb {

    public abstract String rating();

    public static Builder builder() {
        return new AutoValue_IgdbEsrb.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder rating(String rating);
        public abstract IgdbEsrb build();
    }
}
