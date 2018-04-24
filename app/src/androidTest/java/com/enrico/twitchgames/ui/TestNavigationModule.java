package com.enrico.twitchgames.ui;

import com.enrico.twitchgames.lifecycle.ActivityLifecycleTask;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;

/**
 * Created by enrico.
 */
@Module
public abstract class TestNavigationModule {

    @Binds
    abstract ScreenNavigator bindScreenNavigator(TestScreenNavigator screenNavigator);

    @Binds
    @IntoSet
    abstract ActivityLifecycleTask bindScreenNavigatorTask(TestScreenNavigator screenNavigator);
}
