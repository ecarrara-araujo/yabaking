package br.com.ecarrara.yabaking.recipes.data.datasource.dbflow;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;

import java.util.List;

import br.com.ecarrara.yabaking.recipes.data.datasource.RecipesLocalDataSource;
import br.com.ecarrara.yabaking.recipes.data.datasource.dbflow.model.DBFlowIngredient;
import br.com.ecarrara.yabaking.recipes.data.datasource.dbflow.model.DBFlowRecipe;
import br.com.ecarrara.yabaking.recipes.data.datasource.dbflow.model.DBFlowRecipe_Table;
import br.com.ecarrara.yabaking.recipes.data.datasource.dbflow.model.DBFlowStep;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import io.reactivex.Completable;
import io.reactivex.Maybe;

public class DBFlowLocalDataSource implements RecipesLocalDataSource {

    private final DatabaseWrapper database;

    public DBFlowLocalDataSource(DatabaseWrapper databaseWrapper) {
        this.database = databaseWrapper;
    }

    @Override
    public Completable save(List<Recipe> recipes) {
        return Completable.defer(() -> {
            SQLite.delete(DBFlowRecipe.class).execute();
            SQLite.delete(DBFlowIngredient.class).execute();
            SQLite.delete(DBFlowStep.class).execute();
            FastStoreModelTransaction
                    .insertBuilder(FlowManager.getModelAdapter(DBFlowRecipe.class))
                    .addAll(DBFlowRecipe.Mapper.fromDomain(recipes))
                    .build()
                    .execute(database);
            for (Recipe recipe : recipes) {
                FastStoreModelTransaction
                        .insertBuilder(FlowManager.getModelAdapter(DBFlowIngredient.class))
                        .addAll(DBFlowIngredient.Mapper.fromDomain(recipe.ingredients(), recipe.id()))
                        .build()
                        .execute(database);
                FastStoreModelTransaction
                        .insertBuilder(FlowManager.getModelAdapter(DBFlowStep.class))
                        .addAll(DBFlowStep.Mapper.fromDomain(recipe.steps(), recipe.id()))
                        .build()
                        .execute(database);
            }
            return Completable.complete();
        });
    }

    @Override
    public Maybe<List<Recipe>> list() {
        return Maybe.defer(() -> {
            List<DBFlowRecipe> dbFlowRecipes = SQLite.select().from(DBFlowRecipe.class).queryList();
            if (dbFlowRecipes.isEmpty()) {
                return Maybe.empty();
            } else {
                return Maybe.just(DBFlowRecipe.Mapper.toDomain(dbFlowRecipes));
            }
        });
    }

    @Override
    public Maybe<Recipe> get(@NonNull Integer id) {
        return Maybe.defer(() -> {
            DBFlowRecipe dbFlowRecipe = SQLite.select().from(DBFlowRecipe.class)
                    .where(DBFlowRecipe_Table.id.eq(id))
                    .querySingle();
            if (dbFlowRecipe == null) {
                return Maybe.empty();
            } else {
                return Maybe.just(DBFlowRecipe.Mapper.toDomain(dbFlowRecipe));
            }
        });
    }

}
