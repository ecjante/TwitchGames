package com.enrico.twitchgames.data;

import com.enrico.twitchgames.data.responses.TwitchStreamsResponse;
import com.enrico.twitchgames.data.responses.TwitchTopGamesResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by enrico.
 */
public interface TwitchService {

    @GET("kraken/games/top")
    Single<TwitchTopGamesResponse> getTopGames(@Query("limit") int limit, @Query("offset") int offset);

    @GET("kraken/streams?stream_type=live")
    Single<TwitchStreamsResponse> getStreams(@Query("game") String game, @Query("limit") int limit, @Query("offset") int offset);
}
