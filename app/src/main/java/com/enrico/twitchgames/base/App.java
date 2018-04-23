package com.enrico.twitchgames.base;

import android.app.Application;

import com.enrico.twitchgames.BuildConfig;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.di.ActivityInjector;
import com.jakewharton.threetenabp.AndroidThreeTen;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by enrico.
 */
public class App extends Application {

    @Inject ActivityInjector activityInjector;

    protected AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = initComponent();
        appComponent.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        AndroidThreeTen.init(this);
    }

    protected AppComponent initComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ActivityInjector getActivityInjector() {
        return activityInjector;
    }
}
