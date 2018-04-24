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

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by enrico.
 */
@ScreenScope
public class GameDetailsPresenter {

    private final Context context;

    @Inject
    GameDetailsPresenter(
            Context context,
            @Named("twitch_game_id") long twitchGameId,
            @Named("game_name") String gameName,
            GameRepository repository,
            GameDetailsViewModel viewModel,
            @ForScreen DisposableManager disposableManager,
            RecyclerDataSource dataSource) {
        this.context = context;
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
        Uri uri;
        if (isPackageInstalled()) {
            uri = Uri.parse("twitch://stream/" + stream.channel().name());
        } else {
            uri = Uri.parse("https://www.twitch.tv/" + stream.channel().name());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    private boolean isPackageInstalled() {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo("tv.twitch.android.app", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
