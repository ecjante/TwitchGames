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

public class QuickPlayActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String YOUTUBE_ID_KEY = "youtubeId";
    private static final String SEEK_KEY = "seek";

    public static final int REQUEST_CODE_ERROR = 100;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.player) YouTubePlayerView playerView;
    private String youtubeId;
    private YouTubePlayer youTubePlayer;
    private int seekTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_play);

        ButterKnife.bind(this);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        Intent intent = getIntent();
        if (intent != null)
            youtubeId = intent.getStringExtra(YOUTUBE_ID_KEY);

        if (savedInstanceState != null && savedInstanceState.containsKey(SEEK_KEY))
            seekTime = savedInstanceState.getInt(SEEK_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (youTubePlayer != null) {
            youTubePlayer.cueVideo(youtubeId, seekTime);
        } else {
            initializePlayer();
        }
    }

    private void initializePlayer() {
        playerView.initialize(getString(R.string.google_api_key), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        this.youTubePlayer = youTubePlayer;
        youTubePlayer.loadVideo(youtubeId, seekTime);
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){

            youTubeInitializationResult.getErrorDialog(this,REQUEST_CODE_ERROR);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_ERROR){

            playerView.initialize(getString(R.string.google_api_key),this);

        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        seekTime = youTubePlayer.getCurrentTimeMillis();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(SEEK_KEY, youTubePlayer.getCurrentTimeMillis());
        super.onSaveInstanceState(bundle);
    }
}
