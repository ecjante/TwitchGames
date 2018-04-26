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
        return "t_cover_small/";
    }

    @Override
    protected String large() {
        return "t_cover_big/";
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
