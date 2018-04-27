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
 *
 * Presenter for Game details screen
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
                // Make call to repository to get the specified twitch game
                repository.getGameInfo(twitchGameId, gameName)
                        .doOnError(viewModel.detailsError())
                        .doOnSuccess(game -> {
                            screenshotsDataSource.setData(game.getScreenshots());
                            videosDataSource.setData(game.getVideos());
                        })
                        .subscribe(viewModel.processIgdbGame(), throwable -> {

                        }),
                // make call to repository to get the streams for the game
                repository.getStreams(twitchGameId, gameName)
                        .doOnError(viewModel.streamsError())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(streamsDataSource::setData)
                        .subscribe(viewModel.streamsLoaded(), throwable -> {

                        })
        );
    }

    /**
     * make call to screen navigator to handle opening a stream
     * @param stream
     */
    public void onStreamClicked(TwitchStream stream) {
        screenNavigator.openStream(stream);
    }

    /**
     * make call to screen navigator to handle going to screenshot screen
     * @param url
     */
    public void onImageClicked(String url) {
        screenNavigator.openScreenshot(url);
    }

    /**
     * make call to screen navigator to handle going to QuickPlay activity
     * @param videoId
     */
    public void onVideoClicked(String videoId) {
        screenNavigator.playVideo(videoId);
    }
}
