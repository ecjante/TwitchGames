package com.enrico.twitchgames.details;

import com.enrico.twitchgames.base.ScreenModule;
import com.enrico.twitchgames.di.ScreenComponent;
import com.enrico.twitchgames.di.ScreenScope;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by enrico.
 */
@ScreenScope
@Subcomponent(modules = {
        ScreenModule.class,
        GameDetailsScreenModule.class,
})
public interface GameDetailsComponent extends ScreenComponent<GameDetailsController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<GameDetailsController> {

        @BindsInstance
        public abstract void bindTwitchGameId(@Named("twitch_game_id") long twitchGameId);

        @BindsInstance
        public abstract void bindGameName(@Named("game_name") String gameName);

        @BindsInstance
        public abstract void bindGameBoxTemplate(@Named("game_box_template") String gameBoxTemplate);

        @Override
        public void seedInstance(GameDetailsController instance) {
            bindTwitchGameId(instance.getArgs().getLong(GameDetailsController.TWITCH_GAME_ID_KEY));
            bindGameName(instance.getArgs().getString(GameDetailsController.GAME_NAME_KEY));
            bindGameBoxTemplate(instance.getArgs().getString(GameDetailsController.GAME_BOX_TEMPLATE_KEY));

        }
    }
}
