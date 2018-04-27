package com.enrico.twitchgames.data;

import android.content.Context;

import com.enrico.twitchgames.models.igdb.twitchonlygames.CreativeGame;
import com.enrico.twitchgames.models.igdb.twitchonlygames.IRLGame;
import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.enrico.twitchgames.models.igdb.NoInfoGame;
import com.squareup.moshi.Moshi;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by enrico.
 *
 * Uses the IGDB service to make API calls
 */
public class IgdbRequester {

    private final IgdbService service;
    private final Moshi moshi;
    private final Context context;

    @Inject
    IgdbRequester(IgdbService service, Moshi moshi, Context context) {
        this.service = service;
        this.moshi = moshi;
        this.context = context;
    }

    /**
     * Get game info given the query. Store the twitch id in the game info and return it.
     * If there is no game returned then return the NoInfoGame
     * @param id
     * @param query
     * @return
     */
    Single<IgdbGame> getGameInfo(long id, String query) {
        return service.getGame(query)
                .map(list -> {
                    if (list.isEmpty()) {
                        return new NoInfoGame(id);
                    } else {
                        return list.get(0).withTwitchId(id);
                    }
                });
    }
}
