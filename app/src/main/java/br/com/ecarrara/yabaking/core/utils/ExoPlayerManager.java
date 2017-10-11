package br.com.ecarrara.yabaking.core.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

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

import java.util.Collection;
import java.util.HashMap;

public final class ExoPlayerManager {

    private static ExoPlayerManager instance;

    public static ExoPlayerManager getInstance() {
        if (instance == null) {
            instance = new ExoPlayerManager();
        }
        return instance;
    }

    private HashMap<Integer, SimpleExoPlayer> playersInUse = new HashMap<>();

    private ExoPlayerManager() {
    }

    public void clearAllPlayers() {
        Collection<SimpleExoPlayer> players = playersInUse.values();
        for (SimpleExoPlayer player : players) {
            player.release();
        }
        playersInUse.clear();
    }

    public void stopPlayerFor(int playerKey) {
        if (playersInUse.containsKey(playerKey)) {
            playersInUse.get(playerKey).stop();
        }
    }

    public SimpleExoPlayer prepareExoPlayerForUri(
            int playerKey,
            @NonNull Context context,
            @NonNull Uri uri,
            @NonNull SimpleExoPlayerView simpleExoPlayerView,
            int currentWindow,
            boolean playWhenReady,
            long playbackPosition) {

        SimpleExoPlayer simpleExoPlayer = playersInUse.get(playerKey);

        if (simpleExoPlayer == null) {
            simpleExoPlayer = setUpMediaPlayer(context);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(currentWindow, playbackPosition);
            MediaSource mediaSource = setUpMediaSource(context, uri);
            simpleExoPlayer.prepare(mediaSource, true, false);
            playersInUse.put(playerKey, simpleExoPlayer);
        } else {
            simpleExoPlayer.seekTo(currentWindow, playbackPosition);
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
        }

        return simpleExoPlayer;
    }

    private SimpleExoPlayer setUpMediaPlayer(Context context) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        return ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
    }

    private MediaSource setUpMediaSource(Context context, Uri mediUri) {
        final String APPLICATION_BASE_USER_AGENT = "YaBaking";
        final String userAgent = Util.getUserAgent(context, APPLICATION_BASE_USER_AGENT);
        return new ExtractorMediaSource(
                mediUri,
                new DefaultDataSourceFactory(context, userAgent),
                new DefaultExtractorsFactory(),
                null, null);
    }

    public void releaseExoPlayer(int playerKey) {
        if (playersInUse.containsKey(playerKey)) {
            SimpleExoPlayer simpleExoPlayer = playersInUse.remove(playerKey);
            simpleExoPlayer.release();
        }
    }

}
