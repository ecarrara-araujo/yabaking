package br.com.ecarrara.yabaking.ingredients.presentation.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.ingredients.presentation.IngredientFormatter;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import br.com.ecarrara.yabaking.recipes.presentation.details.RecipeDetailsActivity;

public class IngredientsWidgetProvider extends AppWidgetProvider {

    /**
     * Updates all widgets instances for this widget
     *
     * @param context       base Context from this call was originated
     * @param widgetManager to be used for updates
     * @param appWidgetsIds widgets ids that will be updated
     * @param recipe        data to use in the update
     */
    public static void update(Context context,
                              AppWidgetManager widgetManager,
                              int[] appWidgetsIds,
                              Recipe recipe) {
        for (int appWidgetId : appWidgetsIds) {
            updateAppWidget(context, widgetManager, appWidgetId, recipe);
        }
    }

    private static void updateAppWidget(Context context,
                                        AppWidgetManager appWidgetManager,
                                        int appWidgetId,
                                        Recipe recipe) {

        RemoteViews widgetRemoteView = getIngredientListToUpdate(context, recipe);
        widgetRemoteView.setTextViewText(R.id.ingredients_widget_recipe_name, recipe.name());

        Intent reconfigurationIntent = new Intent(context, IngredientsWidgetProviderConfigurationActivity.class);
        reconfigurationIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent reconfigurationPendingIntent = PendingIntent.getActivity(context, appWidgetId,
                reconfigurationIntent, 0);
        widgetRemoteView.setOnClickPendingIntent(R.id.ingredients_widget_recipe_name, reconfigurationPendingIntent);
        widgetRemoteView.setOnClickPendingIntent(R.id.ingredients_widget_edit_recipe, reconfigurationPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, widgetRemoteView);
    }

    private static RemoteViews getIngredientListToUpdate(Context context, Recipe recipe) {
        RemoteViews ingredientsListView = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);
        Intent remoteAdapterIntent = new Intent(context, RemoteIngredientsListAdapterService.class);

        IngredientFormatter ingredientsFormatter = new IngredientFormatter(context);
        List<String> ingredientsDescription = ingredientsFormatter.formatIngredients(recipe.ingredients());
        ArrayList<String> ingredientsToBeBundled = new ArrayList<>(ingredientsDescription.size());
        ingredientsToBeBundled.addAll(ingredientsDescription);

        remoteAdapterIntent.putStringArrayListExtra(RemoteIngredientsListAdapterService.EXTRA_INGREDIENTS_LIST, ingredientsToBeBundled);
        ingredientsListView.setRemoteAdapter(R.id.ingredients_widget_list_view, remoteAdapterIntent);

        Intent recipeDetailIntent = new Intent(context, RecipeDetailsActivity.class);
        recipeDetailIntent.putExtra(RecipeDetailsActivity.ARGUMENT_RECIPE, recipe);
        PendingIntent recipeDetailPendingIntent = PendingIntent.getActivity(context, 0,
                recipeDetailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        ingredientsListView.setPendingIntentTemplate(R.id.ingredients_widget_list_view, recipeDetailPendingIntent);

        return ingredientsListView;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        IngredientsRefreshService.refreshIngredientsList(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) { /* Do Nothing */ }

    @Override
    public void onEnabled(Context context) { /* Do nothing */ }

    @Override
    public void onDisabled(Context context) { /* Do nothing */ }
}

