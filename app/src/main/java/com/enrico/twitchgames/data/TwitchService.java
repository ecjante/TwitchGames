package com.enrico.twitchgames.data;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by enrico.
 */
public interface TwitchService {

    @GET("games/top")
    Single<TwitchTopGamesResponse> getTopGames(@Query("limit") int limit, @Query("offset") int offset);
}
