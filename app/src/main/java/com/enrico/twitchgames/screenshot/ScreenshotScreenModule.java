package com.enrico.twitchgames.screenshot;

import com.enrico.twitchgames.lifecycle.ScreenLifecycleTask;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;

/**
 * Created by enrico.
 *
 * Provides the ScreenshotUiManager
 */
@Module
public abstract class ScreenshotScreenModule {

    @Binds
    @IntoSet
    abstract ScreenLifecycleTask bindUiManagerTask(ScreenshotUiManager uiManager);
}
