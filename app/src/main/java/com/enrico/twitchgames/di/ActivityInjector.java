package com.enrico.twitchgames.di;

import android.app.Activity;
import android.content.Context;

import com.enrico.twitchgames.base.App;
import com.enrico.twitchgames.base.BaseActivity;
import com.enrico.twitchgames.home.MainActivityComponent;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.AndroidInjector;

/**
 * Created by enrico.
 *
 * Mimics DispatchingAndroidInjector
 * Caches injectors
 * Helps retain states across configuration change
 */
public class ActivityInjector {

    private final Map<Class<? extends Activity>, Provider<AndroidInjector.Factory<? extends Activity>>> activityInjectors;
    private final Map<String, AndroidInjector<? extends Activity>> cache = new HashMap<>();

    @Inject
    ActivityInjector(Map<Class<? extends Activity>, Provider<AndroidInjector.Factory<? extends Activity>>> activityInjectors) {
        this.activityInjectors = activityInjectors;
    }

    /**
     * Inject activity. Checks cache if injector for instanceId already exists and uses that injector
     * to inject the activity. Otherwise creates a new Injector and adds it to the cache
     * @param activity
     */
    void inject(Activity activity) {
        if (!(activity instanceof BaseActivity))
            throw new IllegalArgumentException("Activity must extend BaseActivity");

        String instanceId = ((BaseActivity) activity).getInstanceId();
        if (cache.containsKey(instanceId)) {
            //noinspection unchecked
            ((AndroidInjector<Activity>) cache.get(instanceId)).inject(activity);
            return;
        }

        //noinspection unchecked
        AndroidInjector.Factory<Activity> injectorFactory = ((AndroidInjector.Factory<Activity>) activityInjectors.get(activity.getClass()).get());
        AndroidInjector<Activity> injector = injectorFactory.create(activity);
        cache.put(instanceId, injector);
        injector.inject(activity);
    }

    /**
     * Clears the injector from the cache
     * @param activity
     */
    void clear(Activity activity) {
        if (!(activity instanceof BaseActivity))
            throw new IllegalArgumentException("Activity must extend BaseActivity");

        cache.remove(((BaseActivity) activity).getInstanceId());
    }

    /**
     * Get the ActivityInjector from outside
     * @param context
     * @return
     */
    static ActivityInjector get(Context context) {
        return ((App) context.getApplicationContext()).getActivityInjector();
    }
}
