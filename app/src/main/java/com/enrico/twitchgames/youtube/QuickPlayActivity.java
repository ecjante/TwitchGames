package com.enrico.twitchgames.youtube;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.enrico.twitchgames.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuickPlayActivity extends YouTubeBaseActivity {

    public static final String YOUTUBE_ID_KEY = "youtubeId";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.player) YouTubePlayerView playerView;
    private String youtubeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_play);

        ButterKnife.bind(this);

//        toolbar.setTitle();
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        Intent intent = getIntent();
        if (intent != null) {
            youtubeId = intent.getStringExtra(YOUTUBE_ID_KEY);
        }

        playerView.initialize(getString(R.string.google_api_key),
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo(youtubeId);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
