package com.enrico.twitchgames.models.igdb;

import com.google.auto.value.AutoValue;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class IgdbWebsite {

    public abstract String category();
    public abstract String url();

    public static Builder builder() {
        return new AutoValue_IgdbWebsite.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder category(String category);
        public abstract Builder url(String category);
        public abstract IgdbWebsite build();
    }
}
