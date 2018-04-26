package com.enrico.twitchgames.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.enrico.twitchgames.database.favorites.FavoriteTwitchGame;
import com.enrico.twitchgames.database.favorites.FavoriteTwitchGameDao;
import com.enrico.twitchgames.database.igdb.Converters;
import com.enrico.twitchgames.database.igdb.DbIgdbGame;
import com.enrico.twitchgames.database.igdb.DbIgdbGameDao;

/**
 * Created by enrico.
 */
@Database(
        entities = {
                FavoriteTwitchGame.class,
                DbIgdbGame.class
        }, version = 1
)
@TypeConverters({
        Converters.class,
})
public abstract class AppDatabase extends RoomDatabase {

    public abstract FavoriteTwitchGameDao favoriteTwitchGameDao();

    public abstract DbIgdbGameDao dbIgdbGameDao();
}
