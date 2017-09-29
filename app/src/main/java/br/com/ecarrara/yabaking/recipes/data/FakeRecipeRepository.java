package br.com.ecarrara.yabaking.recipes.data;

import java.util.ArrayList;
import java.util.List;

import br.com.ecarrara.yabaking.ingredients.domain.entity.Ingredient;
import br.com.ecarrara.yabaking.ingredients.domain.entity.Measure;
import br.com.ecarrara.yabaking.recipes.domain.RecipesRepository;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;
import io.reactivex.Single;

public class FakeRecipeRepository implements RecipesRepository {

    List<Recipe> recipes;

    public FakeRecipeRepository() {
        recipes = new ArrayList<>();
        recipes.add(getFakeCookieRecipe());
        recipes.add(getFakeRiceRecipe());
        recipes.add(getFakePastaRecipe());
        recipes.add(getFakeSaladRecipe());
        recipes.add(getFakeDessertRecipe());
    }

    @Override
    public Single<List<Recipe>> list() {
        return Single.defer(() -> Single.just(recipes));
    }

    @Override
    public Single<Recipe> get(Integer recipeId) {
        return Single.defer(() -> Single.just(recipes.get(recipeId)));
    }

    private Recipe getFakeCookieRecipe() {
        return Recipe.builder()
                .setId(0)
                .setName("Awesome Cookies for Fun")
                .setImageUrl("")
                .setIngredients(getFakeIngredientsList())
                .build();
    }

    private Recipe getFakeRiceRecipe() {
        return Recipe.builder()
                .setId(1)
                .setName("Rice to be Really happy")
                .setImageUrl("")
                .setIngredients(getFakeIngredientsList())
                .build();
    }

    private Recipe getFakePastaRecipe() {
        return Recipe.builder()
                .setId(2)
                .setName("Pasta for the fatty ones")
                .setImageUrl("")
                .setIngredients(getFakeIngredientsList())
                .build();
    }

    private Recipe getFakeSaladRecipe() {
        return Recipe.builder()
                .setId(3)
                .setName("Salad for the Fits")
                .setImageUrl("")
                .setIngredients(getFakeIngredientsList())
                .build();
    }

    private Recipe getFakeDessertRecipe() {
        return Recipe.builder()
                .setId(4)
                .setName("Desserts for everyone!")
                .setImageUrl("")
                .setIngredients(getFakeIngredientsList())
                .build();
    }

    private List<Ingredient> getFakeIngredientsList() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(getCrackerCrumbs());
        ingredients.add(getUnsaltedButter());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        ingredients.add(getGranulatedSugar());
        return ingredients;
    }

    private Ingredient getCrackerCrumbs() {
        return Ingredient.builder()
                .setMeasure(Measure.CUP)
                .setQuantity(2f)
                .setName("Graham Cracker crumbs")
                .build();
    }

    private Ingredient getUnsaltedButter() {
        return Ingredient.builder()
                .setMeasure(Measure.TABLE_SPOON)
                .setQuantity(6f)
                .setName("unsalted butter, melted")
                .build();
    }

    private Ingredient getGranulatedSugar() {
        return Ingredient.builder()
                .setMeasure(Measure.CUP)
                .setQuantity(0.5f)
                .setName("granulated sugar")
                .build();
    }

}
