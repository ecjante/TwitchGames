package com.enrico.twitchgames.details;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.base.BaseController;
import com.enrico.twitchgames.customviews.ExpandableTextView;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by enrico.
 */
public class GameDetailsController extends BaseController {

    static final String TWITCH_GAME_ID_KEY = "twitch_game_id";
    static final String GAME_NAME_KEY = "game_name";
    private static String gameName;

    public static Controller newInstance(long twitchGameId, String gameName) {
        GameDetailsController.gameName = gameName;
        Bundle bundle = new Bundle();
        bundle.putLong(TWITCH_GAME_ID_KEY, twitchGameId);
        bundle.putString(GAME_NAME_KEY, gameName);
        return new GameDetailsController(bundle);
    }

    @Inject GameDetailsViewModel viewModel;
    @Inject GameDetailsPresenter presenter;

    @BindView(R.id.loading_indicator) View detailsLoadingView;
    @BindView(R.id.tv_error) TextView errorText;
    @BindView(R.id.iv_main_screenshot) ImageView mainScreenshotImage;
    @BindView(R.id.iv_game_cover) ImageView gameCoverImage;
    @BindView(R.id.tv_release_date) TextView releaseDateText;
    @BindView(R.id.tv_game_name) TextView gameNameText;
    @BindView(R.id.tv_summary) ExpandableTextView summaryText;
    @BindView(R.id.show_more_or_less) TextView showMoreOrLess;

    @BindView(R.id.streams_list) RecyclerView streamsList;
    @BindView(R.id.streams_loading_indicator) View streamsLoadingView;
    @BindView(R.id.tv_streams_error) TextView streamsErrorText;

    public GameDetailsController(Bundle bundle) {
        super(bundle);
    }

    @Override
    protected void onViewBound(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        streamsList.setLayoutManager(layoutManager);
        streamsList.setAdapter(new StreamsAdapter(presenter));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                streamsList.getContext(),
                layoutManager.getOrientation()
        );
        streamsList.addItemDecoration(dividerItemDecoration);

        summaryText.setInterpolator(new AccelerateDecelerateInterpolator());
        summaryText.setOnClickListener(v -> {
            summaryText.toggle();
        });
        showMoreOrLess.setOnClickListener(v -> {
            summaryText.toggle();
        });
        summaryText.addOnExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(@NonNull ExpandableTextView view) {
                showMoreOrLess.setText(R.string.show_less);
            }

            @Override
            public void onCollapse(@NonNull ExpandableTextView view) {
                showMoreOrLess.setText(R.string.show_more);
            }
        });
    }

    @Override
    protected Disposable[] subscriptions() {
        return new Disposable[] {
            viewModel.details()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(details -> {
                    if (details.loading()) {
                        detailsLoadingView.setVisibility(View.VISIBLE);
                        errorText.setVisibility(View.GONE);
                        errorText.setText(null);
                    } else {
                        if (details.isSuccess()) {
                            errorText.setText(null);
                            Glide.with(mainScreenshotImage.getContext())
                                    .load(details.mainScreenShot())
                                    .apply(new RequestOptions().placeholder(R.drawable.screenshot_placeholder))
                                    .into(mainScreenshotImage);
                            Glide.with(gameCoverImage.getContext())
                                    .load(details.cover())
                                    .apply(new RequestOptions().placeholder(R.drawable.game_placeholder))
                                    .into(gameCoverImage);
                        } else {
                            //noinspection ConstantConditions
                            errorText.setText(details.errorRes());
                        }
                        detailsLoadingView.setVisibility(View.GONE);
                        errorText.setVisibility(details.isSuccess() ? View.GONE : View.VISIBLE);
                        releaseDateText.setText(details.releaseDate());
                        summaryText.setText(details.summary());
                        summaryText.setVisibility(details.isSuccess() ? View.VISIBLE : View.GONE);
                        gameNameText.setText(details.name() != null ? details.name() : gameName);
                        showMoreOrLess.setVisibility(details.isSuccess() && summaryIsEllipsized() ? View.VISIBLE : View.GONE);
                    }
            }),
            viewModel.streams()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(streamsDetails -> {
                    if (streamsDetails.loading()) {
                        streamsLoadingView.setVisibility(View.VISIBLE);
                        streamsList.setVisibility(View.GONE);
                        streamsErrorText.setVisibility(View.GONE);
                        streamsErrorText.setText(null);
                    } else {
                        streamsLoadingView.setVisibility(View.GONE);
                        streamsList.setVisibility(streamsDetails.isSuccess() ? View.VISIBLE : View.GONE);
                        streamsErrorText.setVisibility(streamsDetails.isSuccess() ? View.GONE : View.VISIBLE);
                        if (streamsDetails.isSuccess()) {
                            streamsErrorText.setText(null);
                            ((StreamsAdapter) streamsList.getAdapter()).setData(streamsDetails.streams());
                        } else {
                            //noinspection ConstantConditions
                            streamsErrorText.setText(streamsDetails.errorRes());
                        }
                    }
            })
        };
    }

    private boolean summaryIsEllipsized() {
        Layout layout = summaryText.getLayout();
        if (layout != null) {
            int lines = layout.getLineCount();
            if (lines > 0) {
                int ellipsisCount = layout.getEllipsisCount(lines - 1);
                return ellipsisCount > 0;
            }
        }
        return false;
    }

    @Override
    protected int layoutRes() {
        return R.layout.screen_game_details;
    }
}
