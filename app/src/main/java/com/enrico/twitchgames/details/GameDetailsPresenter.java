package com.enrico.twitchgames.details;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

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

    private final Context context;

    @Inject
    GameDetailsPresenter(
        Context context,
        @Named("twitch_game_id") long twitchGameId,
        @Named("game_name") String gameName,
        GameRepository repository,
        GameDetailsViewModel viewModel
    ) {
        this.context = context;
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
        if (isPackageInstalled()) {
            Uri uri = buildUri(stream);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } else {
            Uri url = buildUrl(stream);
            Intent intent = new Intent(Intent.ACTION_VIEW, url);
            context.startActivity(intent);
        }
    }

    private Uri buildUri(TwitchStream stream) {
        return new Uri.Builder().scheme("twitch")
                .authority("stream")
                .path(stream.channel().name())
                .build();
    }

    private Uri buildUrl(TwitchStream stream) {
        return new Uri.Builder().scheme("https")
                .authority("www.twitch.tv")
                .path(stream.channel().name())
                .build();
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
