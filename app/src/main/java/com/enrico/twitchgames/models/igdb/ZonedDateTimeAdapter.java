package com.enrico.twitchgames.models.igdb;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by enrico.
 */
public class ZonedDateTimeAdapter {

    @FromJson ZonedDateTime fromJson(long epoch) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.systemDefault());
    }

    @ToJson long toJson(ZonedDateTime date) {
        return date.toEpochSecond();
    }
}
