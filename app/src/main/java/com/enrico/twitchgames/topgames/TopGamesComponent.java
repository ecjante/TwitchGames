package com.enrico.twitchgames.topgames;

import com.enrico.twitchgames.base.ScreenModule;
import com.enrico.twitchgames.di.ScreenComponent;
import com.enrico.twitchgames.di.ScreenScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by enrico.
 */
@ScreenScope
@Subcomponent(modules = {
        ScreenModule.class,
        TopGamesScreenModule.class,
})
public interface TopGamesComponent extends ScreenComponent<TopGamesController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TopGamesController> {
        
    }
}
