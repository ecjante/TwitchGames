package com.enrico.twitchgames.models.igdb;

import android.util.SparseArray;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class IgdbWebsite {

//    private static final SparseArray<String> CATEGORY = buildCategories();

    public abstract String category();
    public abstract String url();

    static Builder builder() {
        return new AutoValue_IgdbWebsite.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setCategory(String category);
        abstract Builder setUrl(String category);
        abstract IgdbWebsite build();
    }

//    public static JsonAdapter<IgdbWebsite> jsonAdapter(Moshi moshi) {
//        return new AutoValue_IgdbWebsite.MoshiJsonAdapter(moshi);
//    }

//    public String getCategory() {
//        return CATEGORY.get(category());
//    }

//    private static SparseArray<String> buildCategories() {
//        SparseArray<String> array = new SparseArray<>(13);
//        array.append(1, "Official");
//        array.append(2, "Wikia");
//        array.append(3, "Wikipedia");
//        array.append(4, "Facebook");
//        array.append(5, "Twitter");
//        array.append(6, "Twitch");
//        array.append(8, "Instagram");
//        array.append(9, "YouTube");
//        array.append(10, "iPhone");
//        array.append(11, "iPad");
//        array.append(12, "Android");
//        array.append(13, "Steam");
//        array.append(14, "Reddit");
//        return array;
//    }
}
