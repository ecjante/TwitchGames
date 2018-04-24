package com.enrico.twitchgames.networking;

import android.content.Context;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import timber.log.Timber;

/**
 * Created by enrico on 3/14/18.
 */

class IgdbMockResourceLoader {

    private IgdbMockResourceLoader() {

    }

    @Nullable
    static String getResponseString(Context context, @Nullable String query, String[] endpointParts) {
        try {
            String currentPath = "mock";
            Set<String> mockList = new HashSet<>(Arrays.asList(context.getAssets().list(currentPath)));
            for (String endpointPart : endpointParts) {
                if (mockList.contains(endpointPart)) {
                    currentPath = currentPath + "/" + endpointPart;
                    mockList = new HashSet<>(Arrays.asList(context.getAssets().list(currentPath)));
                }
            }

            // At this stage, our mock list will be the list of files in the matching director for
            // the endpoint parts
            String finalPath = null;
            if (query != null) {
                for (String path : mockList) {
                    if (path.contains(query)) {
                        finalPath = currentPath + "/" + path;
                        break;
                    }
                }

                if (finalPath != null) {
                    return responseFromPath(context, finalPath);
                } else {
                    finalPath = currentPath + "/" + mockList.iterator().next();
                    return responseFromPath(context, finalPath);
                }
            }
            return null;
        } catch (IOException e) {
            Timber.e(e, "Error loading mock response from assets");
            return null;
        }
    }

    private static String responseFromPath(Context context, String path) {
        StringBuilder sb = new StringBuilder();
        try (InputStream assetStream = context.getAssets().open(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(assetStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            Timber.e(e, "Error reading mock response");
        }
        return sb.toString();
    }
}
