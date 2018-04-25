package com.enrico.twitchgames.topgames;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.enrico.poweradapter.adapter.RecyclerAdapter;
import com.enrico.poweradapter.adapter.RecyclerDataSource;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.base.BaseController;
import com.enrico.twitchgames.util.EndlessRecyclerViewScrollListener;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by enrico.
 */
public class TopGamesController extends BaseController {

    @Inject TopGamesPresenter presenter;
    @Inject TopGamesViewModel viewModel;
    @Inject RecyclerDataSource dataSource;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.top_games_list) RecyclerView topGamesList;
    @BindView(R.id.loading_indicator) View loadingView;
    @BindView(R.id.tv_error) TextView errorText;

    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onViewBound(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        topGamesList.setLayoutManager(layoutManager);
        topGamesList.setAdapter(new RecyclerAdapter(dataSource));

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.loadNextTopGames();
            }
        };

        topGamesList.addOnScrollListener(scrollListener);

        toolbar.setOnClickListener(v -> topGamesList.smoothScrollToPosition(0));
    }

    @Override
    protected Disposable[] subscriptions() {
        return new Disposable[] {
                viewModel.loading()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(loading ->  {
                            loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
                            topGamesList.setVisibility(loading ? View.GONE : View.VISIBLE);
                            errorText.setVisibility(loading ? View.GONE : errorText.getVisibility());
                }),
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
