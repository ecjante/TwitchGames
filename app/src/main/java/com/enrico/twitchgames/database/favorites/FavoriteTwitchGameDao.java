package com.enrico.twitchgames.database.favorites;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by enrico.
 *
 * DAO for favorited twitch games
 */
@Dao
public interface FavoriteTwitchGameDao {

    /**
     * Get twitch game ids
     * @return
     */
    @Query("SELECT id FROM favoritetwitchgame")
    Flowable<List<Long>> getFavoritedTwitchGameIds();

    /**
     * Get twitch game models
     * @return
     */
    @Query("SELECT * from favoritetwitchgame")
    Single<List<FavoriteTwitchGame>> getFavoritedTwitchGames();

    /**
     * Add a favorited game
     * @param game
     */
    @Insert
    void addFavorite(FavoriteTwitchGame game);

    /**
     * Remove a favorited game
     * @param game
     */
    @Delete
    void deleteFavorite(FavoriteTwitchGame game);
}
