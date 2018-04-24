package com.enrico.twitchgames.details;

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
import com.enrico.twitchgames.models.twitch.TwitchChannel;
import com.enrico.twitchgames.models.twitch.TwitchStream;

import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by enrico.
 */
public class StreamRender implements ItemRenderer<TwitchStream> {

    private final Provider<GameDetailsPresenter> presenterProvider;

    @Inject
    StreamRender(Provider<GameDetailsPresenter> presenterProvider) {
        this.presenterProvider = presenterProvider;
    }

    @Override
    public int layoutRes() {
        return R.layout.view_twitch_stream_list_item;
    }

    @Override
    public View createView(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes(), parent, false);
        view.setTag(new ViewBinder(view, presenterProvider.get()));
        return view;
    }

    @Override
    public void render(@NonNull View itemView, @NonNull TwitchStream item) {
        ((ViewBinder) itemView.getTag()).bind(item);
    }

    static class ViewBinder {

        private TwitchStream stream;
        @BindView(R.id.iv_thumbnail)
        ImageView thumbnailImage;
        @BindView(R.id.tv_viewer_count)
        TextView viewerCountText;
        @BindView(R.id.iv_channel_logo) ImageView channelLogoImage;
        @BindView(R.id.tv_channel_name) TextView channelNameText;
        @BindView(R.id.tv_channel_status) TextView channelStatusText;
        @BindView(R.id.tv_follower_count) TextView followerCountText;

        ViewBinder(View itemView, GameDetailsPresenter presenter) {
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (stream != null) {
                    presenter.onStreamClicked(stream);
                }
            });
        }

        void bind(TwitchStream stream) {
            if (this.stream == null || !this.stream.id().equals(stream.id())) {
                this.stream = stream;
                Glide.with(thumbnailImage.getContext())
                        .load(stream.preview().getLarge())
                        .apply(new RequestOptions().placeholder(R.drawable.stream_placeholder))
                        .into(thumbnailImage);
                viewerCountText.setText(
                        viewerCountText.getContext().getResources().getQuantityString(
                                R.plurals.viewer_count,
                                stream.viewers(),
                                NumberFormat.getNumberInstance(Locale.getDefault()).format(stream.viewers())
                        )
                );
                TwitchChannel channel = stream.channel();
                Glide.with(channelLogoImage.getContext())
                        .load(channel.logo())
                        .into(channelLogoImage);
                channelNameText.setText(channel.displayName());
                channelStatusText.setText(channel.status());
                followerCountText.setText(
                        followerCountText.getContext().getResources().getQuantityString(
                                R.plurals.follower_count,
                                channel.followers(),
                                NumberFormat.getNumberInstance(Locale.getDefault()).format(channel.followers())
                        )
                );
            }
        }
    }
}
