package com.enrico.twitchgames.ui;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;

/**
 * Created by enrico.
 */
public interface ScreenNavigator {

    void initWithRouter(Router router, Controller rootScreen);

    boolean pop();

    void goToGameDetails(long twitchGameId, String gameName);

    void clear();
}
