package com.enrico.twitchgames.models.jsonadapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.ToJson;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.util.Date;

import timber.log.Timber;

/**
 * Created by enrico.
 */
public class ZonedDateTimeAdapter {

    @FromJson ZonedDateTime fromJson(JsonReader reader) {
        Object value = null;
        try {
            value = reader.readJsonValue();
        } catch (IOException e) {
            Timber.e(e, "Error reading from json");
            return null;
        }
        if (value instanceof String) {
            return ZonedDateTime.parse((String) value);
        } else if (value instanceof Double) {
            Date date = new Date(((Double) value).longValue());
            return ZonedDateTime.ofInstant(Instant.ofEpochMilli(((Double) value).longValue()), ZoneId.systemDefault());
        } else {
            throw new IllegalArgumentException("Date is invalid format");
        }
//        return ZonedDateTime.parse(date);
    }

    @ToJson String toJson(ZonedDateTime date) {
        return date.toString();
    }
}
