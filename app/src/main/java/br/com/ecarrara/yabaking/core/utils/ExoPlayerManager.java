package br.com.ecarrara.yabaking.core.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.SurfaceView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayerManager {

    private static ExoPlayerManager exoPlayerManagerInstance;

    public static ExoPlayerManager getInstance() {
        if (exoPlayerManagerInstance == null) {
            exoPlayerManagerInstance = new ExoPlayerManager();
        }
        return exoPlayerManagerInstance;
    }

    private SimpleExoPlayer simpleExoPlayer;
    private Uri mediUri;
    private boolean isPlaying;

    public void prepareExoPlayerForUri(
            @NonNull Context context,
            @NonNull Uri uri,
            @NonNull SimpleExoPlayerView simpleExoPlayerView) {
        if (!uri.equals(mediUri) || simpleExoPlayer == null) {
            mediUri = uri;
            setUpMediaPlayer(context);
            setUpMediaSource(context);
        }

        simpleExoPlayer.clearVideoSurface();
        simpleExoPlayer.setVideoSurfaceView((SurfaceView) simpleExoPlayerView.getVideoSurfaceView());
        simpleExoPlayer.seekTo(simpleExoPlayer.getCurrentPosition() + 1);
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
    }

    private void setUpMediaPlayer(Context context) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
    }

    private void setUpMediaSource(Context context) {
        final String APPLICATION_BASE_USER_AGENT = "YaBaking";
        final String userAgent = Util.getUserAgent(context, APPLICATION_BASE_USER_AGENT);
        MediaSource mediaSource = new ExtractorMediaSource(
                mediUri,
                new DefaultDataSourceFactory(context, userAgent),
                new DefaultExtractorsFactory(),
                null, null);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.getPlayWhenReady();
    }

    public void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    public void goToBackground() {
        if (simpleExoPlayer != null) {
            isPlaying = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.setPlayWhenReady(false);
        }
    }

    public void goToForeground() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(isPlaying);
        }
    }

}
