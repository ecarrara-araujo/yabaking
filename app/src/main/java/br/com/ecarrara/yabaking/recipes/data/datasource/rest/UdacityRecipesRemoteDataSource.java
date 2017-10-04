package br.com.ecarrara.yabaking.recipes.data.datasource.rest;

import java.util.List;

import br.com.ecarrara.yabaking.recipes.data.datasource.RecipesRemoteDataSource;
import br.com.ecarrara.yabaking.recipes.data.datasource.rest.model.JsonRecipe;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import io.reactivex.Single;

public class UdacityRecipesRemoteDataSource implements RecipesRemoteDataSource {

    private UdacityRecipesApi udacityRecipesApi;

    public UdacityRecipesRemoteDataSource(UdacityRecipesApi udacityRecipesApi) {
        this.udacityRecipesApi = udacityRecipesApi;
    }

    @Override
    public Single<List<Recipe>> list() {
        return udacityRecipesApi
                .listRecipes()
                .map(JsonRecipe.Mapper::toDomain);
    }

}
