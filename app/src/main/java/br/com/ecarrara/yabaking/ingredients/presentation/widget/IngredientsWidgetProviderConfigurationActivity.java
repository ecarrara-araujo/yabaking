package br.com.ecarrara.yabaking.ingredients.presentation.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.core.di.Injector;
import br.com.ecarrara.yabaking.core.presentation.LoadDataActivity;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class IngredientsWidgetProviderConfigurationActivity
        extends LoadDataActivity<List<SimpleRecipeListItemViewModel>> {

    @Inject
    IngredientsWidgetProviderConfigurationPresenter ingredientsWidgetProviderConfigurationPresenter;

    @BindView(R.id.ingredients_widget_configuration_recipes_list_recycler_view)
    RecyclerView recipesListView;

    private static final String LAST_KNOWN_RECIPES_LIST_POSITION_KEY = "last_known_recipes_list_position";
    private static final String LAST_KNOWN_APP_WIDGET_ID_KEY = "last_known_app_widget_id_position";
    private static final int DEFAULT_RECIPES_LIST_INITIAL_POSITION = 0;

    private int lastKnownRecipesListPosition = DEFAULT_RECIPES_LIST_INITIAL_POSITION;
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private SimpleRecipeListAdapter recipesListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        processSavedInstanceState(savedInstanceState);
        processExtras(getIntent().getExtras());
        setContentView(R.layout.ingredients_widget_configuration_activity);
        initialize();
    }

    private void processSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            lastKnownRecipesListPosition = savedInstanceState.getInt(
                    LAST_KNOWN_RECIPES_LIST_POSITION_KEY, DEFAULT_RECIPES_LIST_INITIAL_POSITION);
            appWidgetId = savedInstanceState.getInt(
                    LAST_KNOWN_APP_WIDGET_ID_KEY, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
    }

    private void processExtras(Bundle extras) {
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }

    private void initialize() {
        ButterKnife.bind(this);
        Injector.applicationComponent().inject(this);
        setUpActionBar();
        setupRecyclerView();
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.ingredients_widget_select_a_recipe);
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recipesListView.getContext(), layoutManager.getOrientation());

        recipesListView.addItemDecoration(dividerItemDecoration);
        recipesListAdapter = new SimpleRecipeListAdapter(ingredientsWidgetProviderConfigurationPresenter);
        recipesListView.setAdapter(recipesListAdapter);
        recipesListView.setLayoutManager(layoutManager);
        recipesListView.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.ingredientsWidgetProviderConfigurationPresenter.attachTo(this, null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(LAST_KNOWN_RECIPES_LIST_POSITION_KEY,
                ((LinearLayoutManager) recipesListView.getLayoutManager()).findFirstVisibleItemPosition());
        outState.putInt(LAST_KNOWN_APP_WIDGET_ID_KEY, appWidgetId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        this.ingredientsWidgetProviderConfigurationPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void showContent(List<SimpleRecipeListItemViewModel> viewData) {
        this.recipesListView.setVisibility(VISIBLE);
        this.recipesListAdapter.setRecipes(viewData);
        configureListPosition();
    }

    private void configureListPosition() {
        if (recipesListAdapter.getItemCount() >= lastKnownRecipesListPosition) {
            recipesListView.scrollToPosition(lastKnownRecipesListPosition);
        }
    }

    @Override
    public void hideContent() {
        this.recipesListView.setVisibility(GONE);
    }

    public void notifyWidget(Recipe recipe) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);

        int[] widgetsIds = widgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
        IngredientsWidgetProvider.update(this, widgetManager, widgetsIds, recipe);

        Intent resultIntent = new Intent();
        resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}

