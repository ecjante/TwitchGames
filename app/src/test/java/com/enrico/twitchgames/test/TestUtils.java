package com.enrico.twitchgames.test;

import com.enrico.twitchgames.models.AdapterFactory;
import com.enrico.twitchgames.models.igdb.IgdbEsrbAdapter;
import com.enrico.twitchgames.models.igdb.IgdbPegiAdapter;
import com.enrico.twitchgames.models.igdb.IgdbWebsiteAdapter;
import com.enrico.twitchgames.models.igdb.ZonedDateTimeAdapter;
import com.enrico.twitchgames.models.twitch.TwitchTopGamesLinkAdapter;
import com.squareup.moshi.Moshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * Created by enrico.
 */
public class TestUtils {

    private static TestUtils INSTANCE = new TestUtils();

    private static final Moshi TEST_MOSHI = initializeMoshi();

    private TestUtils() {}

    public static <T> T loadJson(String path, Type type) {
        try {
            String json = getFileStringPath(path);
            //noinspection unchecked
            return (T) TEST_MOSHI.adapter(type).fromJson(json);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not deserialize path: " + path + " into type: " + type);
        }
    }

    public static <T> T loadJson(String path, Class<T> clazz) {
        try {
            String json = getFileStringPath(path);
            //noinspection unchecked
            return (T) TEST_MOSHI.adapter(clazz).fromJson(json);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not deserialize path: " + path + " into class: " + clazz);
        }
    }

    private static String getFileStringPath(String path) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    INSTANCE.getClass().getClassLoader().getResourceAsStream(path)
            ));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read from resource at: " + path);
        }
    }

    private static Moshi initializeMoshi() {
        Moshi.Builder builder = new Moshi.Builder();
        builder.add(AdapterFactory.create());
        builder.add(new ZonedDateTimeAdapter());
        builder.add(new TwitchTopGamesLinkAdapter());
        builder.add(new IgdbPegiAdapter());
        builder.add(new IgdbEsrbAdapter());
        builder.add(new IgdbWebsiteAdapter());
        return builder.build();
    }
}
