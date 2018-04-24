package com.enrico.twitchgames.models.twitch;

import com.enrico.poweradapter.item.RecyclerItem;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.threeten.bp.ZonedDateTime;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchStream implements RecyclerItem {

    private static final String TEMPLATE = "{width}x{height}";
    private static final String EXTRA_LARGE = "720x405";
    private static final String LARGE = "640x360";
    private static final String MEDIUM = "320x180";
    private static final String SMALL = "80x45";

    @Json(name = "_id") public abstract Long id();
    public abstract String game();
    public abstract int viewers();
    @Json(name = "created_at") public abstract ZonedDateTime createdAt();
    public abstract TwitchStreamThumbnail preview();
    public abstract TwitchChannel channel();

    @Override
    public long getId() {
        return id();
    }

    @Override
    public String renderKey() {
        return "TwitchStream";
    }

    public static JsonAdapter<TwitchStream> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchStream.MoshiJsonAdapter(moshi);
    }
}
