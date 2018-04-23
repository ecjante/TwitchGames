package com.enrico.twitchgames.data;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by enrico.
 */
@Module(includes = {
        TwitchGameServiceModule.class,
        IgdbGameServiceModule.class
})
public abstract class GameServiceModule {

    @Provides
    @Named("network_scheduler")
    static Scheduler provideNetworkScheduler() {
        return Schedulers.io();
    }
}
