package com.enrico.twitchgames.screenshot;

import com.enrico.twitchgames.base.ScreenModule;
import com.enrico.twitchgames.di.ScreenComponent;
import com.enrico.twitchgames.di.ScreenScope;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by enrico.
 *
 * Component for the Screenshot screen
 */
@ScreenScope
@Subcomponent(modules = {
        ScreenModule.class,
        ScreenshotScreenModule.class
})
public interface ScreenshotComponent extends ScreenComponent<ScreenshotController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ScreenshotController> {

        // Provides the screenshot url to the screenshot controller
        @BindsInstance
        public abstract void bindScreenshot(@Named("screenshot") String screenshot);

        @Override
        public void seedInstance(ScreenshotController instance) {
            bindScreenshot(instance.getArgs().getString(ScreenshotController.SCREENSHOT_KEY));

        }
    }
}
