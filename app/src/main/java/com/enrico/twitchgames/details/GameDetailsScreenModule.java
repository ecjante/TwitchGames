package com.enrico.twitchgames.details;

import com.enrico.twitchgames.lifecycle.ScreenLifecycleTask;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;

/**
 * Created by enrico.
 */
@Module
public abstract class GameDetailsScreenModule {

    @Binds
    @IntoSet
    abstract ScreenLifecycleTask bindUiManagerTask(GameDetailsUiManager uiManager);
}
