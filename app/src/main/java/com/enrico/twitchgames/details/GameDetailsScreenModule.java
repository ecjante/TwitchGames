package com.enrico.twitchgames.details;

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
 */
@Module
public abstract class GameDetailsScreenModule {

    @Binds
    @IntoSet
    abstract ScreenLifecycleTask bindUiManagerTask(GameDetailsUiManager uiManager);

    @Binds
    @IntoMap
    @RenderKey("TwitchStream")
    abstract ItemRenderer<? extends RecyclerItem> bindTopGameRenderer(StreamRender streamRenderer);

    @Provides
    @ScreenScope
    static RecyclerDataSource provideRecyclerDataSource(Map<String, ItemRenderer<? extends RecyclerItem>> renderers) {
        return new RecyclerDataSource(renderers);
    }
}
