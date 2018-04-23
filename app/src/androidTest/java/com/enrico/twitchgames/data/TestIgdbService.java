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
public class TestIgdbService extends TestService implements IgdbService {

    public static final int FLAG_GET_GAME = 1;

    @Inject
    TestIgdbService(TestUtils testUtils) {
        super(testUtils);
    }

    @Override
    public Single<List<IgdbGame>> getGame(String searchQuery) {
        if (noError(FLAG_GET_GAME)) {
            IgdbGame response = testUtils.loadJson("mock/igdb/get_fortnite_game.json", IgdbGame.class);
            List<IgdbGame> list = new ArrayList<>();
            list.add(response);
            if (isHolding(FLAG_GET_GAME)) {
                return holdingSingle(list, FLAG_GET_GAME);
            }
            return Single.just(list);
        }
        return Single.error(new IOException());
    }
}
