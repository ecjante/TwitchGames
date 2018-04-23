package com.enrico.twitchgames.models.jsonadapters;

import com.enrico.twitchgames.models.igdb.IgdbEsrb;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

/**
 * Created by enrico.
 */
public class IgdbEsrbAdapter {

    @FromJson
    IgdbEsrb fromJson(RatingJson obj) {
        return IgdbEsrb.builder()
                .rating(toRating(obj.rating))
                .build();
    }

    private String toRating(int key) {
        switch (key) {
            case 1: return "RP";
            case 2: return "EC";
            case 3: return "E";
            case 4: return "E10+";
            case 5: return "T";
            case 6: return "M";
            case 7: return "AO";
            default: return "RP";
        }
    }

    @ToJson
    RatingJson toJson(IgdbEsrb obj) {
        RatingJson igdb = new RatingJson();
        igdb.rating = fromRating(obj.rating());
        return igdb;
    }

    private int fromRating(String key) {
        switch (key) {
            case "RP"  : return 1;
            case "EC"  : return 2;
            case "E"   : return 3;
            case "E10+": return 4;
            case "T"   : return 5;
            case "M"   : return 6;
            case "AO"  : return 7;
            default: return 1;
        }
    }
}
