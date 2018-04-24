package com.enrico.twitchgames.networking;

import android.content.Context;

import com.enrico.twitchgames.R;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by enrico.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    @Named("twitch_call_factory")
    static Call.Factory provideTwitchOkHttp(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(i -> {
                    Request request = i.request()
                            .newBuilder()
                            .addHeader("Client-ID", context.getString(R.string.twitch_api_key))
                            .build();
                    return i.proceed(request);
                })
                .build();
    }

    @Provides
    @Named("twitch_base_url")
    static String provideTwitchBaseUrl() {
        return "https://api.twitch.tv/kraken/";
    }

    @Provides
    @Singleton
    @Named("igdb_call_factory")
    static Call.Factory provideIGDBOkHttp(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(i -> {
                    Request request = i.request()
                            .newBuilder()
                            .addHeader("user-key", context.getString(R.string.igdb_api_key))
                            .addHeader("Accept", "application/json")
                            .build();
                    return i.proceed(request);
                })
                .build();
    }

    @Provides
    @Named("igdb_base_url")
    static String provideIGDBBaseUrl() {
        return "https://api-endpoint.igdb.com/";
    }
}
