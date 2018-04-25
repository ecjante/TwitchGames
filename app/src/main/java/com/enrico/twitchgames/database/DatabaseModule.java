package com.enrico.twitchgames.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by enrico.
 */
@Module
public abstract class DatabaseModule {

    @Provides
    @Singleton
    static AppDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "twitchgames-database").build();
    }
}
