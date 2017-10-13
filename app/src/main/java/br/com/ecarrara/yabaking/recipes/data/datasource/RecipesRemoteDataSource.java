package br.com.ecarrara.yabaking.recipes.data.datasource;

import java.util.List;

import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import io.reactivex.Single;

public interface RecipesRemoteDataSource {

    Single<List<Recipe>> list();

}
