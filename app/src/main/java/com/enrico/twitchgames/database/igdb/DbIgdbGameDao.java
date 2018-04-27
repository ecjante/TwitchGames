package com.enrico.twitchgames.database.igdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import io.reactivex.Maybe;

/**
 * Created by enrico.
 *
 * DAO for IGDB games
 */
@Dao
public interface DbIgdbGameDao {

    /**
     * Get IGDB game given the id
     * @param id
     * @return
     */
    @Query("SELECT * FROM dbigdbgame WHERE id = :id LIMIT 1")
    Maybe<DbIgdbGame> findById(long id);

    /**
     * ADd the IGDB game to the database
     * @param game
     */
    @Insert
    void addIgdbGame(DbIgdbGame game);

    /**
     * Remove the IGDB game from the database
     * @param game
     */
    @Delete
    void deleteIgdbGame(DbIgdbGame game);
}
