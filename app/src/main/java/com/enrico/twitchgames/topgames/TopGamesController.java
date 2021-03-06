package com.enrico.twitchgames.topgames;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.enrico.poweradapter.adapter.RecyclerAdapter;
import com.enrico.poweradapter.adapter.RecyclerDataSource;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.base.BaseController;
import com.enrico.twitchgames.util.EndlessRecyclerViewScrollListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by enrico.
 *
 * Controller for Twitch top games
 */
public class TopGamesController extends BaseController {

    @Inject TopGamesPresenter presenter;
    @Inject TopGamesViewModel viewModel;
    @Inject RecyclerDataSource dataSource;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.top_games_list) RecyclerView topGamesList;
    @BindView(R.id.loading_indicator) View loadingView;
    @BindView(R.id.more_loading_indicator) View moreLoadingView;
    @BindView(R.id.tv_error) TextView errorText;

    @BindView(R.id.toolbar_button) ImageButton favoritesButton;

    private EndlessRecyclerViewScrollListener scrollListener;

    private boolean isTopGames = true;

    @Override
    protected void onViewBound(View view) {
        // set up recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        topGamesList.setLayoutManager(layoutManager);
        topGamesList.setAdapter(new RecyclerAdapter(dataSource, true));

        // scroll listener to load more top games when scrolling
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (isTopGames) {
                    presenter.loadNextTopGames();
                }
            }
        };
        topGamesList.addOnScrollListener(scrollListener);

        // favorites button to view favorited twitch games
        favoritesButton.setVisibility(View.VISIBLE);
        favoritesButton.setImageResource(R.drawable.ic_favorite);
    }

    /**
     * Click handler on toolbar. Scroll to top when click
     */
    @OnClick(R.id.toolbar)
    void scrollToTop() {
        topGamesList.smoothScrollToPosition(0);
    }

    /**
     * Click handler for the toolbar button. Toggle between top games and favorited games
     */
    @OnClick(R.id.toolbar_button)
    void showTopGames() {
        if (isTopGames) {
            presenter.loadFavoriteGames();
            favoritesButton.setImageResource(R.drawable.ic_top);
            toolbar.setTitle(R.string.screen_title_favorite_games);
        } else {
            presenter.loadTopGames();
            favoritesButton.setImageResource(R.drawable.ic_favorite);
            toolbar.setTitle(R.string.screen_title_top_games);
        }
        isTopGames = !isTopGames;
    }

    @Override
    protected Disposable[] subscriptions() {
        return new Disposable[] {
                // update loading when loading updated
                viewModel.loading()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(loading ->  {
                            loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
                            topGamesList.setVisibility(loading ? View.GONE : View.VISIBLE);
                            errorText.setVisibility(loading ? View.GONE : errorText.getVisibility());
                }),
                // update more loading when updated
                viewModel.moreLoading()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(loading ->  {
                            moreLoadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
                }),
                // update view when errors updated
                viewModel.error()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(errorRes -> {
                            if (errorRes == -1) {
                                errorText.setText(null);
                                errorText.setVisibility(View.GONE);
                            } else {
                                errorText.setVisibility(View.VISIBLE);
                                topGamesList.setVisibility(View.GONE);
                                errorText.setText(errorRes);
                            }
                })
        };
    }

    @Override
    protected int layoutRes() {
        return R.layout.screen_top_games;
    }
}
