package br.com.ecarrara.yabaking.core.data;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = DBFlowRecipeDatabase.NAME, version = DBFlowRecipeDatabase.VERSION)
public class DBFlowRecipeDatabase {

    public static final String NAME = "YaBakingRecipes";

    public static final int VERSION = 1;

}
