package com.enrico.twitchgames.details;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.matcher.ViewMatchers;

import com.enrico.twitchgames.R;

import org.hamcrest.Matchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by enrico.
 */
class GameDetailsRobot {

    private static final String CHROME_PACKAGE = "com.android.chrome";
    private static final String TWITCH_PACKAGE = "tv.twitch.android.app";
    private static final String TEST_BROWSER_URI = "https://www.twitch.tv/drlupo";
    private static final String TEST_TWITCH_URI = "twitch://stream/drlupo";

    static GameDetailsRobot init() {
        return new GameDetailsRobot();
    }

    private GameDetailsRobot() {

    }

    GameDetailsRobot verifyName(String name) {
        onView(withId(R.id.tv_game_name)).check(matches(withText(name)));
        return this;
    }

    GameDetailsRobot verifyReleaseDate(String releaseDate) {
        onView(withId(R.id.tv_release_date)).check(matches(withText(releaseDate)));
        return this;
    }

    GameDetailsRobot verifySummary(String summary) {
        onView(withId(R.id.tv_summary)).check(matches(withText(summary)));
        return this;
    }

    GameDetailsRobot verifyErrorText(Integer textRes) {
        if (textRes == null) {
            onView(withId(R.id.tv_error)).check(matches(withText("")));
        } else {
            onView(withId(R.id.tv_error)).check(matches(withText(textRes)));
        }
        return this;
    }

    GameDetailsRobot verifyErrorVisibility(ViewMatchers.Visibility visibility) {
        onView(withId(R.id.tv_error)).check(matches(withEffectiveVisibility(visibility)));
        return this;
    }

    GameDetailsRobot verifyStreamsErrorText(Integer textRes) {
        if (textRes == null) {
            onView(withId(R.id.tv_streams_error)).check(matches(withText("")));
        } else {
            onView(withId(R.id.tv_streams_error)).check(matches(withText(textRes)));
        }
        return this;
    }

    GameDetailsRobot verifyStreamsErrorVisibility(ViewMatchers.Visibility visibility) {
        onView(withId(R.id.tv_streams_error)).check(matches(withEffectiveVisibility(visibility)));
        return this;
    }

    GameDetailsRobot verifyLoadingVisibility(ViewMatchers.Visibility visibility) {
        onView(withId(R.id.loading_indicator)).check(matches(withEffectiveVisibility(visibility)));
        return this;
    }

    GameDetailsRobot verifyStreamsLoadingVisibility(ViewMatchers.Visibility visibility) {
        onView(withId(R.id.streams_loading_indicator)).check(matches(withEffectiveVisibility(visibility)));
        return this;
    }

    GameDetailsRobot verifyStreamsShown(String name) {
        onView(allOf(withId(R.id.tv_channel_name), withText(name))).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        return this;
    }

    GameDetailsRobot verifyHeaderVisibility(ViewMatchers.Visibility visibility) {
        onView(withId(R.id.header)).check(matches(withEffectiveVisibility(visibility)));
        return this;
    }

    GameDetailsRobot verifyStreamClicked(Activity activity) {
        Intent browserIntent = new Intent();
        Uri browserUri = Uri.parse(TEST_BROWSER_URI);
        browserIntent.setData(browserUri);
        Instrumentation.ActivityResult browserResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, browserIntent);

        Intent twitchIntent = new Intent();
        Uri twitchUri = Uri.parse(TEST_TWITCH_URI);
        twitchIntent.setData(Uri.parse(TEST_TWITCH_URI));
        Instrumentation.ActivityResult twitchResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, twitchIntent);

        intending(allOf(
                hasData(browserUri),
                hasAction(Intent.ACTION_VIEW)
        )).respondWith(browserResult);

        intending(allOf(
                hasData(twitchUri),
                hasAction(Intent.ACTION_VIEW)
        )).respondWith(twitchResult);

        onView(withId(R.id.streams_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        PackageManager pm = activity.getPackageManager();
        boolean packagedInstalled;
        try {
            pm.getPackageInfo(TWITCH_PACKAGE, PackageManager.GET_ACTIVITIES);
            packagedInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            packagedInstalled = false;
        }
        if (packagedInstalled) {
            Intents.intended(allOf(
                    hasAction(Matchers.equalTo(Intent.ACTION_VIEW)),
                    IntentMatchers.hasData(Matchers.equalTo(twitchUri)),
                    IntentMatchers.toPackage(TWITCH_PACKAGE)));
        } else {
            Intents.intended(allOf(
                    hasAction(Matchers.equalTo(Intent.ACTION_VIEW)),
                    IntentMatchers.hasData(Matchers.equalTo(browserUri)),
                    IntentMatchers.toPackage(CHROME_PACKAGE)));
        }

        return this;
    }
}
