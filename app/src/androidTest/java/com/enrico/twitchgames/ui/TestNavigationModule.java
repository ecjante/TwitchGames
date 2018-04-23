package com.enrico.twitchgames.ui;

import dagger.Binds;
import dagger.Module;

/**
 * Created by enrico.
 */
@Module
public abstract class TestNavigationModule {

    @Binds
    abstract ScreenNavigator bindScreenNavigator(TestScreenNavigator screenNavigator);
}
