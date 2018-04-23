package com.enrico.twitchgames.topgames;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.models.twitch.TwitchGame;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by enrico.
 */
public class TopGamesAdapter extends RecyclerView.Adapter<TopGamesAdapter.TopGamesViewHolder> {


    private final TopGameClickedListener listener;
    private final List<TwitchTopGame> data = new ArrayList<>();

    TopGamesAdapter(TopGameClickedListener listener) {
        this.listener = listener;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public TopGamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_top_games_list_item, parent, false);
        return new TopGamesViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TopGamesViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).game().id();
    }

    void setData(List<TwitchTopGame> topGames) {
        if (topGames != null) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TopGamesDiffCallback(data, topGames));
            data.clear();
            data.addAll(topGames);
            diffResult.dispatchUpdatesTo(this);
        } else {
            data.clear();
            notifyDataSetChanged();
        }
    }

    void addData(List<TwitchTopGame> topGames) {
        List<TwitchTopGame> newData = new ArrayList<>(data);
        newData.addAll(topGames);
        setData(newData);
    }

    static final class TopGamesViewHolder extends RecyclerView.ViewHolder {

        private TwitchTopGame topGame;
        @BindView(R.id.iv_box_art) ImageView boxArtImage;
        @BindView(R.id.tv_game_name) TextView gameNameText;
        @BindView(R.id.tv_viewer_count) TextView viewerCountText;

        public TopGamesViewHolder(View itemView, TopGameClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (topGame != null) {
                    listener.onTopGameClicked(topGame.game());
                }
            });
        }

        public void bind(TwitchTopGame topGame) {
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

    interface TopGameClickedListener {
        void onTopGameClicked(TwitchGame topGame);
    }
}
