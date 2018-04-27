package com.enrico.twitchgames.di;

import android.app.Activity;

import com.bluelinelabs.conductor.Controller;

/**
 * Created by enrico.
 */
public class Injector {

    private Injector() {}

    /**
     * Injects activities
     * @param activity
     */
    public static void inject(Activity activity) {
        ActivityInjector.get(activity).inject(activity);
    }

    /**
     * Clears activity component
     * @param activity
     */
    public static void clearComponent(Activity activity) {
        ActivityInjector.get(activity).clear(activity);
    }

    /**
     * Injects controllers
     * @param controller
     */
    public static void inject(Controller controller) {
        ScreenInjector.get(controller.getActivity()).inject(controller);
    }

    /**
     * Clears controller components
     * @param controller
     */
    public static void clearComponent(Controller controller) {
        ScreenInjector.get(controller.getActivity()).clear(controller);
    }
}
