package com.enrico.twitchgames.screenshot;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.enrico.twitchgames.R;
import com.enrico.twitchgames.lifecycle.ScreenLifecycleTask;
import com.enrico.twitchgames.ui.ScreenNavigator;
import com.enrico.twitchgames.util.ButterknifeUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by enrico.
 *
 * UiManager for the Screenshot screen. Binds with ButterKnife. Sets up toolbar title and back button to pop
 */
public class ScreenshotUiManager extends ScreenLifecycleTask {

    private final ScreenNavigator screenNavigator;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private Unbinder unbinder;

    @Inject
    ScreenshotUiManager(ScreenNavigator screenNavigator) {
        this.screenNavigator = screenNavigator;
    }

    @Override
    public void onEnterScope(View view) {
        unbinder = ButterKnife.bind(this, view);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            screenNavigator.pop();
        });
    }

    @Override
    public void onExitScope() {
        toolbar.setNavigationOnClickListener(null);
        ButterknifeUtils.unbind(unbinder);
    }
}
