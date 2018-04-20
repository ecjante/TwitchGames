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
    static Call.Factory provideOkHttp(Context context) {
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
}
