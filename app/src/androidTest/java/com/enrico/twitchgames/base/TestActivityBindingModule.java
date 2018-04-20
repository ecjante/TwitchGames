package com.enrico.twitchgames.base;

import android.app.Activity;

import com.enrico.twitchgames.home.MainActivity;
import com.enrico.twitchgames.home.TestMainActivityComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by enrico.
 */
@Module(subcomponents = {
        TestMainActivityComponent.class
})
public abstract class TestActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> provideMainActivityInjector(TestMainActivityComponent.Builder builder);
}
