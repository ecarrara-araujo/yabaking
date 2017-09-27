package br.com.ecarrara.yabaking.recipes.data;

import java.util.ArrayList;
import java.util.List;

import br.com.ecarrara.yabaking.recipes.domain.RecipesRepository;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import io.reactivex.Single;

public class FakeRecipeRepository implements RecipesRepository {

    @Override
    public Single<List<Recipe>> list() {
        return Single.defer(() -> {
            List<Recipe> recipes = new ArrayList<Recipe>();
            recipes.add(getFakeCookieRecipe());
            recipes.add(getFakeRiceRecipe());
            recipes.add(getFakePastaRecipe());
            recipes.add(getFakeSaladRecipe());
            recipes.add(getFakeDessertRecipe());
            return Single.just(recipes);
        });
    }

    private Recipe getFakeCookieRecipe() {
        return Recipe.builder()
                .setId(1)
                .setName("Awesome Cookies for Fun")
                .setImageUrl("")
                .build();
    }

    private Recipe getFakeRiceRecipe() {
        return Recipe.builder()
                .setId(2)
                .setName("Rice to be Really happy")
                .setImageUrl("")
                .build();
    }

    private Recipe getFakePastaRecipe() {
        return Recipe.builder()
                .setId(3)
                .setName("Pasta for the fatty ones")
                .setImageUrl("")
                .build();
    }

    private Recipe getFakeSaladRecipe() {
        return Recipe.builder()
                .setId(4)
                .setName("Salad for the Fits")
                .setImageUrl("")
                .build();
    }

    private Recipe getFakeDessertRecipe() {
        return Recipe.builder()
                .setId(5)
                .setName("Desserts for everyone!")
                .setImageUrl("")
                .build();
    }

}
