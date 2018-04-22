package com.enrico.twitchgames.data;

import dagger.Binds;
import dagger.Module;

/**
 * Created by enrico.
 */
@Module
public abstract class TestGameServiceModule {

    @Binds
    abstract TwitchService bindTwitchService(TestTwitchService twitchService);

    @Binds
    abstract IgdbService bindIgdbService(TestIgdbService igdbService);
}
