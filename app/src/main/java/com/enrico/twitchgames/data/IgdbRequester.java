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
 */
public class IgdbRequester {

    private static final long IRL_ID = 494717;
    private static final long CREATIVE_ID = 488191;
    private static final String IRL = "IRL";

    private final IgdbService service;
    private final Moshi moshi;
    private final Context context;

    @Inject
    IgdbRequester(IgdbService service, Moshi moshi, Context context) {
        this.service = service;
        this.moshi = moshi;
        this.context = context;
    }

    Single<IgdbGame> getGameInfo(long id, String query) {
        if (id == IRL_ID) {
            return Single.just(new IRLGame());
        } else if (id == CREATIVE_ID) {
            return Single.just(new CreativeGame());
        } else {
            return service.getGame(query)
                    .map(list -> {
                        if (list.isEmpty()) {
                            return new NoInfoGame();
                        } else {
                            return list.get(0);
                        }
                    });
        }
    }
}
