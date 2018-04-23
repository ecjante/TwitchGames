package com.enrico.twitchgames.data;

import android.content.Context;

import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.squareup.moshi.Moshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by enrico.
 */
public class MockIgdbService implements IgdbService {

    private static final String FORTNITE = "Fortnite";
    private static final String FORTNITE_JSON = "fortnite.json";
    private static final String GOD_OF_WAR = "God of War";
    private static final String GOD_OF_WAR_JSON = "god_of_war.json";
    private static final String LEAGUE_OF_LEGENDS = "League of Legends";
    private static final String LEAGUE_OF_LEGENDS_JSON = "league_of_legends.json";

    private final Context context;
    private final Moshi moshi;

    @Inject
    MockIgdbService(Context context, Moshi moshi) {
        this.context = context;
        this.moshi = moshi;
    }

    @Override
    public Single<List<IgdbGame>> getGame(String searchQuery) {
        String fileName;
        switch (searchQuery) {
            case FORTNITE:
                fileName = FORTNITE_JSON;
                break;
            case GOD_OF_WAR:
                fileName = GOD_OF_WAR_JSON;
                break;
            case LEAGUE_OF_LEGENDS:
                fileName = LEAGUE_OF_LEGENDS_JSON;
                break;
            default: fileName = FORTNITE_JSON;
        }
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    context.getAssets().open(fileName)
            ));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            IgdbGame game = moshi.adapter(IgdbGame.class).fromJson(sb.toString());
            List<IgdbGame> list = new ArrayList<>();
            list.add(game);
            return Single.just(list);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read from assets at: " + fileName);
        }
    }
}
