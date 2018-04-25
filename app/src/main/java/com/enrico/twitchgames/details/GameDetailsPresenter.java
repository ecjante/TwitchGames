package com.enrico.twitchgames.details;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.enrico.poweradapter.adapter.RecyclerDataSource;
import com.enrico.twitchgames.data.GameRepository;
import com.enrico.twitchgames.di.ForScreen;
import com.enrico.twitchgames.di.ScreenScope;
import com.enrico.twitchgames.lifecycle.DisposableManager;
import com.enrico.twitchgames.models.twitch.TwitchStream;
import com.enrico.twitchgames.ui.ScreenNavigator;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by enrico.
 */
@ScreenScope
public class GameDetailsPresenter {

    private final ScreenNavigator screenNavigator;

    @Inject
    GameDetailsPresenter(
            @Named("twitch_game_id") long twitchGameId,
            @Named("game_name") String gameName,
            GameRepository repository,
            GameDetailsViewModel viewModel,
            ScreenNavigator screenNavigator,
            @ForScreen DisposableManager disposableManager,
            RecyclerDataSource dataSource) {
        this.screenNavigator = screenNavigator;
        disposableManager.add(
                repository.getGameInfo(twitchGameId, gameName)
                        .doOnError(viewModel.detailsError())
                        .subscribe(viewModel.processIgdbGame(), throwable -> {

                        }),
                repository.getStreams(twitchGameId, gameName)
                        .doOnError(viewModel.streamsError())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(dataSource::setData)
                        .subscribe(viewModel.streamsLoaded(), throwable -> {

                        })
        );
    }

    public void onStreamClicked(TwitchStream stream) {
        screenNavigator.openStream(stream);
    }
}
