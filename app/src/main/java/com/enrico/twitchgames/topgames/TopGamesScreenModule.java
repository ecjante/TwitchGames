package com.enrico.twitchgames.topgames;

import com.enrico.twitchgames.lifecycle.ScreenLifecycleTask;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;

/**
 * Created by enrico.
 */
@Module
public abstract class TopGamesScreenModule {

    @Binds
    @IntoSet
    abstract ScreenLifecycleTask bindUiManagerTask(TopGamesUiManager uiManager);
}
