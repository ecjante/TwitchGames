package com.enrico.twitchgames.ui;

import com.enrico.twitchgames.models.twitch.TwitchStream;

/**
 * Created by enrico.
 */
public interface ScreenNavigator {

    boolean pop();

    void goToGameDetails(long twitchGameId, String gameName, String gameBoxTemplate);

    void openStream(TwitchStream stream);

    void playVideo(String id);

    void openScreenshot(String url);
}
