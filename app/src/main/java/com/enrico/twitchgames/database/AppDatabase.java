package com.enrico.twitchgames.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.enrico.twitchgames.database.favorites.FavoriteTwitchGame;
import com.enrico.twitchgames.database.favorites.FavoriteTwitchGameDao;

/**
 * Created by enrico.
 */
@Database(
        entities = {
            FavoriteTwitchGame.class
        }, version = 1
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FavoriteTwitchGameDao favoriteTwitchGameDao();
}
