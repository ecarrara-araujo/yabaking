package br.com.ecarrara.yabaking.ingredients.presentation.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import br.com.ecarrara.yabaking.R;

public class RemoteIngredientsListViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final int NUMBER_OF_VIEW_TYPES = 1;

    private Context applicationContext;
    private List<String> ingredientsDescription;

    RemoteIngredientsListViewsFactory(Context applicationContext,
                                      List<String> ingredientsDescription) {
        this.applicationContext = applicationContext;
        this.ingredientsDescription = ingredientsDescription;
    }

    @Override
    public void onCreate() { /* Do nothing */ }

    @Override
    public void onDataSetChanged() { /* Do nothing */ }

    @Override
    public void onDestroy() { /* Do nothing */ }

    @Override
    public int getCount() {
        return ingredientsDescription == null ? 0 : ingredientsDescription.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(applicationContext.getPackageName(),
                R.layout.ingredients_widget_list_item);

        remoteViews.setTextViewText(R.id.ingredient_list_item_text_view,
                ingredientsDescription.get(position));

        Intent fillIntent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.ingredient_list_item_text_view, fillIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return NUMBER_OF_VIEW_TYPES;
    }

    @Override
    public long getItemId(int itemId) {
        return itemId;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}