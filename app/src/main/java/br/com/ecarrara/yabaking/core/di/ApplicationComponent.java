package br.com.ecarrara.yabaking.core.di;

import javax.inject.Singleton;

import br.com.ecarrara.yabaking.ingredients.presentation.listing.IngredientsListFragment;
import br.com.ecarrara.yabaking.ingredients.presentation.widget.IngredientsRefreshService;
import br.com.ecarrara.yabaking.ingredients.presentation.widget.IngredientsWidgetProviderConfigurationActivity;
import br.com.ecarrara.yabaking.ingredients.presentation.widget.RemoteIngredientsListViewsFactory;
import br.com.ecarrara.yabaking.recipes.di.RecipesModule;
import br.com.ecarrara.yabaking.recipes.presentation.details.RecipeDetailsActivity;
import br.com.ecarrara.yabaking.recipes.presentation.listing.RecipesListActivity;
import br.com.ecarrara.yabaking.steps.presentation.listing.StepsListAdapter;
import br.com.ecarrara.yabaking.steps.presentation.listing.StepsListFragment;
import br.com.ecarrara.yabaking.steps.presentation.navigating.StepsNavigationActivity;
import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkingModule.class,
        RecipesModule.class
})
public interface ApplicationComponent {

    void inject(RecipesListActivity recipesListActivity);

    void inject(IngredientsListFragment ingredientsListFragment);

    void inject(StepsListFragment stepsListFragment);

    void inject(IngredientsRefreshService ingredientsRefreshService);

    void inject(IngredientsWidgetProviderConfigurationActivity ingredientsWidgetProviderConfigurationActivity);

    void inject(RemoteIngredientsListViewsFactory remoteIngredientsListViewsFactory);

    void inject(RecipeDetailsActivity recipeDetailsActivity);

    void inject(StepsListAdapter stepsListAdapter);

    void inject(StepsNavigationActivity stepsNavigationActivity);

}
