package br.com.ecarrara.yabaking.recipes.presentation.details;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.ingredients.presentation.IngredientsListFragment;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;

class RecipeDetailsViewPagerAdapter extends FragmentStatePagerAdapter {

    static final int PAGE_POSITION_INGREDIENTS = 0;
    static final int PAGE_POSITION_STEPS = 1;

    private static final int NUMBER_OF_PAGES = 1;

    private Context context;
    private Recipe recipe;

    public RecipeDetailsViewPagerAdapter(FragmentManager fragmentManager, Context context, Recipe recipe) {
        this(fragmentManager);
        this.context =context;
        this.recipe = recipe;
    }

    public RecipeDetailsViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PAGE_POSITION_INGREDIENTS:
                return IngredientsListFragment.newInstance(recipe.ingredients());
            case PAGE_POSITION_STEPS:
            default:
                throw new UnsupportedOperationException("Page position not supported: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case PAGE_POSITION_INGREDIENTS:
                return context.getResources().getString(R.string.recipe_details_ingredient);
            case PAGE_POSITION_STEPS:
                return context.getResources().getString(R.string.recipe_details_steps);
            default:
                throw new UnsupportedOperationException("Page position not supported: " + position);
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }

}
