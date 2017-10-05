package br.com.ecarrara.yabaking.recipes.di;

import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Singleton;

import br.com.ecarrara.yabaking.core.data.DBFlowRecipeDatabase;
import br.com.ecarrara.yabaking.recipes.data.RecipesRepositoryImpl;
import br.com.ecarrara.yabaking.recipes.data.datasource.RecipesLocalDataSource;
import br.com.ecarrara.yabaking.recipes.data.datasource.RecipesRemoteDataSource;
import br.com.ecarrara.yabaking.recipes.data.datasource.RecipesUserPreferencesDataSource;
import br.com.ecarrara.yabaking.recipes.data.datasource.dbflow.DBFlowLocalDataSource;
import br.com.ecarrara.yabaking.recipes.data.datasource.preferences.RecipesUserPreferencesDataSourceImpl;
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
            RecipesRemoteDataSource recipesRemoteDataSource,
            RecipesLocalDataSource recipesLocalDataSource,
            RecipesUserPreferencesDataSource recipesUserPreferencesDataSource) {
        return new RecipesRepositoryImpl(
                recipesRemoteDataSource, recipesLocalDataSource, recipesUserPreferencesDataSource);
    }

    @Provides
    @Singleton
    public RecipesRemoteDataSource providesRecipesRemoteDataSource(
            UdacityRecipesApi udacityRecipesApi) {
        return new UdacityRecipesRemoteDataSource(udacityRecipesApi);
    }

    @Provides
    @Singleton
    public RecipesLocalDataSource providesRecipesLocalDataSource() {
        return new DBFlowLocalDataSource(FlowManager.getWritableDatabase(DBFlowRecipeDatabase.class));
    }

    @Provides
    @Singleton
    public RecipesUserPreferencesDataSource providesRecipesUserPreferencesDataSource(
            Context context) {
        return new RecipesUserPreferencesDataSourceImpl(context);
    }

}
