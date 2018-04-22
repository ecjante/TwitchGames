package com.enrico.twitchgames.networking;

import com.enrico.twitchgames.models.AdapterFactory;
import com.enrico.twitchgames.models.igdb.IgdbEsrbAdapter;
import com.enrico.twitchgames.models.igdb.IgdbPegiAdapter;
import com.enrico.twitchgames.models.igdb.IgdbWebsiteAdapter;
import com.enrico.twitchgames.models.igdb.ZonedDateTimeAdapter;
import com.enrico.twitchgames.models.twitch.TwitchTopGamesLinkAdapter;
import com.squareup.moshi.Moshi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by enrico.
 */
@Module(includes = NetworkModule.class)
public abstract class ServiceModule {

    @Provides
    @Singleton
    static Moshi provideMoshi() {
        return new Moshi.Builder()
                .add(AdapterFactory.create())
                .add(new ZonedDateTimeAdapter())
                .add(new TwitchTopGamesLinkAdapter())
                .add(new IgdbPegiAdapter())
                .add(new IgdbEsrbAdapter())
                .add(new IgdbWebsiteAdapter())
                .build();
    }

    @Provides
    @Singleton
    @Named("twitch_retrofit")
    static Retrofit provideTwitchRetrofit(
            Moshi moshi,
            @Named("twitch_call_factory") Call.Factory callFactory,
            @Named("twitch_base_url") String baseUrl
    ) {
        return new Retrofit.Builder()
                .callFactory(callFactory)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    @Provides
    @Singleton
    @Named("igdb_retrofit")
    static Retrofit provideIGDBRetrofit(
            Moshi moshi,
            @Named("igdb_call_factory") Call.Factory callFactory,
            @Named("igdb_base_url") String baseUrl
    ) {
        return new Retrofit.Builder()
                .callFactory(callFactory)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }
}
