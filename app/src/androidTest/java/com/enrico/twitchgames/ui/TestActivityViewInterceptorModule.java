package com.enrico.twitchgames.ui;

import dagger.Module;
import dagger.Provides;

/**
 * Created by enrico.
 */
@Module
public abstract class TestActivityViewInterceptorModule {

    @Provides
    static ActivityViewInterceptor provideActivityViewInterceptor() {
        return ActivityViewInterceptor.DEFAULT;
    }
}
