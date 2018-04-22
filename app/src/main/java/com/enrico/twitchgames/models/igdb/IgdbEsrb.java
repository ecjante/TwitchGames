package com.enrico.twitchgames.models.igdb;

import com.google.auto.value.AutoValue;

/**
 * Created by enrico.
 */
@AutoValue
public abstract class IgdbEsrb {

//    private static final SparseArray<String> ESRB = buildEsrb();

    public abstract String rating();

//    public static JsonAdapter<IgdbEsrb> jsonAdapter(Moshi moshi) {
//        return new AutoValue_IgdbEsrb.MoshiJsonAdapter(moshi);
//    }

    static Builder builder() {
        return new AutoValue_IgdbEsrb.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setRating(String rating);
        abstract IgdbEsrb build();
    }

//    public String rating() {
//        return ESRB.get(rating());
//    }

//    private static SparseArray<String> buildEsrb() {
//        SparseArray<String> array = new SparseArray<>(7);
//        array.append(1, "RP");
//        array.append(2, "EC");
//        array.append(3, "E");
//        array.append(4, "E10+");
//        array.append(5, "T");
//        array.append(6, "M");
//        array.append(7, "AO");
//        return array;
//    }

}
