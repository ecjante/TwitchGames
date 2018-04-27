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
 *
 * Repository that returns data from cache, database, or from the API.
 */
@Singleton
public class GameRepository {

    // IDs for twitch only games. Will add more when others are discovered.
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

    /**
     * Get top games from cache or api
     * @return
     */
    public Single<List<TwitchTopGame>> getTopGames() {
        return Maybe.concat(cachedTwitchTopGames(), apiTwitchTopGames())
                .firstOrError()
                .subscribeOn(scheduler);
    }

    /**
     * Get next top games from api
     * @return
     */
    public Single<List<TwitchTopGame>> getNextTopGames() {
        return apiTwitchNextTopGames()
                .subscribeOn(scheduler)
                .toSingle();
    }

    /**
     * Get favorite games from the database
     * @return
     */
    public Single<List<TwitchTopGame>> getFavoriteGames() {
        return favoriteTwitchGameServiceProvider.get().favoritedTwitchGames()
                .subscribeOn(scheduler);
    }

    /**
     * Get game info from cache, database, or API
     * @param id    used to get cached game or game from db
     * @param query used to get game from API
     * @return
     */
    public Single<IgdbGame> getGameInfo(long id, String query) {
        return Maybe.concat(cachedIgdbGame(id), dbIgdbGame(id), apiIgdbGame(id, query))
                .firstOrError()
                .subscribeOn(scheduler);
    }

    /**
     * Get streams for specified game from cache or API
     * @param id
     * @param game
     * @return
     */
    public Single<List<TwitchStream>> getStreams(long id, String game) {
        return Maybe.concat(cachedTwitchStreams(id), apiTwitchStreams(id, game))
                .firstOrError()
                .subscribeOn(scheduler);
    }

    /**
     * Will get twitch top games from cache unless cache is empty or the time since last fetch has
     * been more than the fetch threshold
     * @return
     */
    private Maybe<List<TwitchTopGame>> cachedTwitchTopGames() {
        return Maybe.create(e -> {
            if (!cachedTwitchTopGames.isEmpty() && !shouldFetchTwitchTopGames()) {
                e.onSuccess(cachedTwitchTopGames);
            }
            e.onComplete();
        });
    }

    /**
     * Get top games from twitch api. Cache the top games and set fetch time
     * @return
     */
    private Maybe<List<TwitchTopGame>> apiTwitchTopGames() {
        return twitchRequesterProvider.get().getTopGames()
                .doOnSuccess(twitchTopGames -> {
                    cachedTwitchTopGames.clear();
                    cachedTwitchTopGames.addAll(twitchTopGames);
                    lastTwitchTopGamesFetch = System.currentTimeMillis();
                })
                .toMaybe();
    }

    /**
     * Get more top games from twitch api
     * @return
     */
    private Maybe<List<TwitchTopGame>> apiTwitchNextTopGames() {
        return twitchRequesterProvider.get().getNextTopGames()
                .doOnSuccess(cachedTwitchTopGames::addAll)
                .toMaybe();
    }

    /**
     * Get IGDB game from cache if it exists
     * @param id
     * @return
     */
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

    /**
     * Get IGDB game from the database if it exists
     * @param id
     * @return
     */
    private Maybe<IgdbGame> dbIgdbGame(long id) {
        return dbIgdbGameServiceProvider.get().getIgdbGame(id)
                .doOnSuccess(igdbGame -> {
                    Timber.i("Received igdb game from db with id: " + id);
                    cachedIgdbGames.put(id, igdbGame);
                });
    }

    /**
     * Get IGDB game from API. add the game to the database and cache
     * @param id
     * @param query
     * @return
     */
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

    /**
     * Get twitch streams from the cache if it exists or the time since last fetch has
     * been more than the fetch threshold
     * @param id
     * @return
     */
    private Maybe<List<TwitchStream>> cachedTwitchStreams(long id) {
        return Maybe.create(e -> {
            if (cachedTwitchStreams.containsKey(id) && !shouldFetchTwitchStreams(id)) {
                e.onSuccess(cachedTwitchStreams.get(id));
            }
            e.onComplete();
        });
    }

    /**
     * Get game streams from the API. Cache the streams and set the fetch time
     * @param id
     * @param game
     * @return
     */
    private Maybe<List<TwitchStream>> apiTwitchStreams(long id, String game) {
        return twitchRequesterProvider.get().getStreams(game)
                .doOnSuccess(twitchStreams -> {
                    lastTwitchStreamsFetch.put(id, System.currentTimeMillis());
                    cachedTwitchStreams.put(id, twitchStreams);
                })
                .toMaybe();
    }

    /**
     * Helper to check if should fetch new set of twitch top games
     * @return
     */
    private boolean shouldFetchTwitchTopGames() {
        return (System.currentTimeMillis() - lastTwitchTopGamesFetch) > FETCH_TIME_THRESHOLD;
    }

    /**
     * Helper to check if should fetch new set of twitch streams
     * @param id
     * @return
     */
    private boolean shouldFetchTwitchStreams(long id) {
        return lastTwitchStreamsFetch.containsKey(id) &&
                (System.currentTimeMillis() - lastTwitchStreamsFetch.get(id)) > FETCH_TIME_THRESHOLD;
    }

    /**
     * Helper to clear the cache
     */
    public void clearCache() {
        cachedTwitchTopGames.clear();
        cachedIgdbGames.clear();
        cachedTwitchStreams.clear();
    }

    /**
     * Helper to just clear IGDB cache
     */
    public void clearIgdbCache() {
        cachedIgdbGames.clear();
    }
}
