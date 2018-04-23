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
    static IgdbService provideIGDBService(Context context, Moshi moshi) {
        return new MockIgdbService(context, moshi);
    }
}
