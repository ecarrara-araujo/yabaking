package br.com.ecarrara.yabaking.recipes.data;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.ecarrara.yabaking.recipes.data.datasource.RecipesLocalDataSource;
import br.com.ecarrara.yabaking.recipes.data.datasource.RecipesRemoteDataSource;
import br.com.ecarrara.yabaking.recipes.data.datasource.RecipesUserPreferencesDataSource;
import br.com.ecarrara.yabaking.recipes.domain.RecipesRepository;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class RecipesRepositoryImpl implements RecipesRepository {

    private RecipesRemoteDataSource recipesRemoteDataSource;
    private RecipesLocalDataSource recipesLocalDataSource;
    private RecipesUserPreferencesDataSource recipesUserPreferencesDataSource;

    public RecipesRepositoryImpl(RecipesRemoteDataSource recipesRemoteDataSource,
                                 RecipesLocalDataSource recipesLocalDataSource,
                                 RecipesUserPreferencesDataSource recipesUserPreferencesDataSource) {
        this.recipesRemoteDataSource = recipesRemoteDataSource;
        this.recipesLocalDataSource = recipesLocalDataSource;
        this.recipesUserPreferencesDataSource = recipesUserPreferencesDataSource;
    }

    @Override
    public Single<List<Recipe>> list() {
        return Maybe
                .concat(recipesLocalDataSource.list(), getFromRemoteDataSourceAndStoreToCache())
                .firstOrError();
    }

    private Maybe<List<Recipe>> getFromRemoteDataSourceAndStoreToCache() {
        return recipesRemoteDataSource
                .list()
                .flatMapMaybe(recipes -> recipesLocalDataSource.save(recipes)
                        .andThen(recipesLocalDataSource.list())
                );
    }

    @Override
    public Single<Recipe> get(@NonNull Integer recipeId) {
        return this.recipesLocalDataSource.get(recipeId).toSingle();
    }

    @Override
    public Single<Recipe> getRecipeForWidgetDisplay() {
        return Single.defer(() -> {
            Integer recipeForDisplayId = recipesUserPreferencesDataSource.getRecipeIdForWidgetDisplay();
            if(recipeForDisplayId != Recipe.INVALID_ID) {
                return recipesLocalDataSource.get(recipeForDisplayId).toSingle();
            } else {
                return Single.error(new RuntimeException("Recipe for display not found"));
            }
        });
    }

    @Override
    public Completable setRecipeForWidgetDisplay(@NonNull Recipe recipe) {
        return Completable.defer(() -> {
            recipesUserPreferencesDataSource.setRecipeForWidgetDisplay(recipe.id());
            return Completable.complete();
        });
    }

}
