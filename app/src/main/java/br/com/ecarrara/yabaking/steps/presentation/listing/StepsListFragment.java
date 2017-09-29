package br.com.ecarrara.yabaking.steps.presentation.listing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.core.di.Injector;
import br.com.ecarrara.yabaking.core.presentation.LoadDataFragment;
import br.com.ecarrara.yabaking.steps.domain.entity.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class StepsListFragment extends LoadDataFragment<List<String>> {

    private static final String ARGUMENT_STEPS_LIST = "steps_list";

    /**
     * Create a new Steps List View for the informed steps
     * @param steps to be rendered by the view
     * @return the prepared Ingredients List
     */
    public static StepsListFragment newInstance(List<Step> steps) {
        ArrayList<Step> stepsToBeBundled = new ArrayList<>(steps.size());
        stepsToBeBundled.addAll(steps);
        StepsListFragment stepsListFragment = new StepsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_STEPS_LIST, stepsToBeBundled);
        stepsListFragment.setArguments(args);
        return stepsListFragment;
    }

    @Inject
    StepsListPresenter ingredientsListPresenter;

    @BindView(R.id.steps_list_recycler_view)
    RecyclerView stepsListView;

    private static final String LAST_KNOWN_STEPS_LIST_POSITION_KEY = "last_known_ingredients_list_position";
    private static final int DEFAULT_STEPS_LIST_INITIAL_POSITION = 0;

    private int lastKnownStepsListPosition = DEFAULT_STEPS_LIST_INITIAL_POSITION;
    private StepsListAdapter stepsListAdapter;
    private ArrayList<Step> steps;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processArguments(getArguments());
        processSavedInstanceState(savedInstanceState);
    }

    private void processArguments(Bundle arguments) {
        if (arguments != null) {
            steps = getArguments().getParcelableArrayList(ARGUMENT_STEPS_LIST);
        }
    }

    private void processSavedInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            lastKnownStepsListPosition = savedInstanceState.getInt(
                    LAST_KNOWN_STEPS_LIST_POSITION_KEY, DEFAULT_STEPS_LIST_INITIAL_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.steps_list_fragment, container, false);
        ButterKnife.bind(this, inflatedView);
        initialize();
        return inflatedView;
    }

    private void initialize() {
        Injector.applicationComponent().inject(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        stepsListAdapter = new StepsListAdapter();
        stepsListView.setAdapter(stepsListAdapter);
        stepsListView.setLayoutManager(layoutManager);
        stepsListView.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.ingredientsListPresenter.attachTo(this, steps);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(LAST_KNOWN_STEPS_LIST_POSITION_KEY,
                ((LinearLayoutManager) stepsListView.getLayoutManager()).findFirstVisibleItemPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        this.ingredientsListPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void showContent(List<String> viewData) {
        this.stepsListView.setVisibility(VISIBLE);
        this.stepsListAdapter.setSteps(viewData);
        configureListPosition();
    }

    private void configureListPosition() {
        if(stepsListAdapter.getItemCount() >= lastKnownStepsListPosition) {
            stepsListView.scrollToPosition(lastKnownStepsListPosition);
        }
    }

    @Override
    public void hideContent() {
        this.stepsListView.setVisibility(GONE);
    }

}
