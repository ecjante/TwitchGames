package com.enrico.twitchgames.test;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import com.enrico.twitchgames.base.TestApp;
import com.enrico.twitchgames.data.GameRepository;
import com.enrico.twitchgames.data.TestIgdbService;
import com.enrico.twitchgames.data.TestTwitchService;
import com.enrico.twitchgames.ui.TestScreenNavigator;

/**
 * Created by enrico.
 */
public class ControllerTestRule<T extends Activity> extends ActivityTestRule<T> {

    public final TestScreenNavigator screenNavigator;
    public final TestTwitchService twitchService;
    public final TestIgdbService igdbService;
    public final GameRepository gameRepository;

    public ControllerTestRule(Class<T> activityClass) {
        super(activityClass, true, false);
        screenNavigator = TestApp.getComponent().screenNavigator();
        twitchService = TestApp.getComponent().twitchService();
        igdbService = TestApp.getComponent().igdbService();
        gameRepository = TestApp.getComponent().gameRepository();
    }

    public void clearState() {
        twitchService.clearErrorFlags();
        twitchService.clearHoldFlags();
        igdbService.clearErrorFlags();
        igdbService.clearHoldFlags();
        gameRepository.clearCache();
    }
}
