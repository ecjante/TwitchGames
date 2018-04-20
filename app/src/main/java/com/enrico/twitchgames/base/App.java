package com.enrico.twitchgames.base;

import android.app.Application;

import com.enrico.twitchgames.BuildConfig;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.di.ActivityInjector;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by enrico.
 */
public class App extends Application {

    @Inject ActivityInjector activityInjector;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public ActivityInjector getActivityInjector() {
        return activityInjector;
    }
}
