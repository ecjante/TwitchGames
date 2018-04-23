package com.enrico.twitchgames.models.jsonadapters;

import com.enrico.twitchgames.models.igdb.IgdbPegi;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

/**
 * Created by enrico.
 */
public class IgdbPegiAdapter {

    @FromJson
    IgdbPegi fromJson(RatingJson obj) {
        return IgdbPegi.builder()
                .rating(toRating(obj.rating))
                .build();
    }

    private int toRating(int key) {
        switch (key) {
            case 1: return 3;
            case 2: return 7;
            case 3: return 12;
            case 4: return 16;
            case 5: return 18;
            default: return 3;
        }
    }

    @ToJson
    RatingJson toJson(IgdbPegi obj) {
        RatingJson pegi = new RatingJson();
        pegi.rating = fromRating(obj.rating());
        return pegi;
    }

    private int fromRating(int key) {
        switch (key) {
            case 3: return 1;
            case 7: return 2;
            case 12: return 3;
            case 16: return 4;
            case 18: return 5;
            default: return 1;
        }
    }
}
