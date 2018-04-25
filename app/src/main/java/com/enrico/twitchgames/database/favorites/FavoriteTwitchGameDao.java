package com.enrico.twitchgames.database.favorites;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by enrico.
 */
@Dao
public interface FavoriteTwitchGameDao {

    @Query("SELECT * from favoritetwitchgame")
    Flowable<List<FavoriteTwitchGame>> getFavoritedTwitchGames();

    @Insert
    void addFavorite(FavoriteTwitchGame game);

    @Delete
    void deleteFavorite(FavoriteTwitchGame game);
}
