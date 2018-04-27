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
 *
 * Database service for IGDB DAO
 */
@Singleton
public class DbIgdbGameService extends DbService {

    private final AppDatabase appDatabase;

    @Inject
    DbIgdbGameService(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    /**
     * Get game if it exists given the id
     * @param id
     * @return
     */
    public Maybe<IgdbGame> getIgdbGame(long id) {
        return appDatabase.dbIgdbGameDao().findById(id)
                .map(IgdbGame::fromDb);
    }

    /**
     * Add IGDB game to the database to alleviate making calls to the API
     * @param game
     */
    public void addIgdbGame(IgdbGame game) {
        runDbOp(() -> appDatabase.dbIgdbGameDao().addIgdbGame(DbIgdbGame.build(game)));
    }

    /**
     * Delete the IGDB game from the database. Currently not used anywhere
     * @param game
     */
    public void deleteIgdbGame(IgdbGame game) {
        runDbOp(() -> appDatabase.dbIgdbGameDao().deleteIgdbGame(DbIgdbGame.build(game)));
    }
}
