package com.enrico.twitchgames.home;

import com.bluelinelabs.conductor.Controller;
import com.enrico.twitchgames.details.GameDetailsComponent;
import com.enrico.twitchgames.details.GameDetailsController;
import com.enrico.twitchgames.di.ControllerKey;
import com.enrico.twitchgames.topgames.TopGamesComponent;
import com.enrico.twitchgames.topgames.TopGamesController;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by enrico.
 */
@Module(subcomponents = {
        TopGamesComponent.class,
        GameDetailsComponent.class
})
public abstract class TestScreenBindingModule {

    @Binds
    @IntoMap
    @ControllerKey(TopGamesController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindTopGamesInjector(TopGamesComponent.Builder builder);

    @Binds
    @IntoMap
    @ControllerKey(GameDetailsController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindGameDetailsInjector(GameDetailsComponent.Builder builder);
}
