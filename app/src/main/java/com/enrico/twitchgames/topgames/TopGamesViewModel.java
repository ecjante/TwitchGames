package com.enrico.twitchgames.topgames;

import com.enrico.twitchgames.R;
import com.enrico.twitchgames.di.ScreenScope;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by enrico.
 *
 * View Model for Twitch top games
 */
@ScreenScope
class TopGamesViewModel {

    private final BehaviorRelay<Integer> errorRelay = BehaviorRelay.create();
    private final BehaviorRelay<Boolean> loadingRelay = BehaviorRelay.create();
    private final BehaviorRelay<Boolean> moreLoadingRelay = BehaviorRelay.create();

    @Inject
    TopGamesViewModel() {

    }

    // Observables for the view

    Observable<Boolean> loading() {
        return loadingRelay;
    }

    Observable<Boolean> moreLoading() {
        return moreLoadingRelay;
    }

    Observable<Integer> error() {
        return errorRelay;
    }

    // Consumers for updating

    Consumer<Boolean> loadingUpdated() {
        return loadingRelay;
    }

    Consumer<Boolean> moreLoadingUpdated() {
        return moreLoadingRelay;
    }

    Action topGamesUpdated() {
        return () -> errorRelay.accept(-1);
    }

    Consumer<Throwable> onError() {
        return throwable -> {
            Timber.e(throwable, "Error loading Top Games");
            errorRelay.accept(R.string.api_error_top_games);
        };
    }
}
