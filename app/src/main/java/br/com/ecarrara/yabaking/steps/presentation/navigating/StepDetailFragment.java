package br.com.ecarrara.yabaking.steps.presentation.navigating;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.squareup.picasso.Picasso;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.core.utils.ExoPlayerManager;
import br.com.ecarrara.yabaking.steps.domain.entity.Step;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;

public class StepDetailFragment extends Fragment {

    private static final String ARGUMENT_STEP = "steps_list";

    /**
     * Create a new Step Detail View for the informed step
     *
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

    @BindView(R.id.step_detail_image_view)
    ImageView imageView;

    @BindView(R.id.step_detail_short_description_text_view)
    TextView shortDescriptionTextView;

    @Nullable
    @BindView(R.id.step_detail_description_text_view)
    TextView descriptionTextView;

    private Unbinder butterKnifeUnbinder;

    private static final String LAST_KNOWN_STEP = "last_known_step";
    private static final String LAST_KNOWN_EXOPLAYER_CURRENT_WINDOW = "last_known_exoplayer_current_window";
    private static final String LAST_KNOWN_EXOPLAYER_PLAY_WHEN_READY = "last_known_exoplayer_play_when_ready";
    private static final String LAST_KNOWN_EXOPLAYER_PLAYBACK_POSITION = "last_known_exoplayer_playback_position";

    private Step step;
    private SimpleExoPlayer mediaPlayer;
    private int currentWindow = 0;
    private boolean playWhenReady = false;
    private long playbackPosition = 0L;

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
        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(LAST_KNOWN_STEP);
            currentWindow = savedInstanceState.getInt(LAST_KNOWN_EXOPLAYER_CURRENT_WINDOW);
            playWhenReady = savedInstanceState.getBoolean(LAST_KNOWN_EXOPLAYER_PLAY_WHEN_READY);
            playbackPosition = savedInstanceState.getLong(LAST_KNOWN_EXOPLAYER_PLAYBACK_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.step_details_fragment, container, false);
        butterKnifeUnbinder = ButterKnife.bind(this, inflatedView);
        initialize();
        return inflatedView;
    }

    private void initialize() {
        if (!isOnLandscapeLayout()) {
            this.descriptionTextView.setText(step.description());
        }
        this.shortDescriptionTextView.setText(step.shortDescription());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            initializeMediaPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMediaContent();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M || mediaPlayer == null) {
            initializeMediaPlayer();
        }
    }

    private void initializeMediaPlayer() {
        if (shouldPrepareVideo()) {
            mediaPlayer = ExoPlayerManager.getInstance().prepareExoPlayerForUri(
                    step.id(),
                    getContext(),
                    Uri.parse(step.videoPath()),
                    mediaPlayerView,
                    currentWindow,
                    playWhenReady,
                    playbackPosition);
        }
    }

    private void setUpMediaContent() {
        if (shouldPrepareImage()) {
            setUpStepImageView();
            return;
        }

        if (shouldPrepareEmptyView()) {
            setUpEmptyImageView();
        }
    }

    private void setUpStepImageView() {
        imageView.setVisibility(VISIBLE);
        Picasso.with(getContext())
                .load(step.thumbnailPath())
                .placeholder(R.color.primary)
                .fit()
                .into(imageView);
    }

    private void setUpEmptyImageView() {
        imageView.setVisibility(VISIBLE);
        int placeholderBackgroundColor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            placeholderBackgroundColor = getResources().getColor(R.color.primary, null);
        } else {
            placeholderBackgroundColor = getResources().getColor(R.color.primary);
        }
        imageView.setBackgroundColor(placeholderBackgroundColor);
    }

    private boolean shouldPrepareVideo() {
        return !step.videoPath().isEmpty();
    }

    private boolean shouldPrepareImage() {
        return !shouldPrepareVideo() && !step.thumbnailPath().isEmpty();
    }

    private boolean shouldPrepareEmptyView() {
        return !shouldPrepareVideo() && !shouldPrepareImage();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            ExoPlayerManager.getInstance().stopPlayerFor(step.id());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            ExoPlayerManager.getInstance().stopPlayerFor(step.id());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterKnifeUnbinder.unbind();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            ExoPlayerManager.getInstance().releaseExoPlayer(step.id());
            mediaPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(LAST_KNOWN_STEP, step);
        if (mediaPlayer != null) {
            playbackPosition = mediaPlayer.getCurrentPosition();
            currentWindow = mediaPlayer.getCurrentWindowIndex();
            playWhenReady = mediaPlayer.getPlayWhenReady();
            outState.putInt(LAST_KNOWN_EXOPLAYER_CURRENT_WINDOW, currentWindow);
            outState.putBoolean(LAST_KNOWN_EXOPLAYER_PLAY_WHEN_READY, playWhenReady);
            outState.putLong(LAST_KNOWN_EXOPLAYER_PLAYBACK_POSITION, playbackPosition);
        }
        super.onSaveInstanceState(outState);
    }

    private boolean isOnLandscapeLayout() {
        return (descriptionTextView == null);
    }

}
