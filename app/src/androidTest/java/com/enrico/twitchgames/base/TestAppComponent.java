package com.enrico.twitchgames.base;

import com.enrico.twitchgames.data.TestTwitchServiceModule;
import com.enrico.twitchgames.networking.ServiceModule;
import com.enrico.twitchgames.topgames.TopGamesControllerTest;
import com.enrico.twitchgames.ui.NavigationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by enrico.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        TestActivityBindingModule.class,
        TestTwitchServiceModule.class,
        ServiceModule.class,
        NavigationModule.class,
})
public interface TestAppComponent extends AppComponent {
    void inject(TopGamesControllerTest topGamesControllerTest);
}
