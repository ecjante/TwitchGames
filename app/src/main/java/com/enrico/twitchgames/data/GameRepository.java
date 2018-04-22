package com.enrico.twitchgames.data;

import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by enrico.
 */
@Singleton
public class GameRepository {

    private final Provider<TwitchRequester> twitchRequesterProvider;
    private final Provider<IgdbRequester> igdbRequesterProvider;

    private final List<TwitchTopGame> cachedTwitchTopGames = new ArrayList<>();
    private final Map<Long, IgdbGame> cachedIgdbGame = new HashMap<>();

    private final long FETCH_TIME_THRESHOLD = TimeUnit.MINUTES.toMillis(30);
    private long lastTwitchTopGamesFetch;

    @Inject
    GameRepository(Provider<TwitchRequester> twitchRequesterProvider, Provider<IgdbRequester> igdbRequesterProvider) {
        this.twitchRequesterProvider = twitchRequesterProvider;
        this.igdbRequesterProvider = igdbRequesterProvider;
    }

    public Single<List<TwitchTopGame>> getTopGames() {
        return Maybe.concat(cachedTwitchTopGames(), apiTwitchTopGames())
                .firstOrError();
    }

    public Single<IgdbGame> getGameInfo(long id, String query) {
        return Maybe.concat(cachedIgdbGame(id), apiIgdbGame(id, query))
                .firstOrError();
    }

    private Maybe<List<TwitchTopGame>> cachedTwitchTopGames() {
        return Maybe.create(e -> {
            if (!cachedTwitchTopGames.isEmpty() && !shouldFetchTwitchTopGames()) {
                e.onSuccess(cachedTwitchTopGames);
            }
            e.onComplete();
        });
    }

    private Maybe<List<TwitchTopGame>> apiTwitchTopGames() {
        return twitchRequesterProvider.get().getTopGames()
                .doOnSuccess(twitchTopGames -> {
                    cachedTwitchTopGames.clear();
                    cachedTwitchTopGames.addAll(twitchTopGames);
                    lastTwitchTopGamesFetch = System.currentTimeMillis();
                })
                .toMaybe();
    }

    private Maybe<IgdbGame> cachedIgdbGame(long id) {
        return Maybe.create(e -> {
           if (cachedIgdbGame.containsKey(id)) {
               e.onSuccess(cachedIgdbGame.get(id));
           }
            e.onComplete();
        });
    }

    private Maybe<IgdbGame> apiIgdbGame(long id, String query) {
        return igdbRequesterProvider.get().getGameInfo(query)
                .doOnSuccess(igdbGame -> {
                    cachedIgdbGame.put(id, igdbGame);
                })
                .toMaybe();
    }

    private boolean shouldFetchTwitchTopGames() {
        return (System.currentTimeMillis() - lastTwitchTopGamesFetch) > FETCH_TIME_THRESHOLD;
    }
}
