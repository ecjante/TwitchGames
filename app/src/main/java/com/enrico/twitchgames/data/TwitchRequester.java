package com.enrico.twitchgames.data;

import com.enrico.twitchgames.models.twitch.TwitchTopGame;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by enrico.
 */
public class TwitchRequester {

    private static final int DEFAULT_LIMIT = 25;
    private static final int DEFAULT_OFFSET = 0;

    private final TwitchService service;
    private TwitchTopGamesResponse response;

    @Inject
    TwitchRequester(TwitchService service) {
        this.service = service;
    }

    public Single<List<TwitchTopGame>> getTopGames() {
        return service.getTopGames(DEFAULT_LIMIT, DEFAULT_OFFSET)
                .doOnSuccess(response -> this.response = response)
                .map(TwitchTopGamesResponse::games)
                .subscribeOn(Schedulers.io());
    }

    public Single<List<TwitchTopGame>> getNextTopGames() {
        int offset = response != null ? response.links().nextOffset() :  DEFAULT_OFFSET;
        return service.getTopGames(DEFAULT_LIMIT, offset)
                .doOnSuccess(response -> this.response = response)
                .map(TwitchTopGamesResponse::games)
                .subscribeOn(Schedulers.io());
    }
}
