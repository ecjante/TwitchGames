package com.enrico.twitchgames.models.twitch;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchGameBox {

    private static final String TEMPLATE = "{width}x{height}";
    private static final String EXTRA_LARGE = "520x720";
    private static final String LARGE = "272x380";
    private static final String MEDIUM = "136x190";
    private static final String SMALL = "52x72";

    public abstract String template();

    public static JsonAdapter<TwitchGameBox> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchGameBox.MoshiJsonAdapter(moshi);
    }

    public String getExtraLarge() {
        return template().replace(TEMPLATE, EXTRA_LARGE);
    }
    public String getLarge() {
        return template().replace(TEMPLATE, LARGE);
    }
    public String getMedium() {
        String medium = template().replace(TEMPLATE, MEDIUM);
        return medium;
    }
    public String getSmall() {
        String small = template().replace(TEMPLATE, SMALL);
        return small;
    }
}
