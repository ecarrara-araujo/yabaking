package br.com.ecarrara.yabaking.recipes.di;

import javax.inject.Singleton;

import br.com.ecarrara.yabaking.recipes.data.RecipesRepositoryImpl;
import br.com.ecarrara.yabaking.recipes.data.datasource.RecipesRemoteDataSource;
import br.com.ecarrara.yabaking.recipes.data.datasource.rest.UdacityRecipesApi;
import br.com.ecarrara.yabaking.recipes.data.datasource.rest.UdacityRecipesRemoteDataSource;
import br.com.ecarrara.yabaking.recipes.domain.RecipesRepository;
import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class RecipesModule {

    @Provides
    @Singleton
    public RecipesRepository providesRecipeRepository(
            RecipesRemoteDataSource recipesRemoteDataSource) {
        return new RecipesRepositoryImpl(recipesRemoteDataSource);
    }

    @Provides
    @Singleton
    public RecipesRemoteDataSource providesRecipesRemoteDataSource(
            UdacityRecipesApi udacityRecipesApi) {
        return new UdacityRecipesRemoteDataSource(udacityRecipesApi);
    }

}
