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

import java.util.ArrayList;
import java.util.List;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.steps.domain.entity.Step;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    public static final String LAST_KNOWN_STEPS = "steps";
    public static final String LAST_KNOWN_CURRENT_STEP_LIST_POSITION = "current_step_list_position";

    @BindView(R.id.step_details_view_pager)
    ViewPager stepDetailViewPager;

    @BindView(R.id.step_details_next_button)
    Button nextStepButton;

    @BindView(R.id.step_details_previous_button)
    Button previousStepButton;

    private static final int INITIAL_STEP_POSITION = 0;

    private List<Step> steps;
    private int currentStepListPosition = INITIAL_STEP_POSITION;

    private StepsNavigationViewPagerAdapter stepsNavigationViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_details_navigation_activity);
        ButterKnife.bind(this);
        processExtras(getIntent().getExtras());
        processSavedInstanceState(savedInstanceState);
        setUpActionBar();
        setUpViewPager();
    }

    private void processExtras(Bundle extras) {
        if(extras != null) {
            steps = extras.getParcelableArrayList(ARGUMENT_STEPS_KEY);
            currentStepListPosition = extras.getInt(ARGUMENT_CURRENT_STEP_KEY);
        }
    }

    private void processSavedInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null && steps == null) {
            steps = savedInstanceState.getParcelableArrayList(LAST_KNOWN_STEPS);
            currentStepListPosition = savedInstanceState.getInt(LAST_KNOWN_CURRENT_STEP_LIST_POSITION);
        }
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
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
    public void onSaveInstanceState(Bundle outState) {
        ArrayList<Step> stepsToBeBundled = new ArrayList<>(steps.size());
        stepsToBeBundled.addAll(steps);

        outState.putParcelableArrayList(LAST_KNOWN_STEPS, stepsToBeBundled);
        outState.putInt(LAST_KNOWN_CURRENT_STEP_LIST_POSITION, stepDetailViewPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.step_details_next_button)
    public void nextStep(View view) {
        navigateToStepInPosition(currentStepListPosition + 1);
    }

    @OnClick(R.id.step_details_previous_button)
    public void previousStep(View view) {
        navigateToStepInPosition(currentStepListPosition - 1);
    }

    private void navigateToStepInPosition(int position) {
        currentStepListPosition = position;
        stepDetailViewPager.setCurrentItem(position);
        onStepChanged();
    }

    private void onStepChanged() {
        if(currentStepListPosition == INITIAL_STEP_POSITION) {
            previousStepButton.setVisibility(INVISIBLE);
        } else {
            previousStepButton.setVisibility(VISIBLE);
        }

        if(currentStepListPosition == steps.size() - 1) {
            nextStepButton.setVisibility(INVISIBLE);
        } else {
            nextStepButton.setVisibility(VISIBLE);
        }
    }

}
