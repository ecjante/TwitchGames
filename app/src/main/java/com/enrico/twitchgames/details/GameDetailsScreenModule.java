package com.enrico.twitchgames.details;

import com.enrico.poweradapter.adapter.RecyclerDataSource;
import com.enrico.poweradapter.item.ItemRenderer;
import com.enrico.poweradapter.item.RecyclerItem;
import com.enrico.poweradapter.item.RenderKey;
import com.enrico.twitchgames.di.ScreenScope;
import com.enrico.twitchgames.lifecycle.ScreenLifecycleTask;

import java.util.Map;

import javax.inject.Named;

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
    abstract ItemRenderer<? extends RecyclerItem> bindTopGameRenderer(StreamRenderer streamRenderer);

    @Binds
    @IntoMap
    @RenderKey("IgdbGameScreenshot")
    abstract ItemRenderer<? extends RecyclerItem> bindIgdbGameScreenshotRenderer(IgdbGameScreenshotRenderer igdbGameScreenshotRenderer);
    @Binds
    @IntoMap
    @RenderKey("IgdbGameVideo")
    abstract ItemRenderer<? extends RecyclerItem> bindIgdbGameVideoRenderer(IgdbGameVideoRenderer igdbGameVideoRenderer);

    @Provides
    @ScreenScope
    @Named("streams_datasource")
    static RecyclerDataSource provideStreamsRecyclerDataSource(Map<String, ItemRenderer<? extends RecyclerItem>> renderers) {
        return new RecyclerDataSource(renderers);
    }

    @Provides
    @ScreenScope
    @Named("screenshots_datasource")
    static RecyclerDataSource provideScreenshotsRecyclerDataSource(Map<String, ItemRenderer<? extends RecyclerItem>> renderers) {
        return new RecyclerDataSource(renderers);
    }

    @Provides
    @ScreenScope
    @Named("videos_datasource")
    static RecyclerDataSource provideVideoRecyclerDataSource(Map<String, ItemRenderer<? extends RecyclerItem>> renderers) {
        return new RecyclerDataSource(renderers);
    }
}
