package com.enrico.twitchgames.topgames;

import com.enrico.twitchgames.data.GameRepository;
import com.enrico.twitchgames.data.TwitchRequester;
import com.enrico.twitchgames.di.ForScreen;
import com.enrico.twitchgames.di.ScreenScope;
import com.enrico.twitchgames.lifecycle.DisposableManager;
import com.enrico.twitchgames.models.twitch.TwitchGame;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;
import com.enrico.twitchgames.ui.ScreenNavigator;

import javax.inject.Inject;

/**
 * Created by enrico.
 */
@ScreenScope
class TopGamesPresenter implements TopGamesAdapter.TopGameClickedListener {

    private final TopGamesViewModel viewModel;
    private final GameRepository gameRepository;
    private final ScreenNavigator screenNavigator;
    private final DisposableManager disposableManager;

    @Inject
    TopGamesPresenter(
            TopGamesViewModel viewModel,
            GameRepository gameRepository,
            ScreenNavigator screenNavigator,
            @ForScreen DisposableManager disposableManager) {
        this.viewModel = viewModel;
        this.gameRepository = gameRepository;
        this.screenNavigator = screenNavigator;
        this.disposableManager = disposableManager;
        loadTopGames();
    }

    private void loadTopGames() {
        disposableManager.add(
                gameRepository.getTopGames()
                        .doOnSubscribe(__ -> viewModel.loadingUpdated().accept(true))
                        .doOnEvent((d, t) -> viewModel.loadingUpdated().accept(false))
                        .subscribe(viewModel.topGamesUpdated(), viewModel.onError())
        );
    }

    @Override
    public void onTopGameClicked(TwitchGame game) {
        screenNavigator.goToGameDetails(game.id(), game.name());
    }
}
