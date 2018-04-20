package com.enrico.twitchgames.base;

import android.support.test.InstrumentationRegistry;

/**
 * Created by enrico.
 */
public class TestApp extends App {

    @Override
    protected AppComponent initComponent() {
        return DaggerTestAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static TestAppComponent getComponent() {
        return (TestAppComponent) ((TestApp) InstrumentationRegistry.getTargetContext().getApplicationContext()).appComponent;
    }
}
