package com.enrico.twitchgames.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.enrico.twitchgames.lifecycle.ActivityLifecycleTask;
import com.enrico.twitchgames.models.twitch.TwitchStream;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by enrico.
 */
@Singleton
public class TestScreenNavigator extends ActivityLifecycleTask implements ScreenNavigator {

    private final DefaultScreenNavigator defaultScreenNavigator;
    private Controller overrideController;

    @Inject
    TestScreenNavigator(Context context) {
        this.defaultScreenNavigator = new DefaultScreenNavigator(context);
    }

    /**
     * Set the Controller to launch when the Activity attaches to the ScreenNavigator. This will
     * be used instead of the Controller provided by {@link RouterProvider#initialScreen()}
     *
     * @param overrideController
     */
    public void overrideInitialController(Controller overrideController) {
        this.overrideController = overrideController;
    }

    @Override
    public void onCreate(AppCompatActivity activity) {
        if (!(activity instanceof RouterProvider)) {
            throw new IllegalArgumentException("Activity must be instance of RouterProvider");
        }
        Controller launchController = overrideController == null ? ((RouterProvider) activity).initialScreen() : overrideController;
        defaultScreenNavigator.initWithRouter(((RouterProvider) activity).getRouter(), launchController);
    }

    @Override
    public boolean pop() {
        return defaultScreenNavigator.pop();
    }

    @Override
    public void goToGameDetails(long twitchGameId, String gameName, String gameBoxTemplate) {
        defaultScreenNavigator.goToGameDetails(twitchGameId, gameName, gameBoxTemplate);
    }

    @Override
    public void openStream(TwitchStream stream) {
        defaultScreenNavigator.openStream(stream);
    }

    @Override
    public void playVideo(String id) {
        defaultScreenNavigator.playVideo(id);
    }

    @Override
    public void openScreenshot(String url) {
        defaultScreenNavigator.openScreenshot(url);
    }

    @Override
    public void onDestroy(AppCompatActivity activity) {
        defaultScreenNavigator.onDestroy(activity);
    }
}
