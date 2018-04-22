package com.enrico.twitchgames.models.twitch;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

/**
 * Created by enrico.
 */
public class TwitchTopGamesLinkAdapter {

    @FromJson TwitchTopGamesLink fromJson(TwitchTopGamesLinkJson obj) {
        String[] selfParams = obj.self.split("\\?")[1].split("&");
        String[] nextParams = obj.next.split("\\?")[1].split("&");
        int limit = Integer.parseInt(selfParams[0].split("=")[1]);
        int selfOffset = selfParams.length == 2 ? Integer.parseInt(selfParams[1].split("=")[1]) : 0;
        int nextOffset = nextParams.length == 2 ? Integer.parseInt(nextParams[1].split("=")[1]) : 0;
        return TwitchTopGamesLink.builder()
                .setLimit(limit)
                .setSelfOffset(selfOffset)
                .setNextOffset(nextOffset)
                .build();
    }

    @ToJson TwitchTopGamesLinkJson toJson(TwitchTopGamesLink obj) {
        return new TwitchTopGamesLinkJson();
    }
}
