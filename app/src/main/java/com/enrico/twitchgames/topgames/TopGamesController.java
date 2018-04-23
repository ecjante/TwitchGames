package com.enrico.twitchgames.topgames;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.enrico.twitchgames.R;
import com.enrico.twitchgames.base.BaseController;

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

    @BindView(R.id.top_games_list) RecyclerView topGamesList;
    @BindView(R.id.loading_indicator) View loadingView;
    @BindView(R.id.tv_error) TextView errorText;

    @Override
    protected void onViewBound(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        topGamesList.setLayoutManager(layoutManager);
        topGamesList.setAdapter(new TopGamesAdapter(presenter));
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
//                topGamesList.getContext(),
//                layoutManager.getOrientation()
//        );
//        topGamesList.addItemDecoration(dividerItemDecoration);
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
                viewModel.topGames()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((((TopGamesAdapter) topGamesList.getAdapter()))::setData),
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
