package br.com.ecarrara.yabaking.steps.presentation.navigating;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.core.di.Injector;
import br.com.ecarrara.yabaking.core.utils.ExoPlayerManager;
import br.com.ecarrara.yabaking.core.utils.RxEventBus;
import br.com.ecarrara.yabaking.steps.domain.entity.Step;
import br.com.ecarrara.yabaking.steps.presentation.listing.StepsListFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import io.reactivex.disposables.Disposable;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class StepsNavigationActivity extends AppCompatActivity {

    public static final String ARGUMENT_STEPS_KEY = "steps";
    public static final String ARGUMENT_CURRENT_STEP_KEY = "current_step";

    public static void navigate(AppCompatActivity parentActivity, List<Step> steps, Integer currentStepId) {
        ArrayList<Step> stepsToBeBundled = new ArrayList<>(steps.size());
        stepsToBeBundled.addAll(steps);

        Intent intent = new Intent(parentActivity, StepsNavigationActivity.class);
        intent.putParcelableArrayListExtra(ARGUMENT_STEPS_KEY, stepsToBeBundled);
        intent.putExtra(ARGUMENT_CURRENT_STEP_KEY, currentStepId);

        ActivityCompat.startActivity(parentActivity, intent, null);
    }

    @Inject
    @Named("stepSelectedEventBus")
    RxEventBus<Integer> stepSelectedEventBus;

    @BindView(R.id.step_details_view_pager)
    ViewPager stepDetailViewPager;

    @Nullable
    @BindView(R.id.step_details_next_button)
    Button nextStepButton;

    @Nullable
    @BindView(R.id.step_details_previous_button)
    Button previousStepButton;

    @Nullable
    @BindView(R.id.step_details_step_list_container)
    FrameLayout stepsListContainer;

    private static final String LAST_KNOWN_STEPS = "steps";
    private static final String LAST_KNOWN_CURRENT_STEP_LIST_POSITION = "current_step_list_position";
    private static final String STEPS_LIST_FRAGMENT_INSTANCE_KEY = "steps_list_fragment_instance";

    private static final int INITIAL_STEP_POSITION = 0;

    private List<Step> steps;
    private int currentStepListPosition = INITIAL_STEP_POSITION;

    private StepsListFragment stepsListFragment;
    private StepsNavigationViewPagerAdapter stepsNavigationViewPagerAdapter;
    private Disposable stepSelectedEventBusDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_details_navigation_activity);
        Injector.applicationComponent().inject(this);
        ButterKnife.bind(this);
        setUpViewForFullscreen();
        processExtras(getIntent().getExtras());
        processSavedInstanceState(savedInstanceState);
        setUpViewForMultipane(savedInstanceState);
        setUpActionBar();
        setUpViewPager();
    }

    private void setUpViewForFullscreen() {
        if (isFullscreenLayout() && !isMultipaneLayout()) {
            View decorView = getWindow().getDecorView();
            int uiOptions = decorView.getSystemUiVisibility();
            uiOptions ^= View.SYSTEM_UI_FLAG_LOW_PROFILE;
            uiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
            uiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            uiOptions ^= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            uiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            uiOptions ^= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void setUpViewForMultipane(@Nullable Bundle savedInstanceState) {
        if (isMultipaneLayout()) {
            if (savedInstanceState != null) {
                stepsListFragment = (StepsListFragment) getSupportFragmentManager()
                        .getFragment(savedInstanceState, STEPS_LIST_FRAGMENT_INSTANCE_KEY);
            }

            if (stepsListFragment == null) {
                stepsListFragment = StepsListFragment.newInstance(steps, currentStepListPosition);
                stepsListFragment.setHighlightSelected(true);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_details_step_list_container, stepsListFragment)
                        .commit();
            }
        }
    }

    private void processExtras(Bundle extras) {
        if (extras != null) {
            steps = extras.getParcelableArrayList(ARGUMENT_STEPS_KEY);
            currentStepListPosition = extras.getInt(ARGUMENT_CURRENT_STEP_KEY);
        }
    }

    private void processSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(LAST_KNOWN_STEPS);
            currentStepListPosition = savedInstanceState.getInt(LAST_KNOWN_CURRENT_STEP_LIST_POSITION);
        }
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        if (isFullscreenLayout()) {
            actionBar.hide();
        } else {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.step_details_recipe_steps);
        }
    }

    private void setUpViewPager() {
        stepsNavigationViewPagerAdapter = new StepsNavigationViewPagerAdapter(getSupportFragmentManager(), steps);
        stepDetailViewPager.setAdapter(stepsNavigationViewPagerAdapter);
        navigateToStepInPosition(currentStepListPosition);
    }

    @Override
    protected void onResume() {
        super.onResume();
        stepSelectedEventBusDisposable = stepSelectedEventBus
                .eventBus()
                .subscribe(this::onStepSelected);
    }

    private void onStepSelected(Integer stepPosition) {
        navigateToStepInPosition(stepPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stepSelectedEventBusDisposable.dispose();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        ArrayList<Step> stepsToBeBundled = new ArrayList<>(steps.size());
        stepsToBeBundled.addAll(steps);

        outState.putParcelableArrayList(LAST_KNOWN_STEPS, stepsToBeBundled);
        outState.putInt(LAST_KNOWN_CURRENT_STEP_LIST_POSITION, stepDetailViewPager.getCurrentItem());

        if (isMultipaneLayout() && stepsListFragment != null) {
            getSupportFragmentManager().putFragment(outState, STEPS_LIST_FRAGMENT_INSTANCE_KEY, stepsListFragment);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExoPlayerManager.getInstance().clearAllPlayers();
    }

    @Optional
    @OnClick(R.id.step_details_next_button)
    public void nextStep(View view) {
        navigateToStepInPosition(currentStepListPosition + 1);
    }

    @Optional
    @OnClick(R.id.step_details_previous_button)
    public void previousStep(View view) {
        navigateToStepInPosition(currentStepListPosition - 1);
    }

    private void navigateToStepInPosition(int position) {
        stopCurrentMedia();
        currentStepListPosition = position;
        stepDetailViewPager.setCurrentItem(position);
        if (isMultipaneLayout()) {
            stepsListFragment.setSelectedItemPosition(position);
        }
        onStepChanged();
    }

    private void stopCurrentMedia() {
        int currentStepId = steps.get(stepDetailViewPager.getCurrentItem()).id();
        ExoPlayerManager.getInstance().stopPlayerFor(currentStepId);
    }

    private void onStepChanged() {
        if (isFullscreenLayout() || isMultipaneLayout()) {
            return;
        }

        if (currentStepListPosition == INITIAL_STEP_POSITION) {
            previousStepButton.setVisibility(INVISIBLE);
        } else {
            previousStepButton.setVisibility(VISIBLE);
        }

        if (currentStepListPosition == steps.size() - 1) {
            nextStepButton.setVisibility(INVISIBLE);
        } else {
            nextStepButton.setVisibility(VISIBLE);
        }
    }

    private boolean isFullscreenLayout() {
        return (previousStepButton == null || nextStepButton == null) && !isMultipaneLayout();
    }

    private boolean isMultipaneLayout() {
        return getResources().getBoolean(R.bool.multiPaneMode);
    }

}
