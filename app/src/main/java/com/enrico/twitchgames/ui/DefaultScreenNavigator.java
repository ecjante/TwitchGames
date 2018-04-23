package com.enrico.twitchgames.ui;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.enrico.twitchgames.details.GameDetailsController;
import com.enrico.twitchgames.di.ActivityScope;

import javax.inject.Inject;

/**
 * Created by enrico.
 */
@ActivityScope
public class DefaultScreenNavigator implements ScreenNavigator {

    private Router router;

    @Inject
    DefaultScreenNavigator() {

    }

    @Override
    public void initWithRouter(Router router, Controller rootScreen) {
        this.router = router;
        if (!router.hasRootController())
            router.setRoot(RouterTransaction.with(rootScreen));
    }

    @Override
    public boolean pop() {
        return router != null && router.handleBack();
    }

    @Override
    public void goToGameDetails(long twitchGameId, String gameName) {
        if (router != null) {
            router.pushController(
                    RouterTransaction.with(GameDetailsController.newInstance(twitchGameId, gameName))
                            .pushChangeHandler(new FadeChangeHandler())
                            .popChangeHandler(new FadeChangeHandler())

            );
        }
    }

    @Override
    public void clear() {
        router = null;
    }
}
