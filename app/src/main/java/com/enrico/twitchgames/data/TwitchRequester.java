package com.enrico.twitchgames.data;

import com.enrico.twitchgames.data.responses.TwitchStreamsResponse;
import com.enrico.twitchgames.data.responses.TwitchTopGamesResponse;
import com.enrico.twitchgames.models.twitch.TwitchStream;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;

import java.util.Collections;
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
    private static final int MAX_CALL_COUNT = 3;

    private final TwitchService service;
    private static TwitchTopGamesResponse response;
    private static TwitchStreamsResponse streamsResponse;
    private static int nextTopGamesCallCount = 0;

    @Inject
    TwitchRequester(TwitchService service) {
        this.service = service;
    }

    Single<List<TwitchTopGame>> getTopGames() {
        return service.getTopGames(DEFAULT_LIMIT, DEFAULT_OFFSET)
                .doOnSuccess(response -> this.response = response)
                .map(TwitchTopGamesResponse::games);
    }

    Single<List<TwitchTopGame>> getNextTopGames() {
        int offset = response != null && response.links().nextOffset() != null
                ? response.links().nextOffset()
                :  DEFAULT_OFFSET;
        if (nextTopGamesCallCount < MAX_CALL_COUNT) {
            return service.getTopGames(DEFAULT_LIMIT, offset)
                    .doOnSuccess(response -> {
                        nextTopGamesCallCount++;
                        this.response = response;
                    })
                    .map(TwitchTopGamesResponse::games);
        } else {
            return Single.just(Collections.emptyList());
        }
    }

    Single<List<TwitchStream>> getStreams(String game) {
        return service.getStreams(game, DEFAULT_LIMIT, DEFAULT_OFFSET)
                .doOnSuccess(response -> this.streamsResponse = response)
                .map(TwitchStreamsResponse::streams);
    }

    Single<List<TwitchStream>> getNextStreams(String game) {
        int offset = streamsResponse != null && streamsResponse.links().nextOffset() != null
                ? streamsResponse.links().nextOffset()
                : DEFAULT_OFFSET;
        return service.getStreams(game, DEFAULT_LIMIT, offset)
                .doOnSuccess(response -> this.streamsResponse = response)
                .map(TwitchStreamsResponse::streams);
    }

    void clearStreamsResponse() {
        streamsResponse = null;
    }

    void clearCallCounts() {
        nextTopGamesCallCount = 0;
    }
}
