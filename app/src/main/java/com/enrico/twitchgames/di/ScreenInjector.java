package com.enrico.twitchgames.di;

import android.app.Activity;

import com.bluelinelabs.conductor.Controller;
import com.enrico.twitchgames.base.BaseActivity;
import com.enrico.twitchgames.base.BaseController;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.AndroidInjector;

/**
 * Created by enrico.
 *
 * Caches controller injectors
 * Helps retains states of controllers
 */
@ActivityScope
public class ScreenInjector {

    private final Map<Class<? extends Controller>, Provider<AndroidInjector.Factory<? extends Controller>>> screenInjectors;
    private final Map<String, AndroidInjector<Controller>> cache = new HashMap<>();

    @Inject
    ScreenInjector(Map<Class<? extends Controller>, Provider<AndroidInjector.Factory<? extends Controller>>> screenInjectors) {
        this.screenInjectors = screenInjectors;
    }

    /**
     * Inject controller. Checks cache if injector for instanceId already exists and uses that injector
     * to inject the activity. Otherwise creates a new Injector and adds it to the cache
     * @param controller
     */
    void inject(Controller controller) {
        if (!(controller instanceof BaseController))
            throw new IllegalArgumentException("Controller must extend BaseController");

        String instanceId = controller.getInstanceId();
        if (cache.containsKey(instanceId)) {
            cache.get(instanceId).inject(controller);
            return;
        }

        //noinspection unchecked
        AndroidInjector.Factory<Controller> injectorFactory = ((AndroidInjector.Factory<Controller>) screenInjectors.get(controller.getClass()).get());
        AndroidInjector<Controller> injector = injectorFactory.create(controller);
        cache.put(instanceId, injector);
        injector.inject(controller);
    }

    /**
     * Clear the injector from the cache
     * @param controller
     */
    void clear(Controller controller) {
        if (!(controller instanceof BaseController))
            throw new IllegalArgumentException("Controller must extend BaseController");

        AndroidInjector<?> injector = cache.remove(controller.getInstanceId());
        if (injector instanceof ScreenComponent) {
            ((ScreenComponent) injector).disposableManager().dispose();
        }
    }

    /**
     * Gets the screen injector
     * @param activity
     * @return
     */
    static ScreenInjector get(Activity activity) {
        if (!(activity instanceof BaseActivity))
            throw new IllegalArgumentException("Activity must extend BaseActivity");

        return ((BaseActivity) activity).getScreenInjector();
    }
}
