package br.com.ecarrara.yabaking.core.di;

import javax.inject.Singleton;

import br.com.ecarrara.yabaking.ingredients.presentation.listing.IngredientsListFragment;
import br.com.ecarrara.yabaking.ingredients.presentation.widget.IngredientsRefreshService;
import br.com.ecarrara.yabaking.ingredients.presentation.widget.IngredientsWidgetProviderConfigurationActivity;
import br.com.ecarrara.yabaking.recipes.di.RecipesModule;
import br.com.ecarrara.yabaking.recipes.presentation.listing.RecipesListActivity;
import br.com.ecarrara.yabaking.steps.presentation.listing.StepsListFragment;
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

}
