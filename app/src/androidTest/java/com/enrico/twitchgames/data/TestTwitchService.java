package com.enrico.twitchgames.data;

import com.enrico.twitchgames.data.responses.TwitchStreamsResponse;
import com.enrico.twitchgames.data.responses.TwitchTopGamesResponse;
import com.enrico.twitchgames.test.TestUtils;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by enrico.
 */
@Singleton
public class TestTwitchService implements TwitchService {

    private final TestUtils testUtils;
    private boolean sendError;

    @Inject
    TestTwitchService(TestUtils testUtils) {

        this.testUtils = testUtils;
    }

    @Override
    public Single<TwitchTopGamesResponse> getTopGames(int limit, int offset) {
        if (!sendError) {
            TwitchTopGamesResponse response = testUtils.loadJson("mock/twitch/get_top_games.json", TwitchTopGamesResponse.class);
            return Single.just(response);
        }
        return Single.error(new IOException());
    }

    @Override
    public Single<TwitchStreamsResponse> getStreams(String game, int limit, int offset) {
        return null;
    }

    public void setSendError(boolean sendError) {
        this.sendError = sendError;
    }
}
