package com.enrico.twitchgames.models.igdb;

import com.enrico.poweradapter.item.RecyclerItem;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class IgdbGameVideo implements RecyclerItem {

    private static final String URL_PREFIX = "https://www.youtube.com/watch?v=";

    public abstract String name();
    @Json(name = "video_id")
    public abstract String videoId();

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String renderKey() {
        return "IgdbGameVideo";
    }

    public static JsonAdapter<IgdbGameVideo> jsonAdapter(Moshi moshi) {
        return new AutoValue_IgdbGameVideo.MoshiJsonAdapter(moshi);
    }

    public static Builder builder() {
        return new AutoValue_IgdbGameVideo.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);
        public abstract Builder videoId(String videoId);
        public abstract IgdbGameVideo build();
    }
}
