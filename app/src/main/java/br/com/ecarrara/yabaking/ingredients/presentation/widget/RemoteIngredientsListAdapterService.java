package br.com.ecarrara.yabaking.ingredients.presentation.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RemoteIngredientsListAdapterService extends RemoteViewsService {

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        if (intent != null) {
            return new RemoteIngredientsListViewsFactory(getApplicationContext());
        }
        return null;
    }

}
