package com.enrico.twitchgames.data;

import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.enrico.twitchgames.test.TestUtils;
import com.squareup.moshi.Types;

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
            List<IgdbGame> response = testUtils.loadJson(
                    "mock/igdb/games/fortnite.json",
                    Types.newParameterizedType(List.class, IgdbGame.class)
            );
            if (isHolding(FLAG_GET_GAME)) {
                return holdingSingle(response, FLAG_GET_GAME);
            }
            return Single.just(response);
        }
        return Single.error(new IOException());
    }
}
