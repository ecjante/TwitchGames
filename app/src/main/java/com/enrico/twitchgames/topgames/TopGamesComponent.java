package com.enrico.twitchgames.topgames;

import com.enrico.twitchgames.di.ScreenScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by enrico.
 */
@ScreenScope
@Subcomponent
public interface TopGamesComponent extends AndroidInjector<TopGamesController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TopGamesController> {
        
    }
}
