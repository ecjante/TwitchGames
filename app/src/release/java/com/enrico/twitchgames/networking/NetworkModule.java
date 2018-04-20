package com.enrico.twitchgames.networking;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by enrico.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    static Call.Factory provideOkHttp() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Named("twitch_base_url")
    static String provideTwitchBaseUrl() {
        return "https://api.twitch.tv/kraken/";
    }
}
