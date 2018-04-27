package com.enrico.twitchgames.ui;

import com.enrico.twitchgames.models.twitch.TwitchStream;

/**
 * Created by enrico.
 *
 * interface to handle navigation
 */
public interface ScreenNavigator {

    // pop current screen
    boolean pop();

    // Go to GameDetails screen
    void goToGameDetails(long twitchGameId, String gameName, String gameBoxTemplate);

    // Open twitch stream
    void openStream(TwitchStream stream);

    // opens QuickPlayActivity to play video
    void playVideo(String id);

    // Go to Screenshot screen
    void openScreenshot(String url);
}
