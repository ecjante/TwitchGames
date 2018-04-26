package com.enrico.twitchgames.details;

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
            @Named("streams_datasource") RecyclerDataSource streamsDataSource,
            @Named("screenshots_datasource") RecyclerDataSource screenshotsDataSource,
            @Named("videos_datasource") RecyclerDataSource videosDataSource) {
        this.screenNavigator = screenNavigator;
        disposableManager.add(
                repository.getGameInfo(twitchGameId, gameName)
                        .doOnError(viewModel.detailsError())
                        .doOnSuccess(game -> {
                            screenshotsDataSource.setData(game.getScreenshots());
                            videosDataSource.setData(game.getVideos());
                        })
                        .subscribe(viewModel.processIgdbGame(), throwable -> {

                        }),
                repository.getStreams(twitchGameId, gameName)
                        .doOnError(viewModel.streamsError())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(streamsDataSource::setData)
                        .subscribe(viewModel.streamsLoaded(), throwable -> {

                        })
        );
    }

    public void onStreamClicked(TwitchStream stream) {
        screenNavigator.openStream(stream);
    }

    public void onScreenshotClicked(String url) {
        screenNavigator.openScreenshot(url);
    }

    public void onVideoClicked(String videoId) {
        screenNavigator.playVideo(videoId);
    }
}
