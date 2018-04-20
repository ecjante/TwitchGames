package com.enrico.twitchgames.ui;

import dagger.Binds;
import dagger.Module;

/**
 * Created by enrico.
 */
@Module
public abstract class NavigationModule {

    @Binds
    abstract ScreenNavigator provideScreenNavigator(DefaultScreenNavigator screenNavigator);
}
