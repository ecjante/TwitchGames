package com.enrico.twitchgames.data;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by enrico.
 */
@Module
public abstract class TestGameServiceModule {

    @Binds
    abstract TwitchService bindTwitchService(TestTwitchService twitchService);

    @Binds
    abstract IgdbService bindIgdbService(TestIgdbService igdbService);

    @Provides
    @Named("network_scheduler")
    static Scheduler provideNetworkScheduler() {
        return Schedulers.trampoline();
    }
}
