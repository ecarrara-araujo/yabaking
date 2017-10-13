package br.com.ecarrara.yabaking.recipes.data.datasource;

import android.support.annotation.NonNull;

public interface RecipesUserPreferencesDataSource {

    Integer getRecipeIdForWidgetDisplay();

    void setRecipeForWidgetDisplay(@NonNull Integer recipeId);

}
