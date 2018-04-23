package com.enrico.twitchgames.details;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.models.twitch.TwitchChannel;
import com.enrico.twitchgames.models.twitch.TwitchStream;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by enrico.
 */
public class StreamsAdapter extends RecyclerView.Adapter<StreamsAdapter.StreamsViewHolder> {

    private final StreamClickedListener listener;
    private final List<TwitchStream> data = new ArrayList<>();

    StreamsAdapter(StreamClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public StreamsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_twitch_stream_list_item, parent, false);
        return new StreamsViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull StreamsViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    void setData(List<TwitchStream> streams) {
        if (streams != null) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new StreamsDiffCallback(data, streams));
            data.clear();
            data.addAll(streams);
            diffResult.dispatchUpdatesTo(this);
        } else {
            data.clear();
            notifyDataSetChanged();
        }
    }

    void addData(List<TwitchStream> streams) {
        List<TwitchStream> newData = new ArrayList<>(data);
        newData.addAll(streams);
        setData(newData);
    }

    static final class StreamsViewHolder extends RecyclerView.ViewHolder {

        private TwitchStream stream;
        @BindView(R.id.iv_thumbnail) ImageView thumbnailImage;
        @BindView(R.id.tv_viewer_count) TextView viewerCountText;
        @BindView(R.id.iv_channel_logo) ImageView channelLogoImage;
        @BindView(R.id.tv_channel_name) TextView channelNameText;
        @BindView(R.id.tv_channel_status) TextView channelStatusText;
        @BindView(R.id.tv_follower_count) TextView followerCountText;

        StreamsViewHolder(View itemView, StreamClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (stream != null) {
                    listener.onStreamClicked(stream);
                }
            });
        }

        void bind(TwitchStream stream) {
            if (this.stream == null || !this.stream.id().equals(stream.id())) {
                this.stream = stream;
                Glide.with(thumbnailImage.getContext())
                        .load(stream.preview().getLarge())
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
                                R.plurals.viewer_count,
                                channel.followers(),
                                NumberFormat.getNumberInstance(Locale.getDefault()).format(channel.followers())
                        )
                );
            }
        }
    }

    interface StreamClickedListener {
        void onStreamClicked(TwitchStream stream);
    }
}
