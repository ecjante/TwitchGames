package com.enrico.twitchgames.details;

import com.enrico.twitchgames.data.GameRepository;
import com.enrico.twitchgames.di.ScreenScope;
import com.enrico.twitchgames.models.twitch.TwitchStream;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by enrico.
 */
@ScreenScope
public class GameDetailsPresenter implements StreamsAdapter.StreamClickedListener {

    @Inject
    GameDetailsPresenter(
        @Named("twitch_game_id") long twitchGameId,
        @Named("game_name") String gameName,
        GameRepository repository,
        GameDetailsViewModel viewModel
    ) {
        repository.getGameInfo(twitchGameId, gameName)
                .doOnSuccess(viewModel.processIgdbGame())
                .doOnError(viewModel.detailsError())
                .flatMap(igdbGame -> repository.getStreams(twitchGameId, gameName)
                        .doOnError(viewModel.streamsError()))
                .subscribe(viewModel.processStreams(), throwable -> {
                    // Handle logging in view model
                });
    }

    @Override
    public void onStreamClicked(TwitchStream stream) {
        // TODO
    }
}
