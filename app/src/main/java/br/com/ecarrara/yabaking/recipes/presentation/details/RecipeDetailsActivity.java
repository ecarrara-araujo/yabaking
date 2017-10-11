package br.com.ecarrara.yabaking.recipes.presentation.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.core.di.Injector;
import br.com.ecarrara.yabaking.core.utils.RxEventBus;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import br.com.ecarrara.yabaking.steps.presentation.navigating.StepsNavigationActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String ARGUMENT_RECIPE = "recipe";

    public static void navigate(AppCompatActivity parentActivity, Recipe recipe) {
        Intent intent = new Intent(parentActivity, RecipeDetailsActivity.class);
        intent.putExtra(ARGUMENT_RECIPE, recipe);

        ActivityCompat.startActivity(parentActivity, intent, null);
    }

    @Inject
    @Named("stepSelectedEventBus")
    RxEventBus<Integer> stepSelectedEventBus;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recipe_detail_collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.recipe_details_view_pager)
    ViewPager recipeDetailsViewPager;

    @BindView(R.id.recipe_detail_tabs)
    TabLayout recipeDetailsTabs;

    private static final String LAST_KNOWN_RECIPE_KEY = "recipe";

    private Recipe recipe;
    private RecipeDetailsViewPagerAdapter recipeDetailsViewPagerAdapter;

    private Disposable stepSelectedEventBusDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_details_activity);
        Injector.applicationComponent().inject(this);
        ButterKnife.bind(this);
        processExtras(getIntent().getExtras());
        processSavedInstanceState(savedInstanceState);
        setUpActionBar();
        setUpCollapsingToolbarLayout();
        setUpViewPager();
    }

    private void processExtras(Bundle extras) {
        if (extras != null) {
            recipe = extras.getParcelable(ARGUMENT_RECIPE);
        }
    }

    private void processSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && recipe == null) {
            recipe = savedInstanceState.getParcelable(LAST_KNOWN_RECIPE_KEY);
        }
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpCollapsingToolbarLayout() {
        collapsingToolbarLayout.setTitle(recipe.name());
    }

    private void setUpViewPager() {
        recipeDetailsViewPagerAdapter = new RecipeDetailsViewPagerAdapter(
                getSupportFragmentManager(), this, recipe);
        recipeDetailsViewPager.setAdapter(recipeDetailsViewPagerAdapter);
        recipeDetailsTabs.setupWithViewPager(recipeDetailsViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        stepSelectedEventBusDisposable = stepSelectedEventBus
                .eventBus()
                .subscribe(this::onStepSelected);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(LAST_KNOWN_RECIPE_KEY, recipe);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        stepSelectedEventBusDisposable.dispose();
    }

    private void onStepSelected(Integer stepPosition) {
        StepsNavigationActivity.navigate(this, recipe.steps(), stepPosition);
    }

}
