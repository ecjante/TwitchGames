package com.enrico.twitchgames.data;

import com.enrico.twitchgames.database.favorites.FavoriteTwitchGameService;
import com.enrico.twitchgames.database.igdb.DbIgdbGameService;
import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.enrico.twitchgames.models.igdb.twitchonlygames.CreativeGame;
import com.enrico.twitchgames.models.igdb.twitchonlygames.IRLGame;
import com.enrico.twitchgames.models.twitch.TwitchStream;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import timber.log.Timber;

/**
 * Created by enrico.
 */
@Singleton
public class GameRepository {

    private static final long IRL_ID = 494717;
    private static final long CREATIVE_ID = 488191;

    private final Provider<TwitchRequester> twitchRequesterProvider;
    private final Provider<IgdbRequester> igdbRequesterProvider;
    private final Provider<DbIgdbGameService> dbIgdbGameServiceProvider;
    private final Provider<FavoriteTwitchGameService> favoriteTwitchGameServiceProvider;
    private final Scheduler scheduler;

    private final List<TwitchTopGame> cachedTwitchTopGames = new ArrayList<>();
    private final Map<Long, IgdbGame> cachedIgdbGames = new HashMap<>();
    private final Map<Long, List<TwitchStream>> cachedTwitchStreams = new HashMap<>();

    private final long FETCH_TIME_THRESHOLD = TimeUnit.MINUTES.toMillis(30);
    private long lastTwitchTopGamesFetch;
    private final Map<Long, Long> lastTwitchStreamsFetch = new HashMap<>();

    @Inject
    GameRepository(
            Provider<TwitchRequester> twitchRequesterProvider,
            Provider<IgdbRequester> igdbRequesterProvider,
            Provider<DbIgdbGameService> dbIgdbGameServiceProvider,
            Provider<FavoriteTwitchGameService> favoriteTwitchGameServiceProvider,
            @Named("network_scheduler") Scheduler scheduler
    ) {
        this.twitchRequesterProvider = twitchRequesterProvider;
        this.igdbRequesterProvider = igdbRequesterProvider;
        this.dbIgdbGameServiceProvider = dbIgdbGameServiceProvider;
        this.favoriteTwitchGameServiceProvider = favoriteTwitchGameServiceProvider;
        this.scheduler = scheduler;
    }

    public Single<List<TwitchTopGame>> getTopGames() {
        return Maybe.concat(cachedTwitchTopGames(), apiTwitchTopGames())
                .firstOrError()
                .subscribeOn(scheduler);
    }

    public Single<List<TwitchTopGame>> getNextTopGames() {
        return apiTwitchNextTopGames()
                .subscribeOn(scheduler)
                .toSingle();
    }

    public Single<List<TwitchTopGame>> getFavoriteGames() {
        return favoriteTwitchGameServiceProvider.get().favoritedTwitchGames()
                .subscribeOn(scheduler);
    }

    public Single<IgdbGame> getGameInfo(long id, String query) {
        return Maybe.concat(cachedIgdbGame(id), dbIgdbGame(id), apiIgdbGame(id, query))
                .firstOrError()
                .subscribeOn(scheduler);
    }

    public Single<List<TwitchStream>> getStreams(long id, String game) {
        return Maybe.concat(cachedTwitchStreams(id), apiTwitchStreams(id, game))
                .firstOrError()
                .subscribeOn(scheduler);
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

    private Maybe<List<TwitchTopGame>> apiTwitchNextTopGames() {
        return twitchRequesterProvider.get().getNextTopGames()
                .doOnSuccess(cachedTwitchTopGames::addAll)
                .toMaybe();
    }

    private Maybe<IgdbGame> cachedIgdbGame(long id) {
        return Maybe.create(e -> {
            if (id == IRL_ID) {
                Timber.i("Return IRL game");
                e.onSuccess(new IRLGame());
            } else if (id == CREATIVE_ID) {
                Timber.i("Return Creative game");
                e.onSuccess(new CreativeGame());
            } else if (cachedIgdbGames.containsKey(id)) {
                e.onSuccess(cachedIgdbGames.get(id));
            }
            e.onComplete();
        });
    }

    private Maybe<IgdbGame> dbIgdbGame(long id) {
        return dbIgdbGameServiceProvider.get().getIgdbGame(id)
                .doOnSuccess(igdbGame -> {
                    Timber.i("Received igdb game from db with id: " + id);
                    cachedIgdbGames.put(id, igdbGame);
                });
    }

    private Maybe<IgdbGame> apiIgdbGame(long id, String query) {
        return igdbRequesterProvider.get().getGameInfo(id, query)
                .doOnSuccess(igdbGame -> {
                    Timber.i("Received igdb game from api with id: " + id);
                    dbIgdbGameServiceProvider.get().addIgdbGame(igdbGame);
                    cachedIgdbGames.put(id, igdbGame);
                })
                .doOnError(Timber::e)
                .toMaybe();
    }

    private Maybe<List<TwitchStream>> cachedTwitchStreams(long id) {
        return Maybe.create(e -> {
            if (cachedTwitchStreams.containsKey(id) && !shouldFetchTwitchStreams(id)) {
                e.onSuccess(cachedTwitchStreams.get(id));
            }
            e.onComplete();
        });
    }

    private Maybe<List<TwitchStream>> apiTwitchStreams(long id, String game) {
        return twitchRequesterProvider.get().getStreams(game)
                .doOnSuccess(twitchStreams -> {
                    lastTwitchStreamsFetch.put(id, System.currentTimeMillis());
                    cachedTwitchStreams.put(id, twitchStreams);
                })
                .toMaybe();
    }

    private boolean shouldFetchTwitchTopGames() {
        return (System.currentTimeMillis() - lastTwitchTopGamesFetch) > FETCH_TIME_THRESHOLD;
    }

    private boolean shouldFetchTwitchStreams(long id) {
        return lastTwitchStreamsFetch.containsKey(id) &&
                (System.currentTimeMillis() - lastTwitchStreamsFetch.get(id)) > FETCH_TIME_THRESHOLD;
    }

    public void clearCache() {
        cachedTwitchTopGames.clear();
        cachedIgdbGames.clear();
        cachedTwitchStreams.clear();
    }

    public void clearIgdbCache() {
        cachedIgdbGames.clear();
    }
}
