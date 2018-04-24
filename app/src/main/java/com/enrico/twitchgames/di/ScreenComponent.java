package com.enrico.twitchgames.di;

import com.enrico.twitchgames.lifecycle.DisposableManager;

import dagger.android.AndroidInjector;

/**
 * Created by enrico.
 */
public interface ScreenComponent<T> extends AndroidInjector<T> {

    @ForScreen
    DisposableManager disposableManager();
}
