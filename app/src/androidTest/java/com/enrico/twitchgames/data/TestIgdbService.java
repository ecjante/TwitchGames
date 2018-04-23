package com.enrico.twitchgames.data;

import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.enrico.twitchgames.test.TestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by enrico.
 */
@Singleton
public class TestIgdbService implements IgdbService {

    private final TestUtils testUtils;
    private boolean sendError;

    @Inject
    TestIgdbService(TestUtils testUtils) {
        this.testUtils = testUtils;
    }

    @Override
    public Single<List<IgdbGame>> getGame(String searchQuery) {
        if (!sendError) {
            IgdbGame response = testUtils.loadJson("mock/igdb/get_god_of_war_game.json", IgdbGame.class);
            List<IgdbGame> list = new ArrayList<>();
            list.add(response);
            return Single.just(list);
        }
        return Single.error(new IOException());
    }

    public void setSendError(boolean sendError) {
        this.sendError = sendError;
    }
}
