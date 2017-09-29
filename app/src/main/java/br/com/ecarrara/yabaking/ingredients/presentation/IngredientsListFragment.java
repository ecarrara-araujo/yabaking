package br.com.ecarrara.yabaking.ingredients.presentation;

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
import br.com.ecarrara.yabaking.core.presentation.LoadDataFragment;
import br.com.ecarrara.yabaking.ingredients.domain.entity.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class IngredientsListFragment extends LoadDataFragment<List<String>> {

    private static final String ARGUMENT_INGREDIENTS_LIST = "ingredients_list";

    /**
     * Create a new Ingredients List View for the informed ingredients
     * @param ingredients to be rendered by the view
     * @return the prepared Ingredients List
     */
    public static IngredientsListFragment newInstance(List<Ingredient> ingredients) {
        ArrayList<Ingredient> ingredientsToBeBundled = new ArrayList<>(ingredients.size());
        ingredientsToBeBundled.addAll(ingredients);
        IngredientsListFragment ingredientsListFragment = new IngredientsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_INGREDIENTS_LIST, ingredientsToBeBundled);
        ingredientsListFragment.setArguments(args);
        return ingredientsListFragment;
    }

    @Inject
    IngredientsListPresenter ingredientsListPresenter;

    @BindView(R.id.ingredients_list_recycler_view)
    RecyclerView ingredientsListView;

    private static final String LAST_KNOWN_INGREDIENTS_LIST_POSITION_KEY = "last_known_ingredients_list_position";
    private static final int DEFAULT_INGREDIENTS_LIST_INITIAL_POSITION = 0;

    private int lastKnownIngredientsListPosition = DEFAULT_INGREDIENTS_LIST_INITIAL_POSITION;
    private IngredientsListAdapter ingredientsListAdapter;
    private ArrayList<Ingredient> ingredients;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processArguments(getArguments());
        processSavedInstanceState(savedInstanceState);
    }

    private void processArguments(Bundle arguments) {
        if (arguments != null) {
            ingredients = getArguments().getParcelableArrayList(ARGUMENT_INGREDIENTS_LIST);
        }
    }

    private void processSavedInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            lastKnownIngredientsListPosition = savedInstanceState.getInt(
                    LAST_KNOWN_INGREDIENTS_LIST_POSITION_KEY, DEFAULT_INGREDIENTS_LIST_INITIAL_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.ingredients_list_fragment, container, false);
        ButterKnife.bind(this, inflatedView);
        initialize();
        return inflatedView;
    }

    private void initialize() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        ingredientsListAdapter = new IngredientsListAdapter();
        ingredientsListView.setAdapter(ingredientsListAdapter);
        ingredientsListView.setLayoutManager(layoutManager);
        ingredientsListView.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.ingredientsListPresenter.attachTo(this, ingredients);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(LAST_KNOWN_INGREDIENTS_LIST_POSITION_KEY,
                ((LinearLayoutManager)ingredientsListView.getLayoutManager()).findFirstVisibleItemPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        this.ingredientsListPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void showContent(List<String> viewData) {
        this.ingredientsListView.setVisibility(VISIBLE);
        this.ingredientsListAdapter.setIngredients(viewData);
        configureListPosition();
    }

    private void configureListPosition() {
        if(ingredientsListAdapter.getItemCount() >= lastKnownIngredientsListPosition) {
            ingredientsListView.scrollToPosition(lastKnownIngredientsListPosition);
        }
    }

    @Override
    public void hideContent() {
        this.ingredientsListView.setVisibility(GONE);
    }

}
