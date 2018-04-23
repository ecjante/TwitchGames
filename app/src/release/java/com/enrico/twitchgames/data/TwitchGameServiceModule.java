package com.enrico.twitchgames.data;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by enrico.
 */
@Module
public abstract class TwitchGameServiceModule {

    @Provides
    @Singleton
    static TwitchService provideTwitchService(@Named("twitch_retrofit") Retrofit retrofit) {
        return retrofit.create(TwitchService.class);
    }
}
