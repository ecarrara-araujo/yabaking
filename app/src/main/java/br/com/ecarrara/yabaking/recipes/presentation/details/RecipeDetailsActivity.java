package br.com.ecarrara.yabaking.recipes.presentation.details;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String ARGUMENT_RECIPE = "recipe";

    public static void navigate(AppCompatActivity parentActivity, Recipe recipe) {
        Intent intent = new Intent(parentActivity, RecipeDetailsActivity.class);
        intent.putExtra(ARGUMENT_RECIPE, recipe);

        ActivityCompat.startActivity(parentActivity, intent, null);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recipe_detail_collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.recipe_details_view_pager)
    ViewPager recipeDetailsViewPager;

    @BindView(R.id.recipe_detail_tabs)
    TabLayout recipeDetailsTabs;

    private Recipe recipe;
    private RecipeDetailsViewPagerAdapter recipeDetailsViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_details_activity);
        ButterKnife.bind(this);
        processExtras(getIntent().getExtras());
        setUpActionBar();
        setUpCollapsingToolbarLayout();
        setUpViewPager();
    }

    private void processExtras(Bundle extras) {
        if(extras != null) {
            recipe = extras.getParcelable(ARGUMENT_RECIPE);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
