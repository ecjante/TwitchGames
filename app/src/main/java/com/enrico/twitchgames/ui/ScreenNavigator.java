package com.enrico.twitchgames.ui;

/**
 * Created by enrico.
 */
public interface ScreenNavigator {

    boolean pop();

    void goToGameDetails(long twitchGameId, String gameName);
}
