package com.enrico.twitchgames.ui;

import dagger.Module;
import dagger.Provides;

/**
 * Created by enrico on 3/14/18.
 */

@Module
public abstract class ActivityViewInterceptorModule {

    @Provides
    static ActivityViewInterceptor provideActivityViewInterceptor() {
        return ActivityViewInterceptor.DEFAULT;
    }
}
