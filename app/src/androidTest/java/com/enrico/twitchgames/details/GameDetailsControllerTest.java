package com.enrico.twitchgames.details;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.bluelinelabs.conductor.Controller;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.data.TestIgdbService;
import com.enrico.twitchgames.data.TestTwitchService;
import com.enrico.twitchgames.home.MainActivity;
import com.enrico.twitchgames.test.ControllerTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by enrico.
 */
@RunWith(AndroidJUnit4.class)
public class GameDetailsControllerTest extends ControllerTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class, true, false);

    @Before
    public void setUp() {
        testRule.clearState();
    }

    @Test
    public void gameDetailsSuccess() {
        launch();

        GameDetailsRobot.init()
                .verifyLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyName("Fortnite")
                .verifySummary("Fortnite is the living, action building game from the developer formerly known as Epic MegaGames. You and your friends will lead a group of Heroes to reclaim and rebuild a homeland that has been left empty by mysterious darkness only known as \"the Storm\". \n \nBand together online to build extravagant forts, find or build insane weapons and traps and protect your towns from the strange monsters that emerge during the Storm. In an action experience from the only company smart enough to attach chainsaws to guns, get out there to push back the Storm and save the world. And don't forget to loot all the things.")
                .verifyReleaseDate("Jul 24, 2017")
                .verifyScreenshotShown()
                .verifyVideosShown();
    }

    @Test
    public void gameDetailsError() {
        igdbService.setErrorFlags(TestIgdbService.FLAG_GET_GAME);
        launch();

        GameDetailsRobot.init()
                .verifyLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyErrorText(R.string.api_error_igdb_game);
    }

    @Test
    public void screenshots() {
        launch();


    }

    @Test
    public void streamsSuccess() {
        launch();

        GameDetailsRobot.init()
                .verifyStreamsLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyStreamsErrorVisibility(ViewMatchers.Visibility.GONE)
                .verifyStreamsShown("DrLupo");
    }

    @Test
    public void streamsError() {
        twitchService.setErrorFlags(TestTwitchService.FLAG_STREAMS);
        launch();

        GameDetailsRobot.init()
                .verifyStreamsLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyStreamsErrorText(R.string.api_error_streams);
    }

    @Test
    public void gameSuccessStreamsError() {
        twitchService.setErrorFlags(TestTwitchService.FLAG_STREAMS);
        launch();

        GameDetailsRobot.init()
                .verifyLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyStreamsLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyStreamsErrorText(R.string.api_error_streams)
                .verifyErrorVisibility(ViewMatchers.Visibility.GONE);
    }

    @Test
    public void gameErrorStreamsSuccess() {
        igdbService.setErrorFlags(TestIgdbService.FLAG_GET_GAME);
        launch();

        GameDetailsRobot.init()
                .verifyLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyErrorText(R.string.api_error_igdb_game)
                .verifyStreamsLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyStreamsErrorVisibility(ViewMatchers.Visibility.GONE)
                .verifyStreamsShown("DrLupo");
    }

    @Test
    public void loadingGame() {
        igdbService.setHoldFlags(TestIgdbService.FLAG_GET_GAME);
        launch();

        GameDetailsRobot.init()
                .verifyLoadingVisibility(ViewMatchers.Visibility.VISIBLE);
    }

    @Test
    public void loadingStreams() {
        twitchService.setHoldFlags(TestTwitchService.FLAG_STREAMS);
        launch();

        GameDetailsRobot.init()
                .verifyStreamsLoadingVisibility(ViewMatchers.Visibility.VISIBLE);
    }

    @Override
    protected Controller controllerToLaunch() {
        return GameDetailsController.newInstance(33214, "Fortnite", "https://static-cdn.jtvnw.net/ttv-boxart/Fortnite-{width}x{height}.jpg");
    }
}
