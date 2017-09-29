package br.com.ecarrara.yabaking.core.di;

import javax.inject.Singleton;

import br.com.ecarrara.yabaking.ingredients.presentation.IngredientsListFragment;
import br.com.ecarrara.yabaking.recipes.di.RecipesModule;
import br.com.ecarrara.yabaking.recipes.presentation.listing.RecipesListActivity;
import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        RecipesModule.class
})
public interface ApplicationComponent {

    void inject(RecipesListActivity recipesListActivity);

    void inject(IngredientsListFragment ingredientsListFragment);

}
