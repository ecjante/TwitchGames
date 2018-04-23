package com.enrico.twitchgames.models.jsonadapters;

import com.enrico.twitchgames.models.igdb.IgdbWebsite;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

/**
 * Created by enrico.
 */
public class IgdbWebsiteAdapter {

    @FromJson
    IgdbWebsite fromJson(IgdbWebsiteJson obj) {
        return IgdbWebsite.builder()
                .category(toCategory(obj.category))
                .url(obj.url)
                .build();
    }

    private String toCategory(int key) {
        switch (key) {
            case 1: return "Official";
            case 2: return "Wikia";
            case 3: return "Wikipedia";
            case 4: return "Facebook";
            case 5: return "Twitter";
            case 6: return "Twitch";
            case 8: return "Instagram";
            case 9: return "YouTube";
            case 10: return "iPhone";
            case 11: return "iPad";
            case 12: return "Android";
            case 13: return "Steam";
            case 14: return "Reddit";
            default: return "Other";
        }
    }

    @ToJson IgdbWebsiteJson toJson(IgdbWebsite obj) {
        IgdbWebsiteJson website = new IgdbWebsiteJson();
        website.category = fromCategory(obj.category());
        website.url = obj.url();
        return website;
    }

    private int fromCategory(String key) {
        switch (key) {
            case "Official": return 1;
            case "Wikia": return 2;
            case "Wikipedia": return 3;
            case "Facebook": return 4;
            case "Twitter": return 5;
            case "Twitch": return 6;
            case "Instagram": return 8;
            case "YouTube": return 9;
            case "iPhone": return 10;
            case "iPad": return 11;
            case "Android": return 12;
            case "Steam": return 13;
            case "Reddit": return 14;
            default: return 0;
        }
    }
}
