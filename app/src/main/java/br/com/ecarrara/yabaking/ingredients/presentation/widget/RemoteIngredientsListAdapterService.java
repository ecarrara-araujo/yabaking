package br.com.ecarrara.yabaking.ingredients.presentation.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class RemoteIngredientsListAdapterService extends RemoteViewsService {

    public static final String EXTRA_INGREDIENTS_LIST = "ingredients_list";

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        if (intent != null) {
            ArrayList<String> ingredients = intent.getStringArrayListExtra(EXTRA_INGREDIENTS_LIST);
            return new RemoteIngredientsListViewsFactory(getApplicationContext(), ingredients);
        }
        return null;
    }

}
