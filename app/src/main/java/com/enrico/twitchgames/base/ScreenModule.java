package com.enrico.twitchgames.base;

import com.enrico.twitchgames.di.ForScreen;
import com.enrico.twitchgames.di.ScreenScope;
import com.enrico.twitchgames.lifecycle.DisposableManager;
import com.enrico.twitchgames.lifecycle.ScreenLifecycleTask;

import java.util.Set;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.Multibinds;

/**
 * Created by enrico.
 */
@Module
public abstract class ScreenModule {

    @Provides
    @ScreenScope
    @ForScreen
    static DisposableManager provideDisposableManager() {
        return new DisposableManager();
    }

    @Multibinds
    abstract Set<ScreenLifecycleTask> screenLifecycleTasks();
}
