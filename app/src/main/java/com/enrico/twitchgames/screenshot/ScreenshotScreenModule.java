package com.enrico.twitchgames.screenshot;

import com.enrico.twitchgames.details.GameDetailsUiManager;
import com.enrico.twitchgames.lifecycle.ScreenLifecycleTask;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;

/**
 * Created by enrico.
 */
@Module
public abstract class ScreenshotScreenModule {

    @Binds
    @IntoSet
    abstract ScreenLifecycleTask bindUiManagerTask(ScreenshotUiManager uiManager);
}
