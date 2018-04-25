package com.enrico.twitchgames.database.favorites;

import android.annotation.SuppressLint;

import com.enrico.twitchgames.database.AppDatabase;
import com.enrico.twitchgames.models.twitch.TwitchGame;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by enrico.
 */
@Singleton
public class FavoriteTwitchGameService {

    private final BehaviorRelay<Set<Long>> favoritedTwitchGameIdsRelay = BehaviorRelay.createDefault(new HashSet<>());
    private final AppDatabase appDatabase;

    @SuppressLint("CheckResult")
    @Inject
    FavoriteTwitchGameService(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        appDatabase.favoriteTwitchGameDao().getFavoritedTwitchGames()
                .subscribeOn(Schedulers.io())
                .map(favoriteTwitchGames -> {
                    Set<Long> twitchGameIds = new HashSet<>();
                    for (FavoriteTwitchGame favoriteTwitchGame : favoriteTwitchGames) {
                        twitchGameIds.add(favoriteTwitchGame.getId());
                    }
                    return twitchGameIds;
                })
                .subscribe(favoritedTwitchGameIdsRelay, throwable -> Timber.e(throwable, "Error loading favorited twitch games from database"));
    }

    public Observable<Set<Long>> favoritedTwitchGameIds() {
        return favoritedTwitchGameIdsRelay;
    }

    public void toggleFavoriteTwitchGame(TwitchGame game) {
        runDbOp(() -> {
            if (favoritedTwitchGameIdsRelay.getValue().contains(game.id())) {
                deleteFavoriteTwitchGame(game);
            } else {
                addFavoriteTwitchGame(game);
            }
        });
    }

    private void deleteFavoriteTwitchGame(TwitchGame game) {
        appDatabase.favoriteTwitchGameDao().deleteFavorite(FavoriteTwitchGame.build(game));
    }

    private void addFavoriteTwitchGame(TwitchGame game) {
        appDatabase.favoriteTwitchGameDao().addFavorite(FavoriteTwitchGame.build(game));
    }

    @SuppressLint("CheckResult")
    private void runDbOp(Action action) {
        Completable.fromAction(action)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {}, throwable -> Timber.e(throwable, "Error performing database operation"));
    }
}
