package com.enrico.twitchgames.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.Router;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.di.Injector;
import com.enrico.twitchgames.di.ScreenInjector;
import com.enrico.twitchgames.lifecycle.ActivityLifecycleTask;
import com.enrico.twitchgames.ui.ActivityViewInterceptor;
import com.enrico.twitchgames.ui.RouterProvider;
import com.enrico.twitchgames.ui.ScreenNavigator;
import com.google.android.youtube.player.YouTubeBaseActivity;

import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by enrico.
 * BaseActivity default behavior of Activities in app
 */
public abstract class BaseActivity extends AppCompatActivity implements RouterProvider {

    private static String INSTANCE_ID_KEY = "instance_id";

    @Inject ScreenInjector screenInjector;
    @Inject ScreenNavigator screenNavigator;
    @Inject ActivityViewInterceptor activityViewInterceptor;
    @Inject Set<ActivityLifecycleTask> activityLifecycleTasks;

    private String instanceId;
    private Router router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            instanceId = savedInstanceState.getString(INSTANCE_ID_KEY);
        } else {
            instanceId = UUID.randomUUID().toString();
        }
        // Inject the activity
        Injector.inject(this);

        // Set content view for activity with the layoutRes from subclass
        activityViewInterceptor.setContentView(this, layoutRes());

        // Set up conductor
        ViewGroup screenContainer = findViewById(R.id.screen_container);
        if (screenContainer == null)
            throw new NullPointerException("Activity must have a view with id: screen_container");

        // Initialize router
        router = Conductor.attachRouter(this, screenContainer, savedInstanceState);
        monitorBackStack();
        for (ActivityLifecycleTask task : activityLifecycleTasks) {
            task.onCreate(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (ActivityLifecycleTask task : activityLifecycleTasks) {
            task.onStart(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (ActivityLifecycleTask task : activityLifecycleTasks) {
            task.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (ActivityLifecycleTask task : activityLifecycleTasks) {
            task.onPause(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (ActivityLifecycleTask task : activityLifecycleTasks) {
            task.onStop(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(INSTANCE_ID_KEY, instanceId);
    }

    @Override
    public void onBackPressed() {
        // only call super if screen navigator did not pop
        if (!screenNavigator.pop()) {
            super.onBackPressed();
        }
    }

    @Override
    public Router getRouter() {
        return router;
    }

    @LayoutRes
    protected abstract int layoutRes();

    /**
     * Subclasses will provide it's root controller
     * @return
     */
    public abstract Controller initialScreen();

    public String getInstanceId() {
        return instanceId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clear the activity from scope if finishing
        if (isFinishing()) {
            Injector.clearComponent(this);
        }
        activityViewInterceptor.clear();
        for (ActivityLifecycleTask task : activityLifecycleTasks) {
            task.onDestroy(this);
        }
    }

    /**
     * Returns injected screen injector
     * @return
     */
    public ScreenInjector getScreenInjector() {
        return screenInjector;
    }

    /**
     * Monitors the back stack. Mainly used to call {@link Injector#clearComponent(Controller)} when
     * controller won't be used anymore
     */
    private void monitorBackStack() {
        router.addChangeListener(new ControllerChangeHandler.ControllerChangeListener() {
            @Override
            public void onChangeStarted(
                    @Nullable Controller to,
                    @Nullable Controller from,
                    boolean isPush,
                    @NonNull ViewGroup container,
                    @NonNull ControllerChangeHandler handler) {

            }

            @Override
            public void onChangeCompleted(
                    @Nullable Controller to,
                    @Nullable Controller from,
                    boolean isPush,
                    @NonNull ViewGroup container,
                    @NonNull ControllerChangeHandler handler) {
                if (!isPush && from != null) {
                    Injector.clearComponent(from);
                }
            }
        });
    }
}
