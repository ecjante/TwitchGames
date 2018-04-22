package com.enrico.twitchgames.models.igdb;

import com.google.auto.value.AutoValue;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class IgdbPegi {

//    private static final SparseIntArray PEGI = buildPegi();

    public abstract int rating();

//    public static JsonAdapter<IgdbPegi> jsonAdapter(Moshi moshi) {
//        return new AutoValue_IgdbPegi.MoshiJsonAdapter(moshi);
//    }

    static Builder builder() {
        return new AutoValue_IgdbPegi.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setRating(int rating);
        abstract IgdbPegi build();
    }

//    public int getRating() {
//        return PEGI.get(value());
//    }

//    private static SparseIntArray buildPegi() {
//        SparseIntArray array = new SparseIntArray(5);
//        array.append(1, 3);
//        array.append(2, 7);
//        array.append(3, 12);
//        array.append(4, 16);
//        array.append(5, 18);
//        return array;
//    }
}
