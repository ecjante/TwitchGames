package com.enrico.twitchgames.topgames;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.enrico.poweradapter.item.ItemRenderer;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;

import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by enrico.
 */
public class TopGamesRenderer implements ItemRenderer<TwitchTopGame> {

    private final Provider<TopGamesPresenter> presenterProvider;

    @Inject
    TopGamesRenderer(Provider<TopGamesPresenter> presenterProvider) {
        this.presenterProvider = presenterProvider;
    }

    @Override
    public int layoutRes() {
        return R.layout.view_top_games_list_item;
    }

    @Override
    public View createView(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes(), parent, false);
        view.setTag(new ViewBinder(view, presenterProvider.get()));
        return view;
    }

    @Override
    public void render(@NonNull View itemView, @NonNull TwitchTopGame item) {
        ((ViewBinder) itemView.getTag()).bind(item);
    }

    static class ViewBinder {

        private TwitchTopGame topGame;
        @BindView(R.id.iv_box_art)
        ImageView boxArtImage;
        @BindView(R.id.tv_game_name)
        TextView gameNameText;
        @BindView(R.id.tv_viewer_count) TextView viewerCountText;

        ViewBinder(View itemView, TopGamesPresenter presenter) {
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (topGame != null) {
                    presenter.onTopGameClicked(topGame.game());
                }
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
