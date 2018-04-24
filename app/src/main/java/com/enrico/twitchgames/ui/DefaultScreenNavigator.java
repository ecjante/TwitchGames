package com.enrico.twitchgames.ui;

import android.support.v7.app.AppCompatActivity;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.enrico.twitchgames.details.GameDetailsController;
import com.enrico.twitchgames.di.ActivityScope;
import com.enrico.twitchgames.lifecycle.ActivityLifecycleTask;

import javax.inject.Inject;

/**
 * Created by enrico.
 */
@ActivityScope
public class DefaultScreenNavigator extends ActivityLifecycleTask implements ScreenNavigator {

    private Router router;

    @Inject
    DefaultScreenNavigator() {

    }

    @Override
    public void onCreate(AppCompatActivity activity) {
        if (!(activity instanceof RouterProvider)) {
            throw new IllegalArgumentException("Activity must be instance of RouterProvider");
        }
        initWithRouter(((RouterProvider) activity).getRouter(), ((RouterProvider) activity).initialScreen());
    }

    void initWithRouter(Router router, Controller rootScreen) {
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
    public void onDestroy(AppCompatActivity activity) {
        router = null;
    }
}
