package com.enrico.twitchgames.details;

import android.os.Bundle;

import com.bluelinelabs.conductor.Controller;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.base.BaseController;

/**
 * Created by enrico.
 */
public class GameDetailsController extends BaseController {

    static final String TWITCH_GAME_ID_KEY = "twitch_game_id";
    static final String GAME_NAME_KEY = "game_name";

    public static Controller newInstance(long twitchGameId, String gameName) {
        Bundle bundle = new Bundle();
        bundle.putLong(TWITCH_GAME_ID_KEY, twitchGameId);
        bundle.putString(GAME_NAME_KEY, gameName);
        return new GameDetailsController(bundle);
    }

    public GameDetailsController(Bundle bundle) {
        super(bundle);
    }

    @Override
    protected int layoutRes() {
        return R.layout.screen_game_details;
    }
}
