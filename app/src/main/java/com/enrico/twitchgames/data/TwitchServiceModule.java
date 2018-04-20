package com.enrico.twitchgames.data;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by enrico.
 */
@Module
public abstract class TwitchServiceModule {

    @Provides
    @Singleton
    static TwitchService provideTwitchService(Retrofit retrofit) {
        return retrofit.create(TwitchService.class);
    }
}
