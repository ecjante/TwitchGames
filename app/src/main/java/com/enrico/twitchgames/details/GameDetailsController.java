package com.enrico.twitchgames.details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.enrico.poweradapter.adapter.RecyclerAdapter;
import com.enrico.poweradapter.adapter.RecyclerDataSource;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.base.BaseController;
import com.enrico.twitchgames.customviews.ExpandableTextView;
import com.enrico.twitchgames.database.favorites.FavoriteTwitchGameService;
import com.enrico.twitchgames.models.twitch.TwitchGame;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by enrico.
 *
 * Controller for game details
 */
public class GameDetailsController extends BaseController implements ExpandableTextView.OnExpandListener {

    static final String TWITCH_GAME_ID_KEY = "twitch_game_id";
    static final String GAME_NAME_KEY = "game_name";
    static final String GAME_BOX_TEMPLATE_KEY = "game_box_template";

    public static Controller newInstance(long twitchGameId, String gameName, String gameBoxTemplate) {
        Bundle bundle = new Bundle();
        bundle.putLong(TWITCH_GAME_ID_KEY, twitchGameId);
        bundle.putString(GAME_NAME_KEY, gameName);
        bundle.putString(GAME_BOX_TEMPLATE_KEY, gameBoxTemplate);
        return new GameDetailsController(bundle);
    }


    @Inject @Named("twitch_game_id") long twitchGameId;
    @Inject @Named("game_name") String gameName;
    @Inject @Named("game_box_template") String gameBoxTemplate;
    @Inject GameDetailsViewModel viewModel;
    @Inject GameDetailsPresenter presenter;
    @Inject @Named("streams_datasource") RecyclerDataSource streamsDataSource;
    @Inject @Named("screenshots_datasource") RecyclerDataSource screenshotsDataSource;
    @Inject @Named("videos_datasource") RecyclerDataSource videosDataSource;
    @Inject FavoriteTwitchGameService favoriteTwitchGameService;

    @BindView(R.id.loading_indicator) View detailsLoadingView;
    @BindView(R.id.tv_error) TextView errorText;
    @BindView(R.id.iv_main_screenshot) ImageView mainScreenshotImage;
    @BindView(R.id.iv_game_cover) ImageView gameCoverImage;
    @BindView(R.id.tv_release_date) TextView releaseDateText;
    @BindView(R.id.tv_game_name) TextView gameNameText;
    @BindView(R.id.tv_summary) ExpandableTextView summaryText;
    @BindView(R.id.show_more_or_less) TextView showMoreOrLess;

    @BindView(R.id.screenshots) View screenshots;
    @BindView(R.id.screenshot_list) RecyclerView screenshotList;

    @BindView(R.id.videos) View videos;
    @BindView(R.id.video_list) RecyclerView videoList;

    @BindView(R.id.streams_list) RecyclerView streamsList;
    @BindView(R.id.streams_loading_indicator) View streamsLoadingView;
    @BindView(R.id.tv_streams_error) TextView streamsErrorText;

    @BindView(R.id.toolbar_button) ImageButton favoriteButton;


    private Disposable favoriteDisposable;
    private TwitchGame twitchGame;
    private String mainScreenshot;
    private String cover;

    public GameDetailsController(Bundle bundle) {
        super(bundle);
    }

