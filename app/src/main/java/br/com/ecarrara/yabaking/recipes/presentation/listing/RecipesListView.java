package br.com.ecarrara.yabaking.recipes.presentation.listing;

import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;

public interface RecipesListView {

    void navigateToRecipeDetail(Recipe recipe);

}
