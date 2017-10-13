package br.com.ecarrara.yabaking.recipes.data.datasource.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import br.com.ecarrara.yabaking.recipes.data.datasource.RecipesUserPreferencesDataSource;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;

public class RecipesUserPreferencesDataSourceImpl implements RecipesUserPreferencesDataSource {

    private static final String SHARED_PREFERENCES_FILE = "yabaking_preferences";

    private static final String SHARED_PREFERENCES_WIDGET_RECIPE_ID_KEY = "widget_recipe_id";

    private Context context;
    private SharedPreferences preferences;

    public RecipesUserPreferencesDataSourceImpl(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    @Override
    public Integer getRecipeIdForWidgetDisplay() {
        return preferences.getInt(SHARED_PREFERENCES_WIDGET_RECIPE_ID_KEY, Recipe.INVALID_ID);
    }

    @Override
    public void setRecipeForWidgetDisplay(@NonNull Integer recipeId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SHARED_PREFERENCES_WIDGET_RECIPE_ID_KEY, recipeId);
        editor.apply();
    }
}