    @Override
    protected void onContextAvailable(@NonNull Context context) {
        super.onContextAvailable(context);

        twitchGame = TwitchGame.buildGame(twitchGameId, gameName, gameBoxTemplate);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onViewBound(View view) {
        // set up screenshots recycler view
        screenshotList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        screenshotList.setAdapter(new RecyclerAdapter(screenshotsDataSource, false));

        // set up videos recycler view
        videoList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        videoList.setAdapter(new RecyclerAdapter(videosDataSource, false));

        // set up twitch streams recycler view
        LinearLayoutManager streamsLayoutManager = new LinearLayoutManager(view.getContext());
        streamsList.setLayoutManager(streamsLayoutManager);
        streamsList.setAdapter(new RecyclerAdapter(streamsDataSource, true));

        // set up summary text expander
        summaryText.setInterpolator(new AccelerateDecelerateInterpolator());
        summaryText.addOnExpandListener(this);

        // set up favorite button RxBindings
        favoriteButton.setVisibility(View.VISIBLE);
        RxView.attachEvents(favoriteButton)
                .subscribe(event -> {
                    if (event.view().isAttachedToWindow()) {
                        listenForFavoriteChanges();
                    } else {
                        if (favoriteDisposable != null) {
                            favoriteDisposable.dispose();
                            favoriteDisposable = null;
                        }
                    }
                });
    }

    @OnClick(R.id.iv_main_screenshot)
    void mainScreenShotOnClick() {
        if (mainScreenshot != null) {
            presenter.onImageClicked(mainScreenshot);
        }
    }

    @OnClick(R.id.iv_game_cover)
    void gameCoverOnClick() {
        if (cover != null) {
            presenter.onImageClicked(cover);
        }
    }

    /**
     * Click handler for summary text view to toggle expand or collapse
     */
    @OnClick(R.id.tv_summary)
    void summaryOnClick() {
        summaryText.toggle();
    }

    /**
     * Click handler for show more or less text view to toggle expand or collapse
     */
    @OnClick(R.id.show_more_or_less)
    void showMoreOrLessOnClick() {
        summaryText.toggle();
    }

    /**
     * ExpandableTextView on expand call to change showMoreOrLess text
     * @param view the textview
     */
    @Override
    public void onExpand(@NonNull ExpandableTextView view) {
        showMoreOrLess.setText(R.string.show_less);
    }

    /**
     * ExpandableTextView on collapse call to change showMoreOrLess text
     * @param view the textview
     */
    @Override
    public void onCollapse(@NonNull ExpandableTextView view) {
        showMoreOrLess.setText(R.string.show_more);
    }

    /**
     * Favorite button click handler to toggle favorite
     */
    @OnClick(R.id.toolbar_button)
    void toggleFavorite() {
        if (twitchGame != null) {
            favoriteTwitchGameService.toggleFavoriteTwitchGame(twitchGame);
        }
    }

    /**
     * Helper method to listen for changes when favorite button is toggled
     */
    private void listenForFavoriteChanges() {
        favoriteDisposable = favoriteTwitchGameService.favoritedTwitchGameIds()
                .map(favoriteIds -> favoriteIds.contains(twitchGameId))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isFavorite -> {
                    if (favoriteButton != null) {
                        favoriteButton.setImageResource(isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                    }
                });
    }

    @Override
    protected Disposable[] subscriptions() {
        return new Disposable[] {
                // details handler
                viewModel.details()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(details -> {
                        if (details.loading()) {
                            // handle when loading
                            detailsLoadingView.setVisibility(View.VISIBLE);
                            errorText.setVisibility(View.GONE);
                            errorText.setText(null);
                        } else {
                            mainScreenshot = null;
                            cover = null;
                            if (details.isSuccess()) {
                                // handle when details success
                                errorText.setText(null);
                                mainScreenshot = details.mainScreenShot();
                                Glide.with(mainScreenshotImage.getContext())
                                        .load(mainScreenshot)
                                        .apply(new RequestOptions().placeholder(R.drawable.screenshot_placeholder))
                                        .into(mainScreenshotImage);
                                cover = details.cover() != null ? details.cover() : twitchGame.box().getExtraLarge();
                                Glide.with(gameCoverImage.getContext())
                                        .load(cover)
                                        .apply(new RequestOptions().placeholder(R.drawable.game_placeholder))
                                        .into(gameCoverImage);
                            } else {
                                //noinspection ConstantConditions
                                errorText.setText(details.errorRes());
                            }
                            // update data
                            detailsLoadingView.setVisibility(View.GONE);
                            errorText.setVisibility(details.isSuccess() ? View.GONE : View.VISIBLE);
                            releaseDateText.setText(details.releaseDate());
                            summaryText.setText(details.summary());
                            summaryText.setVisibility(details.isSuccess() ? View.VISIBLE : View.GONE);
                            gameNameText.setText(details.name() != null ? details.name() : twitchGame.name());
                            showMoreOrLess.setVisibility(details.isSuccess() && summaryIsEllipsized() ? View.VISIBLE : View.GONE);
                            screenshots.setVisibility(details.hasScreenshots() ? View.VISIBLE : View.GONE);
                            videos.setVisibility(details.hasVideos() ? View.VISIBLE : View.GONE);
                        }
                }),
                // streams handler
                viewModel.streams()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(streamsDetails -> {
                        if (streamsDetails.loading()) {
                            // handle visibilities when streams loading
                            streamsLoadingView.setVisibility(View.VISIBLE);
                            streamsList.setVisibility(View.GONE);
                            streamsErrorText.setVisibility(View.GONE);
                            streamsErrorText.setText(null);
                        } else {
                            // handle visibilities when not loading
                            streamsLoadingView.setVisibility(View.GONE);
                            streamsList.setVisibility(streamsDetails.isSuccess() ? View.VISIBLE : View.GONE);
                            streamsErrorText.setVisibility(streamsDetails.isSuccess() ? View.GONE : View.VISIBLE);
                            if (streamsDetails.isSuccess()) {
                                streamsErrorText.setText(null);
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
