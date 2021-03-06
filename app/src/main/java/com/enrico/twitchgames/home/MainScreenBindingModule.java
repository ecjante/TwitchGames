package com.enrico.twitchgames.home;

import com.bluelinelabs.conductor.Controller;
import com.enrico.twitchgames.details.GameDetailsComponent;
import com.enrico.twitchgames.details.GameDetailsController;
import com.enrico.twitchgames.di.ControllerKey;
import com.enrico.twitchgames.screenshot.ScreenshotComponent;
import com.enrico.twitchgames.screenshot.ScreenshotController;
import com.enrico.twitchgames.topgames.TopGamesComponent;
import com.enrico.twitchgames.topgames.TopGamesController;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector_Factory;
import dagger.multibindings.IntoMap;

/**
 * Created by enrico.
 *
 * Holds screen components and provides controller injectors
 */
@Module(subcomponents = {
        TopGamesComponent.class,
        GameDetailsComponent.class,
        ScreenshotComponent.class,
})
public abstract class MainScreenBindingModule {

    @Binds
    @IntoMap
    @ControllerKey(TopGamesController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindTopGamesInjector(TopGamesComponent.Builder builder);

    @Binds
    @IntoMap
    @ControllerKey(GameDetailsController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindGameDetailsInjector(GameDetailsComponent.Builder builder);

    @Binds
    @IntoMap
    @ControllerKey(ScreenshotController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindScreenshotInjector(ScreenshotComponent.Builder builder);
}
