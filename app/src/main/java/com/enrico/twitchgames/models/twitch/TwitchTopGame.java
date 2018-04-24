package com.enrico.twitchgames.models.twitch;

import com.enrico.poweradapter.item.RecyclerItem;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class TwitchTopGame implements RecyclerItem {

    public abstract int channels();
    public abstract int viewers();
    public abstract TwitchGame game();

    @Override
    public String renderKey() {
        return "TwitchTopGame";
    }

    @Override
    public long getId() {
        return game().id();
    }

    public static JsonAdapter<TwitchTopGame> jsonAdapter(Moshi moshi) {
        return new AutoValue_TwitchTopGame.MoshiJsonAdapter(moshi);
    }

    public String getViewerCount() {
        StringBuilder sb = new StringBuilder();
        sb.append(NumberFormat.getNumberInstance(Locale.getDefault()).format(viewers()));
        sb.append(" Viewer");
        if (viewers() != 1)
            sb.append("s");
        return sb.toString();
    }
}
