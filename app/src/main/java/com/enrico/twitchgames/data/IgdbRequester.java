package com.enrico.twitchgames.data;

import android.content.Context;

import com.enrico.twitchgames.models.igdb.IRLGame;
import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.squareup.moshi.Moshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by enrico.
 */
public class IgdbRequester {

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

    Single<IgdbGame> getGameInfo(String query) {
        if (!query.equals(IRL)) {
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        context.getAssets().open("get_igdb_game.json")
                ));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                IgdbGame game = moshi.adapter(IgdbGame.class).fromJson(sb.toString());
                return Single.just(game);
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not read from assets at: get_igdb_game.json");
            }
//            return service.getGame(query)
//                    .filter(list -> !list.isEmpty())
//                    .map(list -> list.get(0))
//                    .toSingle();
        } else {
            return Single.just(new IRLGame());
        }
    }
}
