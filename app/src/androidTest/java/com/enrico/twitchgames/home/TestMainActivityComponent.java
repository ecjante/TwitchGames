package com.enrico.twitchgames.home;

import com.enrico.twitchgames.di.ActivityScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by enrico.
 */
@ActivityScope
@Subcomponent(modules = {
        TestScreenBindingModule.class,
})
public interface TestMainActivityComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {

        @Override
        public void seedInstance(MainActivity instance) {

        }
    }
}
