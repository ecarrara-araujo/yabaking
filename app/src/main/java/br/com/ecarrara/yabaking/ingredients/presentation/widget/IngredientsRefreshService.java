package br.com.ecarrara.yabaking.ingredients.presentation.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.core.di.Injector;
import br.com.ecarrara.yabaking.recipes.domain.RecipesRepository;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;

public class IngredientsRefreshService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENTS_LIST = "br.com.ecarrara.yabaking.action.redresh_ingredients_list";

    @Inject
    RecipesRepository recipesRepository;

    public IngredientsRefreshService() {
        super(IngredientsRefreshService.class.getName());
    }

    /**
     * Starts this Service that will refresh the ingredients list on widgets for the recipe
     * selected for the user.
     */
    public static void refreshIngredientsList(Context context) {
        Intent intent = new Intent(context, IngredientsRefreshService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS_LIST);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Injector.applicationComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            if (ACTION_UPDATE_INGREDIENTS_LIST.equals(intent.getAction())) {
                handleIngredientsListRefreshAction();
            }
        }
    }

    private void handleIngredientsListRefreshAction() {
        Recipe userSelectedRecipe = recipesRepository.getRecipeForWidgetDisplay().blockingGet();

        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] widgetsIds = widgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
        widgetManager.notifyAppWidgetViewDataChanged(widgetsIds, R.id.ingredients_widget_list_view);

        IngredientsWidgetProvider.update(this, widgetManager, widgetsIds, userSelectedRecipe);
    }

}
