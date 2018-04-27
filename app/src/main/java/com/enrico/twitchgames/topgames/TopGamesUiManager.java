package com.enrico.twitchgames.topgames;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.enrico.twitchgames.R;
import com.enrico.twitchgames.di.ScreenScope;
import com.enrico.twitchgames.lifecycle.ScreenLifecycleTask;
import com.enrico.twitchgames.util.ButterknifeUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by enrico.
 *
 * UiManager for the Twitch top games view. Binds the views with ButterKnife and set the toolbar title
 */
@ScreenScope
public class TopGamesUiManager extends ScreenLifecycleTask {

    @BindView(R.id.toolbar) Toolbar toolbar;
    private Unbinder unbinder;

    @Inject
    TopGamesUiManager() {

    }

    @Override
    public void onEnterScope(View view) {
        unbinder = ButterKnife.bind(this, view);
        toolbar.setTitle(R.string.screen_title_top_games);
    }

    @Override
    public void onExitScope() {
        ButterknifeUtils.unbind(unbinder);
    }
}
