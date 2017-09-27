package br.com.ecarrara.yabaking.recipes.di;

import javax.inject.Singleton;

import br.com.ecarrara.yabaking.recipes.data.FakeRecipeRepository;
import br.com.ecarrara.yabaking.recipes.domain.RecipesRepository;
import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class RecipesModule {

    @Provides
    @Singleton
    public RecipesRepository providesRecipeRepository() {
        return new FakeRecipeRepository();
    }

}
