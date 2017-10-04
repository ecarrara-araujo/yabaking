package br.com.ecarrara.yabaking.recipes.data.datasource.rest;

import java.util.List;

import br.com.ecarrara.yabaking.recipes.data.datasource.rest.model.JsonRecipe;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface UdacityRecipesApi {

    @GET("android-baking-app-json")
    Single<List<JsonRecipe>> listRecipes();

}
