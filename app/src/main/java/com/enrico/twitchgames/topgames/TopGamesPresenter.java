package com.enrico.twitchgames.topgames;

import com.enrico.poweradapter.adapter.RecyclerDataSource;
import com.enrico.twitchgames.data.GameRepository;
import com.enrico.twitchgames.data.TwitchRequester;
import com.enrico.twitchgames.di.ForScreen;
import com.enrico.twitchgames.di.ScreenScope;
import com.enrico.twitchgames.lifecycle.DisposableManager;
import com.enrico.twitchgames.models.twitch.TwitchGame;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;
import com.enrico.twitchgames.ui.ScreenNavigator;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by enrico.
 */
@ScreenScope
class TopGamesPresenter {

    private final TopGamesViewModel viewModel;
    private final GameRepository gameRepository;
    private final ScreenNavigator screenNavigator;
    private final DisposableManager disposableManager;
    private final RecyclerDataSource dataSource;

    @Inject
    TopGamesPresenter(
            TopGamesViewModel viewModel,
            GameRepository gameRepository,
            ScreenNavigator screenNavigator,
            @ForScreen DisposableManager disposableManager,
            RecyclerDataSource dataSource) {
        this.viewModel = viewModel;
        this.gameRepository = gameRepository;
        this.screenNavigator = screenNavigator;
        this.disposableManager = disposableManager;
        this.dataSource = dataSource;
        loadTopGames();
    }

    private void loadTopGames() {
        disposableManager.add(
                gameRepository.getTopGames()
                        .doOnSubscribe(__ -> viewModel.loadingUpdated().accept(true))
                        .doOnEvent((d, t) -> viewModel.loadingUpdated().accept(false))
                        .doOnSuccess(__ -> viewModel.topGamesUpdated().run())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(dataSource::setData, viewModel.onError())
        );
    }

    void onTopGameClicked(TwitchGame game) {
        screenNavigator.goToGameDetails(game.id(), game.name());
    }
}
