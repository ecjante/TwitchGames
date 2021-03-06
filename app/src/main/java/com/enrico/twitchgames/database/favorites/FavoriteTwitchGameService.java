package com.enrico.twitchgames.database.favorites;

import android.annotation.SuppressLint;

import com.enrico.twitchgames.database.AppDatabase;
import com.enrico.twitchgames.database.DbService;
import com.enrico.twitchgames.models.twitch.TwitchGame;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by enrico.
 *
 * Service to use call the FavoriteTwitchGameDao
 */
@Singleton
public class FavoriteTwitchGameService extends DbService {

    // Relay for the favorited ids. Reactive for when a game is favorited and unfavorited
    private final BehaviorRelay<Set<Long>> favoritedTwitchGameIdsRelay = BehaviorRelay.createDefault(new HashSet<>());
    private final AppDatabase appDatabase;

    @SuppressLint("CheckResult")
    @Inject
    FavoriteTwitchGameService(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        // Grab and store the favorited ids in the relay
        appDatabase.favoriteTwitchGameDao().getFavoritedTwitchGameIds()
                .subscribeOn(Schedulers.io())
                .map(HashSet::new)
                .subscribe(favoritedTwitchGameIdsRelay, throwable -> Timber.e(throwable, "Error loading favorited twitch games from database"));
    }

    /**
     * Observable for the favorited ids
     * @return
     */
    public Observable<Set<Long>> favoritedTwitchGameIds() {
        return favoritedTwitchGameIdsRelay;
    }

    /**
     * Get the favorited twitch games to view the list of games
     * @return
     */
    public Single<List<TwitchTopGame>> favoritedTwitchGames() {
        return  appDatabase.favoriteTwitchGameDao().getFavoritedTwitchGames()
                .subscribeOn(Schedulers.io())
                .map(favoriteTwitchGames -> {
                    List<TwitchTopGame> games = new ArrayList<>();
                    for (FavoriteTwitchGame game : favoriteTwitchGames) {
                        games.add(TwitchTopGame.buildFromDb(game));
                    }
                    return games;
                });
    }

    /**
     * adds or removes the favorited game
     * @param game
     */
    public void toggleFavoriteTwitchGame(TwitchGame game) {
        runDbOp(() -> {
            if (favoritedTwitchGameIdsRelay.getValue().contains(game.id())) {
                deleteFavoriteTwitchGame(game);
            } else {
                addFavoriteTwitchGame(game);
            }
        });
    }

    /**
     * Delete favorited game
     * @param game
     */
    private void deleteFavoriteTwitchGame(TwitchGame game) {
        appDatabase.favoriteTwitchGameDao().deleteFavorite(FavoriteTwitchGame.build(game));
    }

    /**
     * Add favorited game
     * @param game
     */
    private void addFavoriteTwitchGame(TwitchGame game) {
        appDatabase.favoriteTwitchGameDao().addFavorite(FavoriteTwitchGame.build(game));
    }

}
