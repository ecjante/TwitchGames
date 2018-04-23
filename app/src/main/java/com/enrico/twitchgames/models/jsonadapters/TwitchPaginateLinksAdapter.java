package com.enrico.twitchgames.models.jsonadapters;

import com.enrico.twitchgames.models.twitch.TwitchPaginateLink;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

/**
 * Created by enrico.
 */
public class TwitchPaginateLinksAdapter {

    @FromJson
    TwitchPaginateLink fromJson(TwitchPaginateLinksJson obj) {
        String[] selfParams = obj.self.split("\\?")[1].split("&");
        String[] nextParams = obj.next.split("\\?")[1].split("&");
        Integer limit = getLimit(selfParams);
        Integer selfOffset = getOffset(selfParams);
        Integer nextOffset = getOffset(nextParams);
        return TwitchPaginateLink.builder()
                .limit(limit)
                .selfOffset(selfOffset)
                .nextOffset(nextOffset)
                .build();
    }

    private Integer getLimit(String[] params) {
        for (String param : params) {
            if (param.contains("limit=")) {
                return Integer.parseInt(param.split("=")[1]);
            }
        }
        return null;
    }

    private Integer getOffset(String[] params) {
        for (String param : params) {
            if (param.contains("offset=")) {
                return Integer.parseInt(param.split("=")[1]);
            }
        }
        return null;
    }

    @ToJson
    TwitchPaginateLinksJson toJson(TwitchPaginateLink obj) {
        return new TwitchPaginateLinksJson();
    }
}
