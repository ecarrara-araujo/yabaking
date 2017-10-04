package br.com.ecarrara.yabaking.recipes.domain;

import java.util.List;

import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface RecipesRepository {

    Single<List<Recipe>> list();

    Single<Recipe> get(Integer recipeId);

    Single<Recipe> getRecipeForWidgetDisplay();

    Completable setRecipeForWidgetDisplay(Recipe recipe);

}
