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

    @Override
    protected String small() {
        return medium();
    }

    @Override
    protected String medium() {
        return "t_screenshot_med";
    }

    @Override
    protected String large() {
        return "t_screenshot_big";
    }

    @Override
    protected String extraLarge() {
        return "t_screenshot_huge";
    }

    public static Builder builder() {
        return new AutoValue_IgdbGameScreenshot.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder cloudinaryId(String cloudinaryId);
        public abstract IgdbGameScreenshot build();
    }
}
