package com.enrico.twitchgames.base;

import com.enrico.twitchgames.data.GameServiceModule;
import com.enrico.twitchgames.database.DatabaseModule;
import com.enrico.twitchgames.networking.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by enrico.
 *
 * Top level component
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ActivityBindingModule.class,
        ServiceModule.class,
        GameServiceModule.class,
        DatabaseModule.class,
})
public interface AppComponent {

    void inject(App app);
}
