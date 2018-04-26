package com.enrico.twitchgames.details;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enrico.poweradapter.item.ItemRenderer;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.models.igdb.IgdbGameVideo;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by enrico.
 */
public class IgdbGameVideoRenderer implements ItemRenderer<IgdbGameVideo> {

    private final Provider<GameDetailsPresenter> presenterProvider;

    @Inject
    IgdbGameVideoRenderer(Provider<GameDetailsPresenter> presenterProvider) {
        this.presenterProvider = presenterProvider;
    }

    @Override
    public int layoutRes() {
        return R.layout.view_video_list_item;
    }

    @Override
    public View createView(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes(), parent, false);
        view.setTag(new ViewBinder(view, presenterProvider.get()));
        return view;
    }

    @Override
    public void render(@NonNull View itemView, @NonNull IgdbGameVideo item) {
        ((ViewBinder) itemView.getTag()).bind(item.videoId());
    }

    static class ViewBinder {

        @BindView(R.id.iv_video) YouTubeThumbnailView videoImage;
        private String videoId;

        ViewBinder(View itemView, GameDetailsPresenter presenter) {
            ButterKnife.bind(this, itemView);
            videoImage.setOnClickListener(v -> {
                if (videoId != null) {
                    presenter.onVideoClicked(videoId);
                }
            });
        }

        void bind(String youtubeId) {
            if (this.videoId == null || !this.videoId.equals(youtubeId)) {
                this.videoId = youtubeId;
                videoImage.initialize(videoImage.getContext().getString(R.string.google_api_key), new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                        youTubeThumbnailLoader.setVideo(videoId);
                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                            @Override
                            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                                youTubeThumbnailLoader.release();
                            }

                            @Override
                            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
            }
        }
    }

}