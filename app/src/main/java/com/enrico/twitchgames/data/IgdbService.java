package com.enrico.twitchgames.data;

import com.enrico.twitchgames.models.igdb.IgdbGame;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by enrico.
 */
public interface IgdbService {

    @GET("games/?fields=id,name,summary,platforms,screenshots,videos,cover,esrb,pegi,websites&limit=1")
    Single<List<IgdbGame>> getGame(@Query("search") String searchQuery);
}
