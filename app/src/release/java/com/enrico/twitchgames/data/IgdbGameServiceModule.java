package com.enrico.twitchgames.data;

import android.content.Context;

import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by enrico.
 */
@Module
public abstract class IgdbGameServiceModule {

    @Provides
    @Singleton
    static IgdbService provideIGDBService(@Named("igdb_retrofit") Retrofit retrofit) {
        return retrofit.create(IgdbService.class);
    }
}
