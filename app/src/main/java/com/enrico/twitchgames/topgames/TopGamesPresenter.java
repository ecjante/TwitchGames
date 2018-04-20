package com.enrico.twitchgames.topgames;

import com.enrico.twitchgames.data.TwitchRequester;
import com.enrico.twitchgames.di.ScreenScope;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;

import javax.inject.Inject;

/**
 * Created by enrico.
 */
@ScreenScope
class TopGamesPresenter implements TopGamesAdapter.TopGameClickedListener {

    private final TopGamesViewModel viewModel;
    private final TwitchRequester twitchRequester;

    @Inject
    TopGamesPresenter(TopGamesViewModel viewModel, TwitchRequester twitchRequester) {
        this.viewModel = viewModel;
        this.twitchRequester = twitchRequester;
        loadTopGames();
    }

    private void loadTopGames() {
        twitchRequester.getTopGames()
                .doOnSubscribe(__ -> viewModel.loadingUpdated().accept(true))
                .doOnEvent((d, t) -> viewModel.loadingUpdated().accept(false))
                .subscribe(viewModel.topGamesUpdated(), viewModel.onError());
    }

    @Override
    public void onTopGameClicked(TwitchTopGame topGame) {

    }
}
