package com.enrico.twitchgames.networking;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.Request;

/**
 * Created by enrico on 3/14/18.
 *
 * Helper class for mocking responses
 */

public class IgdbMockResponseFactory {

    private final Context context;
    private final int startIndex;

    @Inject
    IgdbMockResponseFactory(Context context, @Named("igdb_base_url") String igdbBaseUrl) {
        this.context = context;
        startIndex = igdbBaseUrl.length();
    }

    String getMockResponse(Request request) {
        String url = request.url().toString();
        String query = getQuery(url);
        String[] endpointParts = getEndpoint(url).split("/");
        return IgdbMockResourceLoader.getResponseString(context, query, endpointParts);
    }

    private String getEndpoint(String url) {
        int queryParamStart = url.indexOf("?");
        return "igdb/" + (queryParamStart == -1 ? url.substring(startIndex) : url.substring(startIndex, queryParamStart));
    }

    private String getQuery(String url) {
        int queryParamStart = url.indexOf("?");
        if (queryParamStart == -1)
            return null;
        String[] params = url.substring(queryParamStart + 1).split("&");
        for (String param : params) {
            if (param.contains("search")) {
                String query = param.split("=")[1];
                return query.toLowerCase().replaceAll("%20", "_");
            }
        }
        return null;
    }
}
