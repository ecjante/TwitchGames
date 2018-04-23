package com.enrico.twitchgames.ui;

import com.enrico.twitchgames.di.ActivityScope;

import dagger.Binds;
import dagger.Module;

/**
 * Created by enrico.
 */
@Module
public abstract class NavigationModule {

    @Binds
    @ActivityScope
    abstract ScreenNavigator provideScreenNavigator(DefaultScreenNavigator screenNavigator);
}
