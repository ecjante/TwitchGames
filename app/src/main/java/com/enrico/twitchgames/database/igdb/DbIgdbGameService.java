package com.enrico.twitchgames.database.igdb;

import com.enrico.twitchgames.database.AppDatabase;
import com.enrico.twitchgames.database.DbService;
import com.enrico.twitchgames.models.igdb.IgdbGame;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by enrico.
 */
@Singleton
public class DbIgdbGameService extends DbService {

    private final AppDatabase appDatabase;

    @Inject
    DbIgdbGameService(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    public Maybe<IgdbGame> getIgdbGame(long id) {
        return appDatabase.dbIgdbGameDao().findById(id)
                .map(IgdbGame::fromDb);
    }

    public void addIgdbGame(IgdbGame game) {
        runDbOp(() -> appDatabase.dbIgdbGameDao().addIgdbGame(DbIgdbGame.build(game)));
    }

    public void deleteIgdbGame(IgdbGame game) {
        runDbOp(() -> appDatabase.dbIgdbGameDao().deleteIgdbGame(DbIgdbGame.build(game)));
    }
}
