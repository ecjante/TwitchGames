package com.enrico.twitchgames.models.igdb;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class IgdbGameCover extends IgdbImage {

    private static final String SMALL = "t_cover_small/";
    private static final String BIG = "t_cover_big/";

    public static JsonAdapter<IgdbGameCover> jsonAdapter(Moshi moshi) {
        return new AutoValue_IgdbGameCover.MoshiJsonAdapter(moshi);
    }

    public String small() {
        return buildScreenshot(SMALL);
    }

    public String big() {
        return buildScreenshot(BIG);
    }
}
