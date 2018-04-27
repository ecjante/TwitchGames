package com.enrico.twitchgames.topgames;

import com.enrico.poweradapter.adapter.RecyclerDataSource;
import com.enrico.poweradapter.item.ItemRenderer;
import com.enrico.poweradapter.item.RecyclerItem;
import com.enrico.poweradapter.item.RenderKey;
import com.enrico.twitchgames.di.ScreenScope;
import com.enrico.twitchgames.lifecycle.ScreenLifecycleTask;

import java.util.Map;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;

/**
 * Created by enrico.
 *
 * Provides the TopGamesUiManager
 * Provides the ItemRenderer and RecyclerDataSource for the recycler view
 */
@Module
public abstract class TopGamesScreenModule {

    @Binds
    @IntoSet
    abstract ScreenLifecycleTask bindUiManagerTask(TopGamesUiManager uiManager);

    @Binds
    @IntoMap
    @RenderKey("TwitchTopGame")
    abstract ItemRenderer<? extends RecyclerItem> bindTopGameRenderer(TopGamesRenderer topGamesRenderer);

    @Provides
    @ScreenScope
    static RecyclerDataSource provideRecyclerDataSource(Map<String, ItemRenderer<? extends RecyclerItem>> renderers) {
        return new RecyclerDataSource(renderers);
    }
}
