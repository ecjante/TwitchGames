package com.enrico.twitchgames.home;

import com.enrico.twitchgames.di.ActivityScope;
import com.enrico.twitchgames.ui.ActivityViewInterceptor;
import com.enrico.twitchgames.ui.ActivityViewInterceptorModule;
import com.enrico.twitchgames.ui.NavigationModule;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by enrico.
 * Activity level component
 * Uses AndroidInjector for the MainActivity
 */
@ActivityScope
@Subcomponent(modules = {
        MainScreenBindingModule.class,
        NavigationModule.class,
        ActivityViewInterceptorModule.class
})
public interface MainActivityComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {

        @Override
        public void seedInstance(MainActivity instance) {

        }
    }
}
