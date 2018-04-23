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
public class TestTwitchService extends TestService implements TwitchService {

    public static final int FLAG_TOP_GAMES = 1;
    public static final int FLAG_STREAMS = 2;

    @Inject
    TestTwitchService(TestUtils testUtils) {
        super(testUtils);
    }

    @Override
    public Single<TwitchTopGamesResponse> getTopGames(int limit, int offset) {
        if (noError(FLAG_TOP_GAMES)) {
            TwitchTopGamesResponse response = testUtils.loadJson("mock/twitch/get_top_games.json", TwitchTopGamesResponse.class);
            if (isHolding(FLAG_TOP_GAMES)) {
                return holdingSingle(response, FLAG_TOP_GAMES);
            }
            return Single.just(response);
        }
        return Single.error(new IOException());
    }

    @Override
    public Single<TwitchStreamsResponse> getStreams(String game, int limit, int offset) {
        if (noError(FLAG_STREAMS)) {
            TwitchStreamsResponse response = testUtils.loadJson("mock/twitch/get_streams.json", TwitchStreamsResponse.class);
            if (isHolding(FLAG_STREAMS)) {
                return holdingSingle(response, FLAG_STREAMS);
            }
            return Single.just(response);
        }
        return Single.error(new IOException());
    }
}
