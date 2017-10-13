package br.com.ecarrara.yabaking.recipes.data.datasource;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface RecipesLocalDataSource {

    Completable save(List<Recipe> recipes);

    Maybe<List<Recipe>> list();

    Maybe<Recipe> get(@NonNull Integer id);

}
