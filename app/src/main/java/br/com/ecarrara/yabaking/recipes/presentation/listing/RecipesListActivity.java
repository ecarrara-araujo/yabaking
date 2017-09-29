package br.com.ecarrara.yabaking.recipes.presentation.listing;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.core.di.Injector;
import br.com.ecarrara.yabaking.core.presentation.LoadDataActivity;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import br.com.ecarrara.yabaking.recipes.presentation.details.RecipeDetailsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class RecipesListActivity extends LoadDataActivity<List<RecipeListItemViewModel>>
    implements RecipesListView {

    @Inject
    RecipesListPresenter recipesListPresenter;

    @BindView(R.id.recipes_list_recycler_view)
    RecyclerView recipesListView;

    private static final String LAST_KNOWN_RECIPES_LIST_POSITION_KEY = "last_known_recipes_list_position";
    private static final int DEFAULT_RECIPES_LIST_INITIAL_POSITION = 0;

    private int lastKnownRecipesListPosition = DEFAULT_RECIPES_LIST_INITIAL_POSITION;
    private RecipesListAdapter recipesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processSavedInstanceState(savedInstanceState);
        setContentView(R.layout.recipes_list_activity);
        ButterKnife.bind(this);
        initialize();
    }

    private void processSavedInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            lastKnownRecipesListPosition = savedInstanceState.getInt(
                    LAST_KNOWN_RECIPES_LIST_POSITION_KEY, DEFAULT_RECIPES_LIST_INITIAL_POSITION);
        }
    }

    private void initialize() {
        Injector.applicationComponent().inject(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        final int NUMBER_OF_COLUMNS_IN_GRID = computeNumberOfColumns();

        GridLayoutManager layoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS_IN_GRID);
        recipesListAdapter = new RecipesListAdapter(this, recipesListPresenter);
        recipesListView.setAdapter(recipesListAdapter);
        recipesListView.setLayoutManager(layoutManager);
        recipesListView.setHasFixedSize(true);
    }

    private int computeNumberOfColumns() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / getResources().getDimension(R.dimen.recipes_list_item_width_ratio));
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.recipesListPresenter.attachTo(this, null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(LAST_KNOWN_RECIPES_LIST_POSITION_KEY,
                ((GridLayoutManager)recipesListView.getLayoutManager()).findFirstVisibleItemPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        this.recipesListPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void showContent(List<RecipeListItemViewModel> viewData) {
        hideLoading();
        hideError();
        hideRetry();
        this.recipesListView.setVisibility(VISIBLE);
        this.recipesListAdapter.setRecipeListItemViewModels(viewData);
        configureListPosition();
    }

    private void configureListPosition() {
        if(recipesListAdapter.getItemCount() >= lastKnownRecipesListPosition) {
            recipesListView.scrollToPosition(lastKnownRecipesListPosition);
        }
    }

    @Override
    public void hideContent() {
        this.recipesListView.setVisibility(GONE);
    }

    @Override
    public void navigateToRecipeDetail(Recipe recipe) {
        RecipeDetailsActivity.navigate(this, recipe);
    }

}
