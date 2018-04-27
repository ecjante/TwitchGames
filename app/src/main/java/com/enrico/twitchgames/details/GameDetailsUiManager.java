package com.enrico.twitchgames.details;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.enrico.twitchgames.R;
import com.enrico.twitchgames.di.ScreenScope;
import com.enrico.twitchgames.lifecycle.ScreenLifecycleTask;
import com.enrico.twitchgames.ui.ScreenNavigator;
import com.enrico.twitchgames.util.ButterknifeUtils;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by enrico.
 *
 * UI manager for the game details. Binds the views and sets up the toolbar
 */
@ScreenScope
public class GameDetailsUiManager extends ScreenLifecycleTask {

    private final String gameName;
    private final ScreenNavigator screenNavigator;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private Unbinder unbinder;

    @Inject
    GameDetailsUiManager(@Named("game_name") String gameName, ScreenNavigator screenNavigator) {
        this.gameName = gameName;
        this.screenNavigator = screenNavigator;
    }

    @Override
    public void onEnterScope(View view) {
        unbinder = ButterKnife.bind(this, view);
        toolbar.setTitle(gameName);
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
