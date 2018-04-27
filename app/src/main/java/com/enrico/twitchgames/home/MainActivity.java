package com.enrico.twitchgames.home;

import com.bluelinelabs.conductor.Controller;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.base.BaseActivity;
import com.enrico.twitchgames.topgames.TopGamesController;

/**
 * Created by enrico
 *
 * MainActivity that will essentially house each screen. The initial screen is the Twitch top games
 * view
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public Controller initialScreen() {
        return new TopGamesController();
    }
}
