package com.enrico.twitchgames.models.igdb;

import com.enrico.poweradapter.item.RecyclerItem;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class IgdbGameScreenshot extends IgdbImage implements RecyclerItem {

    private static final String MEDIUM = "t_screenshot_med/";
    private static final String BIG = "t_screenshot_big/";

    public static JsonAdapter<IgdbGameScreenshot> jsonAdapter(Moshi moshi) {
        return new AutoValue_IgdbGameScreenshot.MoshiJsonAdapter(moshi);
    }

    @Override
    public String renderKey() {
        return "IgdbGameScreenshot";
    }

    @Override
    public long getId() {
        return 0;
    }

    public String medium() {
        return buildScreenshot(MEDIUM);
    }

    public String big() {
        return buildScreenshot(BIG);
    }
}
