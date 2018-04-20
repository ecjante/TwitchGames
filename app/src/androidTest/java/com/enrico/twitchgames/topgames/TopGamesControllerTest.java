package com.enrico.twitchgames.topgames;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.enrico.twitchgames.R;
import com.enrico.twitchgames.base.TestApp;
import com.enrico.twitchgames.data.TestTwitchService;
import com.enrico.twitchgames.home.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by enrico.
 */
@RunWith(AndroidJUnit4.class)
public class TopGamesControllerTest {

    @Inject TestTwitchService twitchService;

    @Rule public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void setUp() {
        TestApp.getComponent().inject(this);
    }

    @Test
    public void loadGames() {
        twitchService.setSendError(false);
        activityTestRule.launchActivity(null);

        onView(withId(R.id.loading_indicator))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.tv_error))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.top_games_list))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(allOf(withId(R.id.tv_game_name), withText("God of War")))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void loadGamesError() {
        twitchService.setSendError(true);
        activityTestRule.launchActivity(null);

        onView(withId(R.id.loading_indicator))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.top_games_list))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.tv_error))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(withId(R.id.tv_error))
                .check(matches(allOf(withText(R.string.api_error_top_games), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))));
    }
}
