package com.enrico.twitchgames.base;

import com.enrico.twitchgames.data.GameRepository;
import com.enrico.twitchgames.data.TestGameServiceModule;
import com.enrico.twitchgames.data.TestIgdbService;
import com.enrico.twitchgames.data.TestTwitchService;
import com.enrico.twitchgames.database.DatabaseModule;
import com.enrico.twitchgames.database.TestDatabaseModule;
import com.enrico.twitchgames.networking.ServiceModule;
import com.enrico.twitchgames.topgames.TopGamesControllerTest;
import com.enrico.twitchgames.ui.TestActivityViewInterceptorModule;
import com.enrico.twitchgames.ui.TestNavigationModule;
import com.enrico.twitchgames.ui.TestScreenNavigator;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by enrico.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        TestActivityBindingModule.class,
        TestGameServiceModule.class,
        ServiceModule.class,
        TestNavigationModule.class,
        TestActivityViewInterceptorModule.class,
        TestDatabaseModule.class,
})
public interface TestAppComponent extends AppComponent {

    void inject(TopGamesControllerTest topGamesControllerTest);

    TestScreenNavigator screenNavigator();

    TestTwitchService twitchService();

    TestIgdbService igdbService();

    GameRepository gameRepository();
}
