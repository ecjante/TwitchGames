package com.enrico.twitchgames.data;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by enrico.
 */
@Module
public abstract class GameServiceModule {

    @Provides
    @Singleton
    static TwitchService provideTwitchService(@Named("twitch_retrofit") Retrofit retrofit) {
        return retrofit.create(TwitchService.class);
    }

    @Provides
    @Singleton
    static IgdbService provideIGDBService(@Named("igdb_retrofit") Retrofit retrofit) {
        return retrofit.create(IgdbService.class);
    }

    @Provides
    @Named("network_scheduler")
    static Scheduler provideNetworkScheduler() {
        return Schedulers.io();
    }
}
