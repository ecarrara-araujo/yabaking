package br.com.ecarrara.yabaking.steps.presentation.navigating;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.steps.domain.entity.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {

    private static final String ARGUMENT_STEP = "steps_list";

    /**
     * Create a new Step Detail View for the informed step
     * @param step to be rendered by the view
     * @return the prepared view
     */
    public static StepDetailFragment newInstance(Step step) {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_STEP, step);
        stepDetailFragment.setArguments(args);
        return stepDetailFragment;
    }

    @BindView(R.id.step_detail_media_view)
    SimpleExoPlayerView mediaPlayerView;

    @BindView(R.id.step_detail_short_description_text_view)
    TextView shortDescriptionTextView;

    @BindView(R.id.step_detail_description_text_view)
    TextView descriptionTextView;

    private static final String LAST_KNOWN_STEP = "last_known_step";

    private Step step;
    private SimpleExoPlayer mediaPlayer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processArguments(getArguments());
        processSavedInstanceState(savedInstanceState);
    }

    private void processArguments(Bundle arguments) {
        if (arguments != null) {
            step = getArguments().getParcelable(ARGUMENT_STEP);
        }
    }

    private void processSavedInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null && step == null) {
            step = savedInstanceState.getParcelable(LAST_KNOWN_STEP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.step_details_fragment, container, false);
        ButterKnife.bind(this, inflatedView);
        initialize();
        return inflatedView;
    }

    private void initialize() {
        this.shortDescriptionTextView.setText(step.shortDescription());
        this.descriptionTextView.setText(step.description());

        setUpMediaPlayer();
        setUpMediaSource();
    }

    private void setUpMediaPlayer() {
        mediaPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.color.accent));

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mediaPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        mediaPlayerView.setPlayer(mediaPlayer);
    }

    private void setUpMediaSource() {
        final String APPLICATION_BASE_USER_AGENT = "YaBaking";
        final String userAgent = Util.getUserAgent(getContext(), APPLICATION_BASE_USER_AGENT);
        MediaSource mediaSource = new ExtractorMediaSource(
                Uri.parse(step.videoPath()),
                new DefaultDataSourceFactory(getContext(), userAgent),
                new DefaultExtractorsFactory(),
                null, null);
        mediaPlayer.prepare(mediaSource);
        mediaPlayer.getPlayWhenReady();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(LAST_KNOWN_STEP, step);
        super.onSaveInstanceState(outState);
    }

}
