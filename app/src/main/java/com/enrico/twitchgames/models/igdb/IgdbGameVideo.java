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

    public static JsonAdapter<IgdbGameVideo> jsonAdapter(Moshi moshi) {
        return new AutoValue_IgdbGameVideo.MoshiJsonAdapter(moshi);
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String renderKey() {
        return "IgdbGameVideo";
    }

    public String getUrl() {
        StringBuilder sb = new StringBuilder(URL_PREFIX);
        sb.append(videoId());
        return sb.toString();
    }
}
