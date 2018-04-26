package com.enrico.twitchgames.database.igdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import io.reactivex.Maybe;

/**
 * Created by enrico.
 */
@Dao
public interface DbIgdbGameDao {

    @Query("SELECT * FROM dbigdbgame WHERE id = :id LIMIT 1")
    Maybe<DbIgdbGame> findById(long id);

    @Insert
    void addIgdbGame(DbIgdbGame game);

    @Delete
    void deleteIgdbGame(DbIgdbGame game);
}
