package com.enrico.twitchgames.base;

import com.enrico.twitchgames.data.TwitchServiceModule;
import com.enrico.twitchgames.networking.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by enrico.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ActivityBindingModule.class,
        ServiceModule.class,
        TwitchServiceModule.class,
})
public interface AppComponent {

    void inject(App app);
}
