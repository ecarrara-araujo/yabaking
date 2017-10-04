package br.com.ecarrara.yabaking.recipes.domain;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface RecipesRepository {

    Single<List<Recipe>> list();

    Single<Recipe> get(@NonNull Integer recipeId);

    Single<Recipe> getRecipeForWidgetDisplay();

    Completable setRecipeForWidgetDisplay(@NonNull Recipe recipe);

}
