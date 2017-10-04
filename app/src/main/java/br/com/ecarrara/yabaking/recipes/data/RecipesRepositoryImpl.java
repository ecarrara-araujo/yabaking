package br.com.ecarrara.yabaking.recipes.data;

import android.support.annotation.NonNull;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import br.com.ecarrara.yabaking.recipes.data.datasource.RecipesRemoteDataSource;
import br.com.ecarrara.yabaking.recipes.domain.RecipesRepository;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import io.reactivex.Completable;
import io.reactivex.Single;

public class RecipesRepositoryImpl implements RecipesRepository {

    private RecipesRemoteDataSource recipesRemoteDataSource;
    private List<Recipe> recipes;
    private Recipe recipeForDisplay = Recipe.builder().build();

    public RecipesRepositoryImpl(RecipesRemoteDataSource recipesRemoteDataSource) {
        this.recipesRemoteDataSource = recipesRemoteDataSource;
        this.recipes = new ArrayList<>();
    }

    @Override
    public Single<List<Recipe>> list() {
        return Single.defer(() -> {
            if(!recipes.isEmpty()) {
                return Single.just(recipes);
            } else {
                return recipesRemoteDataSource
                        .list()
                        .doOnSuccess(recipesFromRemote -> recipes.addAll(recipesFromRemote));
            }
        });
    }

    @Override
    public Single<Recipe> get(@NonNull Integer recipeId) {
        return Single.defer(() -> {
            if(!recipes.isEmpty()) {
                return Single.just(
                        Stream.of(recipes)
                                .filter(recipe -> recipe.id().equals(recipeId))
                                .findFirst()
                                .get()
                );
            } else {
                return Single.error(new RuntimeException("Recipe not found id: " + recipeId));
            }
        });
    }

    @Override
    public Single<Recipe> getRecipeForWidgetDisplay() {
        return Single.defer(() -> {
            if(recipeForDisplay != null) {
                return Single.just(recipeForDisplay);
            } else {
                return Single.error(new RuntimeException("Recipe for display not found"));
            }
        });
    }

    @Override
    public Completable setRecipeForWidgetDisplay(@NonNull Recipe recipe) {
        return Completable.defer(() -> {
            recipeForDisplay = recipe;
            return Completable.complete();
        });
    }

}
