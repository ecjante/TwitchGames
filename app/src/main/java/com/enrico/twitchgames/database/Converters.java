package com.enrico.twitchgames.database;

import android.arch.persistence.room.TypeConverter;

import com.enrico.twitchgames.models.AdapterFactory;
import com.enrico.twitchgames.models.igdb.IgdbEsrb;
import com.enrico.twitchgames.models.igdb.IgdbGameCover;
import com.enrico.twitchgames.models.igdb.IgdbGameScreenshot;
import com.enrico.twitchgames.models.igdb.IgdbGameVideo;
import com.enrico.twitchgames.models.igdb.IgdbPegi;
import com.enrico.twitchgames.models.igdb.IgdbWebsite;
import com.enrico.twitchgames.models.jsonadapters.IgdbEsrbAdapter;
import com.enrico.twitchgames.models.jsonadapters.IgdbPegiAdapter;
import com.enrico.twitchgames.models.jsonadapters.IgdbWebsiteAdapter;
import com.enrico.twitchgames.models.jsonadapters.ZonedDateTimeAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

/**
 * Created by enrico.
 *
 * Type converters for the database
 */
public class Converters {

    @TypeConverter
    public static ZonedDateTime toZonedDateTime(String data) {
        return ZonedDateTime.parse(data);
    }

    @TypeConverter
    public static String fromZonedDateTime(ZonedDateTime data) {
        return data.toString();
    }

    @TypeConverter
    public static List<IgdbGameScreenshot> toScreenshots(String data) {
        if (data == null)
            return Collections.emptyList();

        try {
            return (List<IgdbGameScreenshot>) new Moshi.Builder()
                    .add(AdapterFactory.create())
                    .build().adapter(Types.newParameterizedType(List.class, IgdbGameScreenshot.class)).fromJson(data);
        } catch (IOException e) {
            Timber.e(e, "Error parsing screenshots from database");
            return Collections.emptyList();
        }
    }

    @TypeConverter
    public static String fromScreenshots(List<IgdbGameScreenshot> data) {
        return new Moshi.Builder()
                .add(AdapterFactory.create())
                .build().adapter(Types.newParameterizedType(List.class, IgdbGameScreenshot.class)).toJson(data);
    }

    @TypeConverter
    public static List<IgdbGameVideo> toVideos(String data) {
        if (data == null)
            return Collections.emptyList();

        try {
            return (List<IgdbGameVideo>) new Moshi.Builder()
                    .add(AdapterFactory.create())
                    .build().adapter(Types.newParameterizedType(List.class, IgdbGameVideo.class)).fromJson(data);
        } catch (IOException e) {
            Timber.e(e, "Error parsing videos from database");
            return Collections.emptyList();
        }
    }

    @TypeConverter
    public static String fromVideos(List<IgdbGameVideo> data) {
        return new Moshi.Builder()
                .add(AdapterFactory.create())
                .build().adapter(Types.newParameterizedType(List.class, IgdbGameVideo.class)).toJson(data);
    }

    @TypeConverter
    public static IgdbGameCover toCover(String data) {
        try {
            return new Moshi.Builder()
                    .add(AdapterFactory.create())
                    .build().adapter(IgdbGameCover.class).fromJson(data);
        } catch (IOException e) {
            Timber.e(e, "Error parsing cover from database");
            return null;
        }
    }

    @TypeConverter
    public static String fromCover(IgdbGameCover data) {
        return new Moshi.Builder()
                .add(AdapterFactory.create())
                .build().adapter(IgdbGameCover.class).toJson(data);
    }

    @TypeConverter
    public static IgdbEsrb toEsrb(String data) {
        try {
            return new Moshi.Builder()
                    .add(AdapterFactory.create())
                    .add(new IgdbEsrbAdapter())
                    .build().adapter(IgdbEsrb.class).fromJson(data);
        } catch (IOException e) {
            Timber.e(e, "Error parsing esrb from database");
            return null;
        }
    }

    @TypeConverter
    public static String fromEsrb(IgdbEsrb data) {
        return new Moshi.Builder()
                .add(AdapterFactory.create())
                .add(new IgdbEsrbAdapter())
                .build().adapter(IgdbEsrb.class).toJson(data);
    }

    @TypeConverter
    public static IgdbPegi toPegi(String data) {
        try {
            return new Moshi.Builder()
                    .add(AdapterFactory.create())
                    .add(new IgdbPegiAdapter())
                    .build().adapter(IgdbPegi.class).fromJson(data);
        } catch (IOException e) {
            Timber.e(e, "Error parsing pegi from database");
            return null;
        }
    }

    @TypeConverter
    public static String fromPegi(IgdbPegi data) {
        return new Moshi.Builder()
                .add(AdapterFactory.create())
                .add(new IgdbPegiAdapter())
                .build().adapter(IgdbPegi.class).toJson(data);
    }

    @TypeConverter
    public static List<IgdbWebsite> toWebsites(String data) {
        if (data == null)
            return Collections.emptyList();

        try {
            return (List<IgdbWebsite>) new Moshi.Builder()
                    .add(AdapterFactory.create())
                    .add(new IgdbWebsiteAdapter())
                    .build().adapter(Types.newParameterizedType(List.class, IgdbWebsite.class)).fromJson(data);
        } catch (IOException e) {
            Timber.e(e, "Error parsing videos from database");
            return Collections.emptyList();
        }
    }

    @TypeConverter
    public static String fromWebsites(List<IgdbWebsite> data) {
        return new Moshi.Builder()
                .add(AdapterFactory.create())
                .add(new IgdbWebsiteAdapter())
                .build().adapter(Types.newParameterizedType(List.class, IgdbWebsite.class)).toJson(data);
    }
}
