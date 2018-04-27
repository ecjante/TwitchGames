package com.enrico.twitchgames.models.igdb;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class IgdbGameCover extends IgdbImage {

    @Override
    protected String small() {
        return "t_cover_small";
    }

    @Override
    protected String medium() {
        return small() + "_2x";
    }

    @Override
    protected String large() {
        return "t_cover_big";
    }

    @Override
    protected String extraLarge() {
        return large() + "_2x";
    }

    public static JsonAdapter<IgdbGameCover> jsonAdapter(Moshi moshi) {
        return new AutoValue_IgdbGameCover.MoshiJsonAdapter(moshi);
    }

    public static Builder builder() {
        return new AutoValue_IgdbGameCover.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder cloudinaryId(String cloudinaryId);
        public abstract IgdbGameCover build();
    }
}
