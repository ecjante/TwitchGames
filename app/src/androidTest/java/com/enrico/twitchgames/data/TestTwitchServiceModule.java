package com.enrico.twitchgames.data;

import dagger.Binds;
import dagger.Module;

/**
 * Created by enrico.
 */
@Module
public abstract class TestTwitchServiceModule {

    @Binds
    abstract TwitchService bindTwitchService(TestTwitchService twitchService);
}
