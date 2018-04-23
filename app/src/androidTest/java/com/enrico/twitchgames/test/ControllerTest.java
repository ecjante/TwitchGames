package com.enrico.twitchgames.test;

import android.content.Intent;

import com.bluelinelabs.conductor.Controller;
import com.enrico.twitchgames.data.GameRepository;
import com.enrico.twitchgames.data.TestIgdbService;
import com.enrico.twitchgames.data.TestTwitchService;
import com.enrico.twitchgames.home.MainActivity;
import com.enrico.twitchgames.ui.TestScreenNavigator;

import org.junit.Rule;

/**
 * Created by enrico.
 */
public abstract class ControllerTest {

    @Rule
    public ControllerTestRule<MainActivity> testRule = new ControllerTestRule<>(MainActivity.class);

    protected TestTwitchService twitchService = testRule.twitchService;
    protected TestIgdbService igdbService = testRule.igdbService;
    protected GameRepository gameRepository = testRule.gameRepository;
    protected TestScreenNavigator screenNavigator = testRule.screenNavigator;

    public ControllerTest() {
        testRule.screenNavigator.overrideInitialController(controllerToLaunch());
    }

    protected abstract Controller controllerToLaunch();

    protected void launch() {
        launch(null);
    }

    protected void launch(Intent intent) {
        testRule.launchActivity(intent);
    }
}
