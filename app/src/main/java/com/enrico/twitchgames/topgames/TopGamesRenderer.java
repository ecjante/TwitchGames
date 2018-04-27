package com.enrico.twitchgames.topgames;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.enrico.poweradapter.item.ItemRenderer;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.database.favorites.FavoriteTwitchGameService;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;
import com.jakewharton.rxbinding2.view.RxView;

import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by enrico.
 *
 * Renderer for the top games rows
 */
public class TopGamesRenderer implements ItemRenderer<TwitchTopGame> {

    private final Provider<TopGamesPresenter> presenterProvider;
    private final FavoriteTwitchGameService favoriteTwitchGameService;

    @Inject
    TopGamesRenderer(
            Provider<TopGamesPresenter> presenterProvider,
            FavoriteTwitchGameService favoriteTwitchGameService) {
        this.presenterProvider = presenterProvider;
        this.favoriteTwitchGameService = favoriteTwitchGameService;
    }

    @Override
    public int layoutRes() {
        return R.layout.view_top_games_list_item;
    }

    @Override
    public View createView(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes(), parent, false);
        view.setTag(new ViewBinder(view, presenterProvider.get(), favoriteTwitchGameService));
        return view;
    }

    @Override
    public void render(@NonNull View itemView, @NonNull TwitchTopGame item) {
        ((ViewBinder) itemView.getTag()).bind(item);
    }

    /**
     * Binds the data to the view
     */
    static class ViewBinder {

        @BindView(R.id.iv_box_art) ImageView boxArtImage;
        @BindView(R.id.tv_game_name) TextView gameNameText;
        @BindView(R.id.tv_viewer_count) TextView viewerCountText;
        @BindView(R.id.ib_favorite) ImageButton favoriteButton;

        private final FavoriteTwitchGameService favoriteTwitchGameService;

        private TwitchTopGame topGame;
        private Disposable favoriteDisposable;

        @SuppressLint("CheckResult")
        ViewBinder(View itemView, TopGamesPresenter presenter, FavoriteTwitchGameService favoriteTwitchGameService) {
            this.favoriteTwitchGameService = favoriteTwitchGameService;
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                if (topGame != null) {
                    presenter.onTopGameClicked(topGame.game());
                }
            });

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

        @OnClick(R.id.ib_favorite)
        void toggleFavorite() {
            if (topGame != null) {
                favoriteTwitchGameService.toggleFavoriteTwitchGame(topGame.game());
            }
        }

        private void listenForFavoriteChanges() {
            favoriteDisposable = favoriteTwitchGameService.favoritedTwitchGameIds()
                    .filter(__ -> topGame != null)
                    .map(favoriteIds -> favoriteIds.contains(topGame.getId()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(isFavorite -> {
                        favoriteButton.setImageResource(isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                    });
        }

        void bind(TwitchTopGame topGame) {
            if (this.topGame == null || !this.topGame.game().id().equals(topGame.game().id())) {
                this.topGame = topGame;
                Glide.with(boxArtImage.getContext())
                        .load(topGame.game().box().getMedium())
                        .apply(new RequestOptions().placeholder(R.drawable.game_placeholder))
                        .into(boxArtImage);
                gameNameText.setText(topGame.game().name());
                viewerCountText.setVisibility(View.GONE);
                if (topGame.viewers() != null) {
                    viewerCountText.setVisibility(View.VISIBLE);
                    viewerCountText.setText(
                            viewerCountText.getContext().getResources().getQuantityString(
                                    R.plurals.viewer_count,
                                    topGame.viewers(),
                                    NumberFormat.getNumberInstance(Locale.getDefault()).format(topGame.viewers())
                            )
                    );
                }
            }
        }
    }
}
